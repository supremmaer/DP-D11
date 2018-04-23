
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NewspaperService;
import controllers.AbstractController;
import domain.Article;
import domain.Newspaper;

@Controller
@RequestMapping("/administrator/newspaper")
public class NewspaperAdministratorController extends AbstractController {

	//Service -----------------------------------------------------------------
	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public NewspaperAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Newspaper> newspapers;

		newspapers = this.newspaperService.findTaboo();
		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "administrator/newspaper/list.do");

		return result;
	}
	// Delete ---------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int newspaperId) {
		ModelAndView result;
		Newspaper newspaper;

		newspaper = this.newspaperService.findOne(newspaperId);
		this.newspaperService.delete(newspaper);
		result = new ModelAndView("redirect:/administrator/newspaper/list.do");

		return result;
	}

}
