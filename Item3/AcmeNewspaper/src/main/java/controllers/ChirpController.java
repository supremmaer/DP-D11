
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.UserService;
import domain.Chirp;

@Controller
@RequestMapping("/chirp")
public class ChirpController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ChirpService	chirpService;
	@Autowired
	private UserService		userService;


	// Constructors -----------------------------------------------------------

	public ChirpController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int userId) {
		ModelAndView result;
		Collection<Chirp> chirps;

		chirps = this.chirpService.findByUserId(userId);
		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "chirp/list.do");

		return result;
	}

}
