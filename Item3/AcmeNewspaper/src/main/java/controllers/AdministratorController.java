/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.ChirpService;
import services.CreditCardService;
import services.CustomerService;
import services.FollowUpService;
import services.NewspaperService;
import services.UserService;
import domain.Newspaper;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services --------------------------------------------------------

	@Autowired
	private UserService			userService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private ArticleService		articleService;

	@Autowired
	private FollowUpService		followUpService;

	@Autowired
	private ChirpService		chirpService;

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-2");

		return result;
	}
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;

		Double avgNewspaperperUser = 0.0;
		Double sdNewspaperperUser = 0.0;

		Double avgArticlesperRendezvous = 0.0;
		Double sdArticlesperRendezvous = 0.0;

		Double avgArticlesperNewspaper = 0.0;
		Double sdArticlesperNewspaper = 0.0;

		Collection<Newspaper> newspaperWithMoreArticles = new ArrayList<Newspaper>();

		Collection<Newspaper> newspaperWithLessArticles = new ArrayList<Newspaper>();

		Double ratioUserNewspaperWriters = 0.0;
		Double ratioUserArticlesWriters = 0.0;

		Double avgFollowPerArticle = 0.0;

		Double avgFollowPerArticlePublishedOne = 0.0;
		Double avgFollowPerArticlePublishedTwo = 0.0;

		Double avgChirpPerUser = 0.0;
		Double sdChirpPerUser = 0.0;

		Double ratioUserMoreChirp = 0.0;

		Double ratioPublicVsPrivate = 0.0;

		Double avgArticlePerPrivateNews = 0.0;
		Double avgArticlePerPublicNews = 0.0;

		Double ratioSubsVSTotal = 0.0;
		Double avgRatioPrivateVsPublic = 0.0;

		if (this.newspaperService.findAll().size() > 0 && this.userService.findAll().size() > 0 && this.articleService.findAll().size() > 0) {

			avgNewspaperperUser = this.userService.averageNewspaperperUser();
			sdNewspaperperUser = this.userService.standardNewspaperperUser();

			avgArticlesperRendezvous = this.userService.averageArticlesperUser();
			sdArticlesperRendezvous = this.userService.standarArticlesperUser();

			avgArticlesperNewspaper = this.newspaperService.averageArticlesPerNewspaper();
			sdArticlesperNewspaper = this.newspaperService.standardArticlesPerNewspaper();

			newspaperWithMoreArticles = this.newspaperService.findNewspapersWithMoreArticlesThanAverage();

			newspaperWithLessArticles = this.newspaperService.findNewspapersWithLessArticlesThanAverage();

			ratioUserNewspaperWriters = this.userService.ratioWriterNewspaper();
			ratioUserArticlesWriters = this.userService.ratioWritersArticle();

			if (this.followUpService.findAll().size() > 0) {

				avgFollowPerArticle = this.followUpService.avgFollowperArticle();

				avgFollowPerArticlePublishedOne = this.followUpService.avgfollowsOneWeakPublicated();
				avgFollowPerArticlePublishedTwo = this.followUpService.avgfollowsTwoWeakPublicated();
			}

			if (this.chirpService.findAll().size() > 0) {
				avgChirpPerUser = this.userService.avgChirpsPerUser();
				sdChirpPerUser = this.userService.standardChirpsperUser();

				ratioUserMoreChirp = this.userService.ratioUserwithMoreChirps();
			}

			ratioPublicVsPrivate = this.newspaperService.ratioPublicVsPrivate();

			avgArticlePerPrivateNews = this.newspaperService.averageArticlesPerPrivateNewspaper();
			avgArticlePerPublicNews = this.newspaperService.averageArticlesPerPublicNewspaper();

			if (this.customerService.findAll().size() > 0 && this.creditCardService.findAll().size() > 0)
				ratioSubsVSTotal = this.customerService.ratioSubscriptorsVScustomer();

			avgRatioPrivateVsPublic = this.newspaperService.avgRatioPrivateVsPublicPerUser();
		}

		result = new ModelAndView("administrator/dashboard");

		result.addObject("avgNewspaperperUser", avgNewspaperperUser);
		result.addObject("sdNewspaperperUser", sdNewspaperperUser);

		result.addObject("avgArticlesperRendezvous", avgArticlesperRendezvous);
		result.addObject("sdArticlesperRendezvous", sdArticlesperRendezvous);

		result.addObject("avgArticlesperNewspaper", avgArticlesperNewspaper);
		result.addObject("sdArticlesperNewspaper", sdArticlesperNewspaper);

		result.addObject("newspaperWithMoreArticles", newspaperWithMoreArticles);
		result.addObject("newspaperWithLessArticles", newspaperWithLessArticles);

		result.addObject("ratioUserNewspaperWriters", ratioUserNewspaperWriters);
		result.addObject("ratioUserArticlesWriters", ratioUserArticlesWriters);

		result.addObject("avgFollowPerArticle", avgFollowPerArticle);

		result.addObject("avgFollowPerArticlePublishedOne", avgFollowPerArticlePublishedOne);
		result.addObject("avgFollowPerArticlePublishedTwo", avgFollowPerArticlePublishedTwo);

		result.addObject("avgChirpPerUser", avgChirpPerUser);
		result.addObject("sdChirpPerUser", sdChirpPerUser);

		result.addObject("ratioUserMoreChirp", ratioUserMoreChirp);

		result.addObject("ratioPublicVsPrivate", ratioPublicVsPrivate);

		result.addObject("avgArticlePerPrivateNews", avgArticlePerPrivateNews);
		result.addObject("avgArticlePerPublicNews", avgArticlePerPublicNews);

		result.addObject("ratioSubsVSTotal", ratioSubsVSTotal);
		result.addObject("avgRatioPrivateVsPublic", avgRatioPrivateVsPublic);

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;
	}

}
