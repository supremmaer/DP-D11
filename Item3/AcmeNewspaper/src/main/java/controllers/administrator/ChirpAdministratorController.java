
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChirpService;
import controllers.AbstractController;
import domain.Chirp;

@Controller
@RequestMapping("/administrator/chirp")
public class ChirpAdministratorController extends AbstractController {

	//Service -----------------------------------------------------------------
	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ChirpAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chirp> chirps;

		chirps = this.chirpService.findByTabooWords();
		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "administrator/chirp/list.do");

		return result;
	}

	// Delete ---------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int chirpId) {
		ModelAndView result;
		Chirp chirp;

		chirp = this.chirpService.findOne(chirpId);
		this.chirpService.delete(chirp);
		result = new ModelAndView("redirect:/administrator/chirp/list.do");

		return result;
	}

}
