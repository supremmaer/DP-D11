/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ArticleService;
import services.ChirpService;
import services.UserService;
import domain.Actor;
import domain.Article;
import domain.Chirp;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private UserService		userService;

	@Autowired
	private ArticleService	articleService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ChirpService	chirpService;


	// Constructors -----------------------------------------------------------

	public UserController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		final ModelAndView result;
		final Collection<User> users;

		users = this.userService.findAll();

		result = new ModelAndView("actor/list");
		result.addObject("actors", users);
		result.addObject("actorType", "user");
		result.addObject("requestURI", "user/list.do");
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int userId) throws Exception {
		final ModelAndView result;
		User user;
		Collection<Article> articles;
		Collection<User> following, followers;
		Actor actor;
		Integer id;
		Collection<Chirp> chirps;

		chirps = this.chirpService.findByUserId(userId);
		user = this.userService.findOne(userId);

		articles = this.articleService.findAllPublishedByUser(userId);
		following = user.getUsers();
		followers = this.userService.findFollowingMe(userId);
		result = new ModelAndView("actor/display");
		if (this.actorService.isLogged()) {
			actor = this.actorService.findByPrincipal();
			id = actor.getId();
			if (actor instanceof User)
				result.addObject("userId", id);
		}
		result.addObject("user", user);
		result.addObject("articles", articles);
		result.addObject("following", following);
		result.addObject("followers", followers);
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "user/display.do?userId=" + userId);
		return result;
	}

}
