
package controllers.agent;

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
import services.AdvertisementService;
import services.CreditCardService;
import services.NewspaperService;
import controllers.AbstractController;
import domain.Advertisement;
import domain.Agent;
import domain.CreditCard;
import domain.Newspaper;

@Controller
@RequestMapping("/agent/advertisement")
public class AdvertisementAgentController extends AbstractController {

	//Services

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdvertisementService	advertisementService;

	@Autowired
	private NewspaperService		newspaperService;


	//Constructor

	public AdvertisementAgentController() {
		super();
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer newspaperId) {
		ModelAndView result;
		Advertisement advertisement;
		Newspaper newspaper;

		newspaper = this.newspaperService.findOne(newspaperId);

		advertisement = this.advertisementService.create();
		advertisement.setNewspaper(newspaper);

		result = this.createEditModelAndView(advertisement);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Advertisement advertisement, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(advertisement);
		else
			try {
				this.advertisementService.save(advertisement);
				result = new ModelAndView("redirect:/newspaper/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "creditCard.commit.error";
				errorMessage = this.error(oops.toString());
				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(advertisement, errorMessage);
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

	//Ancillary methods

	private ModelAndView createEditModelAndView(final Advertisement advertisement) {
		ModelAndView result;

		result = this.createEditModelAndView(advertisement, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Advertisement advertisement, final String messageCode) {
		ModelAndView result;
		Agent agent;
		Collection<CreditCard> creditcards;

		agent = (Agent) this.actorService.findByPrincipal();
		creditcards = this.creditCardService.findByAgent(agent.getId());

		result = new ModelAndView("advertisement/edit");
		result.addObject("advertisement", advertisement);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "agent/advertisement/edit.do");
		result.addObject("creditcards", creditcards);
		return result;
	}
}
