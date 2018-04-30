
package controllers.agent;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NewspaperService;
import controllers.AbstractController;
import domain.Agent;
import domain.Newspaper;

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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Agent agent;
		Collection<Newspaper> newspapers;
		agent = (Agent) this.actorService.findByPrincipal();

		newspapers = this.newspaperService.findByAgentWithAdvertisements(agent.getId());

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "agent/newspaper/list.do");

		return result;
	}
	@RequestMapping(value = "/listNoAdv", method = RequestMethod.GET)
	public ModelAndView listNoAdv() {
		ModelAndView result;
		Agent agent;
		Collection<Newspaper> newspapers;
		agent = (Agent) this.actorService.findByPrincipal();

		newspapers = this.newspaperService.findByAgentWithoutAdvertisements(agent.getId());

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "agent/newspaper/listNoAdv.do");

		return result;
	}

}
