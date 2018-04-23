
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FollowUpRepository;
import domain.Article;
import domain.FollowUp;
import domain.User;

@Service
@Transactional
public class FollowUpService {

	//Managed Repository ----
	@Autowired
	private FollowUpRepository	followUpRepository;

	@Autowired
	private ArticleService		articleService;

	@Autowired
	private ActorService		actorService;


	//Constructors
	public FollowUpService() {
		super();
	}

	public FollowUp create(final int articleId) {
		FollowUp result;
		Article article;

		article = this.articleService.findOne(articleId);
		result = new FollowUp();
		result.setArticle(article);

		return result;
	}

	public Collection<FollowUp> findAll() {
		Collection<FollowUp> result;

		result = this.followUpRepository.findAll();

		return result;
	}

	public void delete(final FollowUp followUp) {

		this.followUpRepository.delete(followUp);

	}

	public FollowUp save(final FollowUp followUp) {
		FollowUp result;
		User user;
		Date date;

		date = new Date();
		user = (User) this.actorService.findByPrincipal();
		Assert.isTrue(user.getArticles().contains(followUp.getArticle()), "followup.error.author");
		Assert.isTrue(followUp.getArticle().getPublishMoment() != null, "followup.error.nonPublished");
		followUp.setPublishMoment(date);
		result = this.followUpRepository.save(followUp);
		user.addFollowUp(result);
		this.actorService.save(user);

		return result;
	}

	public FollowUp findOne(final int followUpId) {
		FollowUp result;

		result = this.followUpRepository.findOne(followUpId);
		Assert.notNull(result);

		return result;
	}

	public Collection<FollowUp> findByArticleId(final int articleId) {
		Collection<FollowUp> result;

		result = this.followUpRepository.findByArticleId(articleId);

		return result;
	}

	public void flush() {
		this.followUpRepository.flush();
	}

	//dashboard

	public Double avgFollowperArticle() {
		return this.followUpRepository.avgFollowperArticle();
	}

	public Collection<FollowUp> followsOneWeakPublicated() {
		final Collection<FollowUp> result = this.followUpRepository.followsOneWeakPublicated();
		return result;
	}

	public Collection<FollowUp> followsTwoWeakPublicated() {
		final Collection<FollowUp> result = this.followUpRepository.followTwoWeakPublicated();
		return result;
	}

	public Double avgfollowsOneWeakPublicated() {
		final Collection<Article> art = this.articleService.findAll();
		final Collection<FollowUp> foll = this.followsOneWeakPublicated();
		final Double res = 1.0 * foll.size() / art.size();
		return res;
	}

	public Double avgfollowsTwoWeakPublicated() {
		final Collection<Article> art = this.articleService.findAll();
		final Collection<FollowUp> foll = this.followsTwoWeakPublicated();
		final Double res = 1.0 * foll.size() / art.size();
		return res;
	}
}
