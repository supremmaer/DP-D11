package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Article;
import domain.Newspaper;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ArticleTest extends AbstractTest {

	@Autowired
	private ArticleService	articleService;

	@Autowired
	private NewspaperService	newspaperService;

	@Test
	public void createAndSaveDriver() {
		final Object[][] testingData = {
			{ // 6.3 - A user writes an article
				"user1","article101","text","summary",false,null
			},
			{ // Taboo words
				"user1","article102","sex","summary",true,null
			},
			{
				"user1","viagra","text","summary",true,null
			},
			{
				"user1","article103","text","viagra",true,null
			},
			{ // Empty title
				"user1","","text","summary",false,ConstraintViolationException.class
			},
			{ // Empty text
				"user1","article104","","summary",false,ConstraintViolationException.class
			},
			{ // A customer writes an article
				"customer1","article105","text","summary",false,ClassCastException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],(String) testingData[i][3], (Boolean) testingData[i][4],
					(Class<?>) testingData[i][5]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void removeDriver() {
		final Object[][] testingData = {
			{ // 7.2 - An admin removes an article
				"admin","article1",null
			},
			{ // A user attempts to remove an article
				"user1","article1",IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.removeTemplate((String) testingData[i][0], (String) testingData[i][1],
					(Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void testFindAllPublished(){
		Collection<Article>	articles;

		try{
			super.startTransaction();

			articles = this.articleService.findAllPublished();
			Assert.notEmpty(articles);
			for (final Article a : articles)
				Assert.isTrue(a.getPublishMoment() != null);
		} finally {
			super.rollbackTransaction();
		}
	}

	// Ancillary methods

	protected void createAndSaveTemplate(final String user, final String title, final String text,final String summary, final Boolean taboo, final Class<?> expected){
		Class<?> caught;
		Article article;
		Article result;
		Integer newspaperId;
		Newspaper newspaper;

		caught = null;
		try {
			this.authenticate(user);
			newspaperId = super.getEntityId("newspaper9");
			article = this.articleService.create();
			article.setPictures(new HashSet<String>());
			article.setTitle(title);
			article.setText(text);
			article.setSummary(summary);
			article = this.articleService.save(article,newspaperId);
			this.articleService.flush();

			result = this.articleService.findOne(article.getId());
			Assert.isTrue(result.isTaboo() == taboo);

			newspaper = this.newspaperService.findOne(newspaperId);
			Assert.isTrue(newspaper.getArticles().contains(result));

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void removeTemplate(final String user,final String beanName,final Class<?> expected){
		Class<?> caught;
		Integer articleId;
		Article article;
		Collection<Article> articles;

		caught = null;
		try{
			this.authenticate(user);

			articleId = this.getEntityId(beanName);
			article = this.articleService.findOne(articleId);

			this.articleService.delete(article);

			articles = this.articleService.findAll();
			Assert.isTrue(!articles.contains(article));
		} catch (final Throwable oops){
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
