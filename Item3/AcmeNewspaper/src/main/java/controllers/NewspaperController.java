
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

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
import services.UserService;
import domain.Article;
import domain.Customer;
import domain.Newspaper;
import domain.User;

@Controller
@RequestMapping("/newspaper")
public class NewspaperController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private NewspaperService	newspaperService;
	@Autowired
	private ArticleService		articleService;
	@Autowired
	private UserService			userService;
	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public NewspaperController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Newspaper newspaper;

		newspaper = this.newspaperService.create();
		result = this.createEditModelAndView(newspaper);

		//En el caso de que necesite meter los newspapers directamente en el create
		//		Muestro los articulos disponibles(ver que es mas optimo,esto o una query compleja)
		//		Collection<Article> articles = this.articleService.findAll();
		//		Collection<Newspaper> newspapers= this.newspaperService.findAll();
		//		Collection<Article> articleWithoutNewspaper = new HashSet<>();
		//		for(Article a: articles){
		//			boolean libre =true;
		//			for(Newspaper n:newspapers){
		//			if(n.getArticles().contains(a)){
		//			libre = false;
		//			}
		//			}
		//		if(libre){
		//			articleWithoutNewspaper.add(a);
		//		}
		//		}

		//		result.addObject("articles", articleWithoutNewspaper);
		return result;
	}
	//Publish

	@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public ModelAndView publish(@RequestParam final int newspaperId) {
		ModelAndView result;
		try {
			final Newspaper newspapernuevo = this.newspaperService.publish(newspaperId);
			result = this.display(newspapernuevo.getId());

		} catch (final Throwable oops) {
			result = this.display(newspaperId);
			final boolean error = true;
			result.addObject("Error", error);

		}
		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String criteria) {
		ModelAndView result;
		Collection<Newspaper> newspapers;

		newspapers = this.newspaperService.findByCriteria(criteria);
		if (criteria == null)
			newspapers = this.newspaperService.findAll();

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		if (this.actorService.isLogged() && this.actorService.findByPrincipal() instanceof Customer) {
			Collection<Newspaper> newspaperscustomer = new ArrayList<Newspaper>();
			final Customer customer = (Customer) this.actorService.findByPrincipal();
			newspaperscustomer = this.newspaperService.findByCustomerID(customer.getId());
			result.addObject("newspaperscustomer", newspaperscustomer);
		}

		return result;
	}

	// display

	@RequestMapping(value = "display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int newspaperId) {
		final ModelAndView result;
		result = new ModelAndView("newspaper/display");
		final Newspaper newspaper;
		newspaper = this.newspaperService.findOne(newspaperId);
		result.addObject("newspaper", newspaper);
		boolean mostrarArticles = true;
		mostrarArticles = false;
		User u;
		if (this.actorService.isLogged())
			if (this.actorService.findByPrincipal() instanceof User) {
				final User actual = (User) this.actorService.findByPrincipal();
				final Boolean EsAutor = actual.getNewspapers().contains(newspaper);
				final Boolean NoEstaPublicado = newspaper.getPublicationDate() == null;
				final Boolean Mostrar = NoEstaPublicado && EsAutor;
				result.addObject("EsAutor", Mostrar);
				mostrarArticles = EsAutor;
			}
		final Collection<Article> articles = newspaper.getArticles();
		final TreeMap<Integer, User> mapaMegaComplejo = new TreeMap<>();

		for (final Article a : articles) {
			u = this.userService.UserByArticle(a.getId());

			mapaMegaComplejo.put(a.getId(), u);
		}
		if (newspaper.getPublicity() == false)
		mostrarArticles = true;
		if (newspaper.getPublicity() == true) {
		
			if (this.actorService.isLogged()&&this.actorService.findByPrincipal() instanceof Customer) {
				final Customer c = (Customer) this.actorService.findByPrincipal();
				final Collection<Newspaper> customerNewspapers = this.newspaperService.findByCustomerID(c.getId());
				if (customerNewspapers.contains(newspaper))
					mostrarArticles = true;
			}
		}

		result.addObject("mapaMegaComplejo", mapaMegaComplejo);
		result.addObject("mostrarArticles", mostrarArticles);

		return result;
	}
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int newspaperId) {
		ModelAndView result;
		Newspaper newspaper;

		newspaper = this.newspaperService.findOne(newspaperId);

		result = this.createEditModelAndView(newspaper);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Newspaper newspaper, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(newspaper);
		else
			try {
				this.newspaperService.save(newspaper);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				String errorMessage = "newspaper.commit.error";

				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(newspaper, errorMessage);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Newspaper newspaper, final BindingResult binding) {
		ModelAndView result;

		try {
			this.newspaperService.delete(newspaper);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			String errorMessage = "category.commit.error";

			if (oops.getMessage().contains("message.error"))
				errorMessage = oops.getMessage();
			result = this.createEditModelAndView(newspaper, errorMessage);
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Newspaper newspaper) {
		ModelAndView result;

		result = this.createEditModelAndView(newspaper, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Newspaper newspaper, final String message) {
		ModelAndView result;

		result = new ModelAndView("newspaper/edit");
		result.addObject("newspaper", newspaper);
		result.addObject("message", message);

		return result;
	}
}
