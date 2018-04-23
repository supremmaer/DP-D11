
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ArticleRepository;
import domain.Actor;
import domain.Administrator;
import domain.Article;
import domain.FollowUp;
import domain.Newspaper;
import domain.User;

@Service
@Transactional
public class ArticleService {

	//Managed Repository ----
	@Autowired
	private ArticleRepository	articleRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private ConfigService		configService;

	@Autowired
	private UserService			userService;

	@Autowired
	private FollowUpService		followUpService;


	//Constructors
	public ArticleService() {
		super();
	}

	public Article create() {
		Article result;

		result = new Article();

		return result;
	}

	public Collection<Article> findAll() {
		Collection<Article> result;

		result = this.articleRepository.findAll();

		return result;
	}

	public void delete(final Article article) {
		Actor principal;
		Collection<Article> articles;
		Collection<FollowUp> followUps;
		Collection<FollowUp> userFollowUps;
		User user;
		Newspaper newspaper;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);

		followUps = this.followUpService.findByArticleId(article.getId());
		//Borrando articulo del usuario
		user = this.userService.findByArticleId(article.getId());
		articles = user.getArticles();
		articles.remove(article);
		user.setArticles(articles);
		//Borrando followsUps de user
		userFollowUps = user.getFollowUp();
		userFollowUps.removeAll(followUps);
		this.userService.save(user);
		//Borrando FollowUps
		for (final FollowUp f : followUps)
			this.followUpService.delete(f);

		//Borrando articulo de su Newspaper
		newspaper = this.newspaperService.findByArticleId(article.getId());
		articles = newspaper.getArticles();
		articles.remove(article);
		newspaper.setArticles(articles);
		this.newspaperService.save(newspaper);

		//Borrando artículo
		this.articleRepository.delete(article);

	}
	public void deleteByNewspaper(final Article article) {
		Actor principal;
		Collection<Article> articles;
		Collection<FollowUp> followUps;
		Collection<FollowUp> userFollowUps;
		User user;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);

		followUps = this.followUpService.findByArticleId(article.getId());
		//Borrando articulo del usuario
		user = this.userService.findByArticleId(article.getId());
		articles = user.getArticles();
		articles.remove(article);
		user.setArticles(articles);
		//Borrando followsUps de user
		userFollowUps = user.getFollowUp();
		userFollowUps.removeAll(followUps);
		this.userService.save(user);
		//Borrando FollowUps
		for (final FollowUp f : followUps)
			this.followUpService.delete(f);

		//Borrando artículo
		this.articleRepository.delete(article);

	}
	public Article save(final Article article, final int newspaperId) {
		Article result, articledb;
		Newspaper newspaper, updated;
		User user;
		Collection<Article> old, neew;

		newspaper = this.newspaperService.findOne(newspaperId);
		user = (User) this.actorService.findByPrincipal();
		if (user.getNewspapers().contains(newspaper))
			Assert.isTrue(newspaper.getPublicationDate() == null, "article.error.alreadyPublished");
		else {
			Assert.isTrue(newspaper.getPublicationDate() == null, "article.error.alreadyPublished");
			Assert.isTrue(newspaper.getPublicity().equals(true), "article.error.notPublic");
		}

		if (this.configService.isTaboo(article.getTitle()) || this.configService.isTaboo(article.getSummary()) || this.configService.isTaboo(article.getText()))
			article.setTaboo(true);
		if (article.getId() == 0) {
			old = new ArrayList<Article>(newspaper.getArticles());
			newspaper.addArticle(article);
			updated = this.newspaperService.save(newspaper);
			neew = new ArrayList<Article>(updated.getArticles());
			neew.removeAll(old);
			result = neew.iterator().next();
			user.addArticle(result);
		} else {
			articledb = this.findOne(article.getId());
			Assert.isTrue(articledb.isDraftMode(), "article.error.finalMode");
			result = this.articleRepository.save(article);

		}

		return result;
	}
	public Article updateDate(final Article article) {
		Article result;
		result = this.articleRepository.save(article);
		return result;
	}
	
	public Article findOne(final int articleId) {
		Article result;

		result = this.articleRepository.findOne(articleId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.articleRepository.flush();
	}

	public Collection<Article> findByKeyword(final String keyword) {
		Collection<Article> result;

		result = this.articleRepository.findByKeyword(keyword);

		return result;
	}

	public Collection<Article> findAllPublished() {
		Collection<Article> result;

		result = this.articleRepository.findAllPublished();

		return result;
	}

	public Collection<Article> findAllPublishedByUser(final int userId) {
		Collection<Article> result;

		result = this.articleRepository.findAllPublishedByUser(userId);

		return result;
	}

	public Collection<Article> findByUser(final int userId) {
		Collection<Article> result;

		result = this.articleRepository.findByUser(userId);

		return result;
	}

	public Collection<Article> findTaboo() {
		Collection<Article> result;

		result = this.articleRepository.findTaboo();

		return result;

	}
}
