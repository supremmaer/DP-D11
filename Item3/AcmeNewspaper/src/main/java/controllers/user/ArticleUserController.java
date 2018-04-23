
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
import services.ArticleService;
import services.NewspaperService;
import controllers.AbstractController;
import domain.Actor;
import domain.Article;
import domain.Newspaper;

@Controller
@RequestMapping("/user/article")
public class ArticleUserController extends AbstractController {

	//Service -----------------------------------------------------------------
	@Autowired
	private ArticleService		articleService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NewspaperService	newspaperService;


	// Constructors -----------------------------------------------------------

	public ArticleUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String keyword) {
		ModelAndView result;
		Collection<Article> articles;
		Actor actor;
		Integer userId;

		userId = null;
		actor = this.actorService.findByPrincipal();
		if (keyword == null) {
			articles = this.articleService.findByUser(actor.getId());
			userId = actor.getId();
		} else
			articles = this.articleService.findByKeyword(keyword);
		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("requestURI", "user/article/list.do");
		if (userId != null)
			result.addObject("userId", userId);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int newspaperId) {
		ModelAndView result;
		Article article;

		article = this.articleService.create();
		result = this.createEditModelAndView(article);
		result.addObject("newspaperId", newspaperId);
		result.addObject("requestURI", "user/article/edit.do?newspaperId=" + newspaperId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int articleId) {
		ModelAndView result;
		Article article;
		Newspaper newspaper;

		newspaper = this.newspaperService.findByArticleId(articleId);
		article = this.articleService.findOne(articleId);
		result = this.createEditModelAndView(article);
		result.addObject("newspaperId", newspaper.getId());
		result.addObject("requestURI", "user/article/edit.do?articleId=" + articleId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Article article, final BindingResult binding, final int newspaperId) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(article);
		else
			try {
				this.articleService.save(article, newspaperId);
				result = new ModelAndView("redirect:/user/article/list.do?newspaperId=" + newspaperId);
			} catch (final Throwable oops) {
				String errorMessage = "article.commit.error";
				errorMessage = this.error(oops.toString());
				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(article, errorMessage);
				result.addObject("requestURI", "user/article/edit.do?newspaperId=" + newspaperId);
			}

		return result;
	}

	private String error(final String s) {
		String result;

		if (s.contains("article.error.alreadyPublished"))
			result = "article.error.alreadyPublished";
		else if (s.contains("article.error.notPublic"))
			result = "article.error.notPublic";
		else if (s.contains("article.error.finalMode"))
			result = "article.error.finalMode";
		else
			result = "article.commit.error";
		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Article article) {
		ModelAndView result;

		result = this.createEditModelAndView(article, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Article article, final String message) {
		ModelAndView result;

		result = new ModelAndView("article/edit");
		result.addObject("article", article);
		result.addObject("message", message);

		return result;
	}
}
