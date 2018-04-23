package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Newspaper;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class NewspaperTest extends AbstractTest{
	// System under test ------------------------------------------------------
	@Autowired
	private NewspaperService	newspaperService;

	@Test
	public void createAndSaveDriver() {
		final Object[][] testingData = {
			{ // 6.1 - A user creates a newspaper
				"user1","newspaper101","description",false,null
			},
			{ // Taboo words
				"user1","newspaper102","sex",true,null
			},
			{
				"user1","viagraNewspaper","description",true,null
			},
			{ // Empty title
				"user2","","description",false,ConstraintViolationException.class
			},
			{ // Empty description
				"user1","newspaper101","",false,ConstraintViolationException.class
			},
			{ // A customer creates a newspaper
				"customer1","newspaper101","description",false,ClassCastException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Boolean) testingData[i][3],
					(Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void publishDriver() {
		final Object[][] testingData = {
			{ // 6.2 - A user publishes a newspaper
				"user1","newspaper9",null
			},
			{ // User publishes an already published newspaper
				"user1","newspaper1",IllegalArgumentException.class
			},
			{ // User publishes a newspaper with draft articles
				"user1","newspaper7",IllegalArgumentException.class
			},
			{ // User publishes another user's newspaper
				"user1","newspaper2",IllegalArgumentException.class
			},
			{ // Newspaper without articles
				"user1","newspaper5",IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.publishTemplate((String) testingData[i][0], (String) testingData[i][1],
					(Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void removeDriver() {
		final Object[][] testingData = {
			{ // 7.2 - An admin removes a newspaper
				"admin","newspaper9",null
			},
			{ // A user attempts to remove a newspaper
				"user1","newspaper9",IllegalArgumentException.class
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
	public void testFindByCriteria(){
		Collection<Newspaper> newspapers;
		final String criteria = "draft";
		try{
			super.startTransaction();
			newspapers = this.newspaperService.findByCriteria(criteria);
			for (final Newspaper n : newspapers)
				Assert.isTrue((n.getTitle().contains(criteria)) || (n.getDescription().contains(criteria)));
		}finally{
			super.rollbackTransaction();
		}
	}

	// Ancillary methods

	protected void createAndSaveTemplate(final String user,final String title,final String description,final Boolean taboo,final Class<?> expected){
		Class<?> caught;
		Newspaper newspaper;
		Newspaper result;

		caught = null;
		try {
			this.authenticate(user);
			newspaper = this.newspaperService.create();
			newspaper.setTitle(title);
			newspaper.setDescription(description);
			newspaper = this.newspaperService.save(newspaper);
			this.newspaperService.flush();

			result = this.newspaperService.findOne(newspaper.getId());
			Assert.isTrue(result.isTaboo() == taboo);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void publishTemplate(final String user,final String beanName,final Class<?> expected){
		Class<?> caught;
		Integer newspaperId;
		Newspaper newspaper;

		caught = null;
		try{
			this.authenticate(user);

			newspaperId = this.getEntityId(beanName);

			this.newspaperService.publish(newspaperId);

			newspaper = this.newspaperService.findOne(newspaperId);
			Assert.notNull(newspaper.getPublicationDate());
		} catch (final Throwable oops){
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void removeTemplate(final String user,final String beanName,final Class<?> expected){
		Class<?> caught;
		Integer newspaperId;
		Newspaper newspaper;
		Collection<Newspaper> newspapers;

		caught = null;
		try{
			this.authenticate(user);

			newspaperId = this.getEntityId(beanName);
			newspaper = this.newspaperService.findOne(newspaperId);

			this.newspaperService.delete(newspaper);

			newspapers = this.newspaperService.findAll();
			Assert.isTrue(!newspapers.contains(newspaper));
		} catch (final Throwable oops){
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
