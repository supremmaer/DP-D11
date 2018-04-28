
package controllers.user;

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
import services.NewspaperService;
import services.VolumeService;
import controllers.AbstractController;
import domain.Actor;
import domain.Newspaper;
import domain.User;
import domain.Volume;

@Controller
@RequestMapping("/user/volume")
public class VolumeUserController extends AbstractController {

	//Service -----------------------------------------------------------------
	@Autowired
	private VolumeService		volumeService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NewspaperService	newspaperService;


	// Constructors -----------------------------------------------------------

	public VolumeUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String keyword) {
		ModelAndView result;
		Collection<Volume> volumes;
		Actor actor;

		volumes = this.volumeService.findByPrincipal();
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

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Volume volume;

		volume = this.volumeService.create();
		result = this.createEditModelAndView(volume);
		result.addObject("requestURI", "user/volume/edit.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int volumeId) {
		ModelAndView result;
		Volume volume;

		volume = this.volumeService.findOne(volumeId);
		result = this.createEditModelAndView(volume);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Volume volume, final BindingResult binding) {
		ModelAndView result;
		String errorMessage;

		if (binding.hasErrors())
			result = this.createEditModelAndView(volume);
		else
			try {
				this.volumeService.save(volume);
				result = new ModelAndView("redirect:/user/volume/list.do");
			} catch (final Throwable oops) {
				errorMessage = this.error(oops.toString());
				result = this.createEditModelAndView(volume, errorMessage);
				result.addObject("requestURI", "user/volume/edit.do");
			}

		return result;
	}

	@RequestMapping(value = "/addNewspaper", method = RequestMethod.GET)
	public ModelAndView addNewspaperCreate(@RequestParam final int volumeId) {
		ModelAndView result;
		Collection<Newspaper> newspapers;
		Volume volume;

		volume = this.volumeService.findOne(volumeId);
		newspapers = this.newspaperService.findAllPublished();
		newspapers.removeAll(volume.getNewspapers());
		result = new ModelAndView("volume/addNewspaper");
		result.addObject("volumeId", volumeId);
		result.addObject("newspapers", newspapers);

		return result;
	}

	@RequestMapping(value = "/addNewspaper", method = RequestMethod.POST, params = "save")
	public ModelAndView addNewspaperSave(final Integer volumeId, final Integer newspaperId) {
		ModelAndView result;
		String errorMessage;

		try {
			this.volumeService.addNewspaper(volumeId, newspaperId);
			result = new ModelAndView("redirect:/volume/display.do?volumeId=" + volumeId);
		} catch (final Throwable oops) {
			errorMessage = this.error(oops.toString());
			result = new ModelAndView("volume/addNewspaper");
			result.addObject("message", errorMessage);
			result.addObject("requestURI", "user/volume/addNewspaper.do");
		}

		return result;
	}

	@RequestMapping(value = "/removeNewspaper", method = RequestMethod.GET)
	public ModelAndView removeNewspaper(@RequestParam final int volumeId, @RequestParam final int newspaperId) {
		ModelAndView result;

		result = new ModelAndView("redirect:/volume/display.do?volumeId=" + volumeId);
		try {
			this.volumeService.removeNewspaper(volumeId, newspaperId);
			result.addObject("removedNewspaper", true);
		} catch (final Throwable oops) {
			result.addObject("errorRemovedNewspaper", true);
		}

		return result;
	}

	private String error(final String s) {
		String result;

		if (s.contains("volume.error.author"))
			result = "volume.error.author";
		else
			result = "volume.commit.error";

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Volume volume) {
		ModelAndView result;

		result = this.createEditModelAndView(volume, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Volume volume, final String message) {
		ModelAndView result;

		result = new ModelAndView("volume/edit");
		result.addObject("volume", volume);
		result.addObject("message", message);

		return result;
	}
}
