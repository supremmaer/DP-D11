
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChirpService;
import controllers.AbstractController;
import domain.Actor;
import domain.Chirp;

@Controller
@RequestMapping("/user/chirp")
public class ChirpUserController extends AbstractController {

	//Service -----------------------------------------------------------------
	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ChirpUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chirp> chirps;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		chirps = this.chirpService.findByFollowed(principal.getId());
		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "user/chirp/list.do");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Chirp chirp;

		chirp = this.chirpService.create();
		result = this.createEditModelAndView(chirp);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Chirp chirp, final BindingResult binding) {
		ModelAndView result;
		Actor principal;

		if (binding.hasErrors())
			result = this.createEditModelAndView(chirp);
		else
			try {
				this.chirpService.save(chirp);
				principal = this.actorService.findByPrincipal();
				result = new ModelAndView("redirect:/user/display.do?userId=" + principal.getId());
			} catch (final Throwable oops) {
				String errorMessage = "category.commit.error";

				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(chirp, errorMessage);
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Chirp chirp) {
		ModelAndView result;

		result = this.createEditModelAndView(chirp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Chirp chirp, final String message) {
		ModelAndView result;

		result = new ModelAndView("chirp/edit");
		result.addObject("chirp", chirp);
		result.addObject("message", message);

		return result;
	}
}
