
package controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.UserService;
import controllers.AbstractController;
import domain.User;

@Controller
@RequestMapping("/user/user")
public class UserUserController extends AbstractController {

	//Service -----------------------------------------------------------------

	@Autowired
	private UserService		userService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public UserUserController() {
		super();
	}

	// Deleting ----------------------------------------------------------------	

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView unfollow(@RequestParam final int userId) {
		ModelAndView result;
		User user;

		this.userService.unfollow(userId);

		user = (User) this.actorService.findByPrincipal();
		result = new ModelAndView("redirect:/user/display.do?userId=" + user.getId());

		return result;
	}

	// Adding ----------------------------------------------------------------
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam final int userId) {
		ModelAndView result;
		User user;

		user = (User) this.actorService.findByPrincipal();

		this.userService.follow(userId);

		result = new ModelAndView("redirect:/user/display.do?userId=" + user.getId());

		return result;
	}
}
