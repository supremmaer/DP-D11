/*
 * SampleTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Article;
import domain.FollowUp;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FollowUpTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private FollowUpService	followUpService;

	@Autowired
	private ArticleService	articleService;


	// Tests ------------------------------------------------------------------
	@Test
	public void createAndSaveDriver() {
		final Object testingData[][] = {
			{	//FollowUp de un artículo publicado, todo correcto
				"user1", "article1", "title", "text", "summary", new Date(), new ArrayList<String>(), null
			}, {	//FollowUp de un artículo draft, 
				"user1", "article16", "title", "text", "summary", new Date(), new ArrayList<String>(), IllegalArgumentException.class
			}, {	//FollowUp de un artículo por otro usuario
				"user2", "article1", "title", "text", "summary", new Date(), new ArrayList<String>(), IllegalArgumentException.class
			}, {	//FollowUp de un artículo no draft, de un newspaper no publicado
				"user1", "article17", "title", "text", "summary", new Date(), new ArrayList<String>(), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Date) testingData[i][5], (Collection<String>) testingData[i][6],
					(Class<?>) testingData[i][7]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	// Ancillary methods ------------------------------------------------------
	protected void createAndSaveTemplate(final String beanName, final String articleBeanName, final String title, final String text, final String summary, final Date publishMoment, final Collection<String> pictures, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		int articleId;
		Article article;
		FollowUp followUp;
		try {
			this.authenticate(beanName);
			articleId = super.getEntityId(articleBeanName);
			article = this.articleService.findOne(articleId);
			followUp = this.followUpService.create(articleId);
			followUp.setTitle(title);
			followUp.setText(text);
			followUp.setSummary(summary);
			followUp.setPublishMoment(publishMoment);
			followUp.setPictures(pictures);
			this.followUpService.save(followUp);
			this.followUpService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
