
package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FollowUpService;
import services.NewspaperService;
import controllers.AbstractController;
import domain.FollowUp;

@Controller
@RequestMapping("/user/followup")
public class FollowUpUserController extends AbstractController {

	//Service -----------------------------------------------------------------
	@Autowired
	private FollowUpService		followupService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NewspaperService	newspaperService;


	// Constructors -----------------------------------------------------------

	public FollowUpUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int articleId) {
		ModelAndView result;
		FollowUp followup;

		followup = this.followupService.create(articleId);
		result = this.createEditModelAndView(followup);
		result.addObject("requestURI", "user/followup/edit.do?articleId=" + articleId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FollowUp followup, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(followup);
		else
			try {
				this.followupService.save(followup);
				result = new ModelAndView("redirect:/article/display.do?articleId=" + followup.getArticle().getId());
			} catch (final Throwable oops) {
				String errorMessage = "followup.commit.error";
				errorMessage = this.error(oops.toString());
				result = this.createEditModelAndView(followup, errorMessage);
				result.addObject("requestURI", "user/followup/edit.do?articleId=" + followup.getArticle().getId());
			}

		return result;
	}

	private String error(final String s) {
		String result;

		if (s.contains("followup.error.author"))
			result = "followup.error.author";
		else if (s.contains("followup.error.nonPublished"))
			result = "followup.error.nonPublished";
		else
			result = "followup.commit.error";

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final FollowUp followup) {
		ModelAndView result;

		result = this.createEditModelAndView(followup, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FollowUp followup, final String message) {
		ModelAndView result;

		result = new ModelAndView("followup/edit");
		result.addObject("followUp", followup);
		result.addObject("message", message);

		return result;
	}
}
