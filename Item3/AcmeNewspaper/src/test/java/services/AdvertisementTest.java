
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
import domain.Advertisement;
import domain.Agent;
import domain.CreditCard;
import domain.Newspaper;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdvertisementTest extends AbstractTest {

	@Autowired
	private AdvertisementService	advertisementService;

	@Autowired
	private NewspaperService		newspaperService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CreditCardService		creditCardService;


	@Test
	public void createAndSaveDriver() {
		final Object[][] testingData = {
			{ // 4.2. Register an advertisement and place it in a newspaper.
				"agent1", "newspaper1", "creditCard3", "title", "http://www.url.com", "http://www.url.com", false, null
			}, { // URL1 no válida
				"agent1", "newspaper1", "creditCard3", "title", "nope", "http://www.url.com", false, ConstraintViolationException.class
			}, { // URL2 no válida
				"agent1", "newspaper1", "creditCard3", "title", "http://www.url.com", "nope", false, ConstraintViolationException.class
			}, { // Título vacío
				"agent1", "newspaper1", "creditCard3", "", "http://www.url.com", "http://www.url.com", false, ConstraintViolationException.class
			}, { // Un usuario intenta crear un advertisement
				"user1", "newspaper1", "creditCard3", "title", "http://www.url.com", "http://www.url.com", false, ClassCastException.class
			}, { // Un customer intenta crear un adver
				"cutomer1", "newspaper1", "creditCard3", "title", "http://www.url.com", "http://www.url.com", false, IllegalArgumentException.class
			}
		//			{ // Agente que no es dueño de la creditcard
		//				"agent1", "newspaper1", "creditCard1", "title", "http://www.url.com", "http://www.url.com", false, IllegalArgumentException.class
		//			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],/* */
					(String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Boolean) testingData[i][6],/**/(Class<?>) testingData[i][7]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void removeDriver() {
		final Object[][] testingData = {
			{ // An admin emove an advertisement that he or she thinks is inappropriate
				"admin", "advertisement1", null
			}
		//			, { // A user attempts to remove an article
		//				"user1", "advertisement1", IllegalArgumentException.class
		//			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.removeTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods

	protected void createAndSaveTemplate(final String agent, final String newspaper, final String creditCard, /* */
		final String title, final String banner, final String target, final Boolean taboo, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Integer newspaperId;
		Integer creditCardId;
		Integer agentId;
		Newspaper newspaperAux;
		CreditCard creditCardAux;
		Advertisement advertisement;
		Agent agentAux;
		try {
			this.authenticate(agent);
			agentId = super.getEntityId(agent);
			newspaperId = super.getEntityId(newspaper);
			creditCardId = super.getEntityId(creditCard);
			agentAux = (Agent) this.actorService.findOne(agentId);
			newspaperAux = this.newspaperService.findOne(newspaperId);
			creditCardAux = this.creditCardService.findOne(creditCardId);
			advertisement = this.advertisementService.create();
			advertisement.setAgent(agentAux);
			advertisement.setBanner(banner);
			advertisement.setCreditCard(creditCardAux);
			advertisement.setNewspaper(newspaperAux);
			advertisement.setTargetPage(target);
			advertisement.setTitle(title);

			this.advertisementService.save(advertisement);
			this.advertisementService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void removeTemplate(final String user, final String beanName, final Class<?> expected) {
		Class<?> caught;
		Integer advertisementId;
		Advertisement advertisement;
		Collection<Advertisement> advertisements;

		caught = null;
		try {
			this.authenticate(user);

			advertisementId = this.getEntityId(beanName);
			advertisement = this.advertisementService.findOne(advertisementId);

			this.advertisementService.delete(advertisement);

			advertisements = this.advertisementService.findAll();
			Assert.isTrue(!advertisements.contains(advertisement));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
