
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ArticleService;
import services.NewspaperService;
import controllers.AbstractController;
import domain.Article;

@Controller
@RequestMapping("/administrator/article")
public class ArticleAdministratorController extends AbstractController {

	//Service -----------------------------------------------------------------
	@Autowired
	private ArticleService		articleService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NewspaperService	newspaperService;


	// Constructors -----------------------------------------------------------

	public ArticleAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Article> articles;

		articles = this.articleService.findTaboo();
		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("requestURI", "administrator/article/list.do");

		return result;
	}
	// Delete ---------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int articleId) {
		ModelAndView result;
		Article article;

		article = this.articleService.findOne(articleId);
		this.articleService.delete(article);
		result = new ModelAndView("redirect:/administrator/article/list.do");

		return result;
	}

}
