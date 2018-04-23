/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CreditCardService;
import services.NewspaperService;
import domain.CreditCard;
import domain.Customer;
import domain.Newspaper;
import forms.SubscribeForm;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	//Services

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NewspaperService	newspaperService;


	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("customer/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("customer/action-2");

		return result;
	}
	@RequestMapping(value = "/subscribe", method = RequestMethod.GET)
	public ModelAndView request(@RequestParam final Integer newspaperId) {
		Newspaper newspaper;
		SubscribeForm subscribeForm;
		ModelAndView result;

		newspaper = this.newspaperService.findOne(newspaperId);
		subscribeForm = this.creditCardService.createForm(newspaper);

		result = this.createEditModelAndView(subscribeForm);
		result.addObject("newspaperId", newspaperId);

		return result;
	}

	@RequestMapping(value = "/subscribe", method = RequestMethod.POST, params = "save")
	public ModelAndView request(@Valid final SubscribeForm subscribeForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(subscribeForm);
		else
			try {
				this.creditCardService.subscribe(subscribeForm);
				result = new ModelAndView("redirect:/newspaper/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(subscribeForm, "customer.commit.error");
			}

		return result;
	}

	// Ancillary Methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final SubscribeForm subscribeForm) {
		ModelAndView result;

		result = this.createEditModelAndView(subscribeForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SubscribeForm subscribeForm, final String messageCode) {
		ModelAndView result;
		Customer customer;
		Collection<CreditCard> creditcards;

		customer = (Customer) this.actorService.findByPrincipal();
		creditcards = this.creditCardService.findByCustomer(customer.getId());

		result = new ModelAndView("customer/subscribe");
		result.addObject("subscribeForm", subscribeForm);
		result.addObject("message", messageCode);
		result.addObject("creditcards", creditcards);
		return result;
	}
}
