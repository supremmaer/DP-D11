
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
import domain.Customer;
import domain.User;
import domain.Volume;

@Controller
@RequestMapping("/volume")
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

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int volumeId, final Boolean removedNewspaper, final Boolean errorRemovedNewspaper) {
		ModelAndView result;
		Volume volume;
		Actor actor;
		Customer customer;
		Collection<Volume> volumesCustomer;
		boolean subscribeable;

		volume = this.volumeService.findOne(volumeId);
		subscribeable = true;
		result = new ModelAndView("volume/display");
		result.addObject("requestURI", "volume/display.do");
		result.addObject("volume", volume);
		if (this.actorService.isLogged()) {
			actor = this.actorService.findByPrincipal();
			if (actor instanceof User)
				result.addObject("userId", actor.getId());
			else if (actor instanceof Customer) {
				customer = (Customer) this.actorService.findByPrincipal();
				volumesCustomer = this.volumeService.findByCustomerId(customer.getId());
				if (volumesCustomer.contains(volume))
					subscribeable = false;
			}
		}
		result.addObject("subscribeable", subscribeable);
		if (removedNewspaper != null)
			result.addObject("removedNewspaper", removedNewspaper);
		else if (errorRemovedNewspaper != null)
			result.addObject("errorRemovedNewspaper", errorRemovedNewspaper);

		return result;
	}

}
