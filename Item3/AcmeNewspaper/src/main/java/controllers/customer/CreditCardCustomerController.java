
package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CreditCardService;
import controllers.AbstractController;
import domain.CreditCard;

@Controller
@RequestMapping("/customer/creditCard")
public class CreditCardCustomerController extends AbstractController {

	//Services

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ActorService		actorService;


	//Constructor

	public CreditCardCustomerController() {
		super();
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreditCard creditCard;

		creditCard = this.creditCardService.create();
		result = this.createEditModelAndView(creditCard);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(creditCard);
		else
			try {
				this.creditCardService.save(creditCard);
				result = new ModelAndView("redirect:/newspaper/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(creditCard, "creditCard.commit.error");
			}

		return result;
	}

	//Ancillary methods

	private ModelAndView createEditModelAndView(final CreditCard creditCard) {
		ModelAndView result;

		result = this.createEditModelAndView(creditCard, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final CreditCard creditCard, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("creditCard/edit");
		result.addObject("creditCard", creditCard);
		result.addObject("message", messageCode);

		return result;
	}
}
