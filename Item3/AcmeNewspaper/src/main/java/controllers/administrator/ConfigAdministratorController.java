
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigService;
import controllers.AbstractController;
import domain.Config;
import forms.TabooWordForm;

@Controller
@RequestMapping("/administrator/config")
public class ConfigAdministratorController extends AbstractController {

	//Service -----------------------------------------------------------------
	@Autowired
	private ConfigService	configService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ConfigAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Config config;

		config = this.configService.findConfiguration();
		result = new ModelAndView("config/display");
		result.addObject("config", config);
		result.addObject("requestURI", "administrator/config/display.do");

		return result;
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/removeTaboo", method = RequestMethod.GET)
	public ModelAndView removeTaboo(final String tabooWord) {
		ModelAndView result;

		result = new ModelAndView("redirect:/administrator/config/display.do");

		this.configService.removeTabooWord(tabooWord);

		return result;
	}
	@RequestMapping(value = "/addTaboo", method = RequestMethod.GET)
	public ModelAndView addTaboo() {
		ModelAndView result;
		TabooWordForm tabooWordForm;

		tabooWordForm = new TabooWordForm();
		result = this.createEditModelAndView(tabooWordForm);

		return result;
	}

	@RequestMapping(value = "/addTaboo", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TabooWordForm tabooWordForm, final BindingResult binding) {
		ModelAndView result;
		Config config;
		Collection<String> tabooWords;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tabooWordForm);
		else
			try {
				config = this.configService.findConfiguration();
				tabooWords = config.getTabooWords();
				tabooWords.add(tabooWordForm.getTabooWord());
				config.setTabooWords(tabooWords);
				this.configService.save(config);
				result = new ModelAndView("redirect:/administrator/config/display.do");
			} catch (final Throwable oops) {
				String errorMessage = "category.commit.error";

				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(tabooWordForm, errorMessage);
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final TabooWordForm tabooWordForm) {
		ModelAndView result;

		result = this.createEditModelAndView(tabooWordForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final TabooWordForm tabooWordForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("config/addTaboo");
		result.addObject("tabooWordForm", tabooWordForm);
		result.addObject("message", message);

		return result;
	}
}
