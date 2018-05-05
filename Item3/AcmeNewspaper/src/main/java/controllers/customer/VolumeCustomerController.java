
package controllers.customer;

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
import services.VolumeService;
import controllers.AbstractController;
import domain.CreditCard;
import domain.Customer;
import domain.Volume;
import forms.SubscribeVolumeForm;

@Controller
@RequestMapping("/customer/volume")
public class VolumeCustomerController extends AbstractController {

	//Services

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private VolumeService		volumeService;

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private ActorService		actorService;


	//Constructor

	public VolumeCustomerController() {
		super();
	}

	//

	@RequestMapping(value = "/subscribe", method = RequestMethod.GET)
	public ModelAndView request(@RequestParam final Integer volumeId) {
		Volume volume;
		SubscribeVolumeForm subscribeVolumeForm;
		ModelAndView result;

		volume = this.volumeService.findOne(volumeId);
		subscribeVolumeForm = this.creditCardService.createForm(volume);

		result = this.createEditModelAndView(subscribeVolumeForm);
		result.addObject("volumeId", volumeId);

		return result;
	}

	@RequestMapping(value = "/subscribe", method = RequestMethod.POST, params = "save")
	public ModelAndView request(@Valid final SubscribeVolumeForm subscribeVolumeForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(subscribeVolumeForm);
		else
			try {
				this.creditCardService.subscribe(subscribeVolumeForm);
				result = new ModelAndView("redirect:/volume/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "creditCard.commit.error";
				errorMessage = this.error(oops.toString());
				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(subscribeVolumeForm, errorMessage);
			}

		return result;
	}

	private String error(final String s) {
		String result;

		if (s.contains("creditCard.error.expired"))
			result = "creditCard.error.expired";
		else
			result = "creditCard.commit.error";

		return result;
	}

	// Ancillary Methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final SubscribeVolumeForm subscribeVolumeForm) {
		ModelAndView result;

		result = this.createEditModelAndView(subscribeVolumeForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SubscribeVolumeForm subscribeVolumeForm, final String messageCode) {
		ModelAndView result;
		Customer customer;
		Collection<CreditCard> creditcards;

		customer = (Customer) this.actorService.findByPrincipal();
		creditcards = this.creditCardService.findByCustomer(customer.getId());

		result = new ModelAndView("customer/subscribeVolume");
		result.addObject("subscribeVolumeForm", subscribeVolumeForm);
		result.addObject("message", messageCode);
		result.addObject("creditcards", creditcards);
		return result;
	}

}
