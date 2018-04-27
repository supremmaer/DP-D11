
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.VolumeService;
import domain.Actor;
import domain.User;
import domain.Volume;

@Controller
@RequestMapping("/user/volume")
public class VolumeController extends AbstractController {

	//Service -----------------------------------------------------------------
	@Autowired
	private VolumeService	volumeService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public VolumeController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String keyword) {
		ModelAndView result;
		Collection<Volume> volumes;
		Actor actor;

		volumes = this.volumeService.findAll();
		result = new ModelAndView("volume/list");
		result.addObject("volumes", volumes);
		result.addObject("requestURI", "volume/list.do");
		if (this.actorService.isLogged()) {
			actor = this.actorService.findByPrincipal();
			if (actor instanceof User)
				result.addObject("userId", actor.getId());
		}

		return result;
	}

}
