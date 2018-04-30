
package controllers.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.ActorService;
import services.NewspaperService;
import controllers.AbstractController;

@Controller
@RequestMapping("/agent/newspaper")
public class NewspaperAgentController extends AbstractController {

	//Service -----------------------------------------------------------------
	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public NewspaperAgentController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Listing ----------------------------------------------------------------

	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//	public ModelAndView list() {
	//		ModelAndView result;
	//		Collection<Newspaper> newspapers;
	//
	//		newspapers = this.newspaperService
	//		result = new ModelAndView("newspaper/list");
	//		result.addObject("newspapers", newspapers);
	//		result.addObject("requestURI", "agent/newspaper/list.do");
	//
	//		return result;
	//	}
	//	@RequestMapping(value = "/listNoAdd", method = RequestMethod.GET)
	//	public ModelAndView listNoAdd() {
	//		ModelAndView result;
	//		Collection<Newspaper> newspapers;
	//
	//		newspapers = this.newspaperService.findTaboo();
	//		result = new ModelAndView("newspaper/list");
	//		result.addObject("newspapers", newspapers);
	//		result.addObject("requestURI", "agent/newspaper/listNoAdd.do");
	//
	//		return result;
	//	}

}
