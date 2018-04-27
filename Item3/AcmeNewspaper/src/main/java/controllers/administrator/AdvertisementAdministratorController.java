
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdvertisementService;
import controllers.AbstractController;
import domain.Advertisement;

@Controller
@RequestMapping("/administrator/advertisement")
public class AdvertisementAdministratorController extends AbstractController {

	//Service -----------------------------------------------------------------
	@Autowired
	private AdvertisementService	advertisementService;

	@Autowired
	private ActorService			actorService;


	//Constructor
	public AdvertisementAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Advertisement> advertisements;

		advertisements = this.advertisementService.findTaboo();
		result = new ModelAndView("advertisement/list");
		result.addObject("advertisement", advertisements);
		result.addObject("requestURI", "administrator/advertisement/list.do");

		return result;
	}

	// Delete ---------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int advertisementId) {
		ModelAndView result;
		Advertisement advertisement;

		advertisement = this.advertisementService.findOne(advertisementId);
		this.advertisementService.delete(advertisement);
		result = new ModelAndView("redirect:/administrator/advertisement/list.do");
		return result;
	}

}
