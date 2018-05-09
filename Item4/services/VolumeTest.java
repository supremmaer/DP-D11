
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
import domain.Volume;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class VolumeTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private VolumeService		volumeService;

	@Autowired
	private NewspaperService	newspaperService;


	@Test
	public void createAndSaveDriver() {
		final Object[][] testingData = {
			{ // A user creates a volume
				"user1", "volume101", "description", 2018, null
			}, { // A user creates a volume without title
				"user1", "", "description", 2018, ConstraintViolationException.class
			}, { // A user creates a volume without description
				"user1", "volume101", "", 2018, ConstraintViolationException.class
			}, { // A user creates a volume without year
				"user1", "volume101", "description", null, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void addNewspaperDriver() {
		final Object[][] testingData = {
			{ // Un usuario añade un newspaper a un volume
				"user1", "volume1", "newspaper4", null
			}, { // Un customer añade un newspaper a un volume
				"customer1", "volume1", "newspaper4", ClassCastException.class
			}, { // Un agent añade un newspaper a un volume
				"agent1", "volume1", "newspaper4", ClassCastException.class
			}, { // Un usuario no logueado añade un newspaper a un volume
				"", "volume1", "newspaper4", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.addNewspaperTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void removeNewspaperDriver() {
		final Object[][] testingData = {
			{ // Un usuario añade un newspaper a un volume
				"user1", "volume1", "newspaper3", null
			}, { // Un customer añade un newspaper a un volume
				"customer1", "volume1", "newspaper3", ClassCastException.class
			}, { // Un agent añade un newspaper a un volume
				"agent1", "volume1", "newspaper3", ClassCastException.class
			}, { // Un usuario no logueado añade un newspaper a un volume
				"", "volume1", "newspaper3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.removeNewspaperTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void testFindAll() {
		Collection<Volume> volumes;
		Volume volume;
		int volumeId;

		volumes = this.volumeService.findAll();
		volumeId = super.getEntityId("volume1");
		volume = this.volumeService.findOne(volumeId);
		Assert.isTrue(volumes.contains(volume));
		Assert.isTrue(volumes.size() == 2);

	}

	// Ancillary methods

	protected void createAndSaveTemplate(final String user, final String title, final String description, final Integer year, final Class<?> expected) {
		Class<?> caught;
		Volume volume;

		caught = null;
		try {
			this.authenticate(user);
			volume = this.volumeService.create();
			volume.setTitle(title);
			volume.setDescription(description);
			volume.setYear(year);
			volume = this.volumeService.save(volume);
			this.volumeService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void addNewspaperTemplate(final String user, final String volumeBeanName, final String newspaperBeanName, final Class<?> expected) {
		Class<?> caught;
		Volume volume;
		Newspaper newspaperAdd;
		Integer volumeId, newspaperId;

		caught = null;
		try {
			this.authenticate(user);
			volumeId = super.getEntityId(volumeBeanName);
			newspaperId = super.getEntityId(newspaperBeanName);
			volume = this.volumeService.findOne(volumeId);
			newspaperAdd = this.newspaperService.findOne(newspaperId);
			volume.addNewspapers(newspaperAdd);
			volume = this.volumeService.save(volume);
			this.volumeService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void removeNewspaperTemplate(final String user, final String volumeBeanName, final String newspaperBeanName, final Class<?> expected) {
		Class<?> caught;
		Volume volume;
		Newspaper newspaperAdd;
		Integer volumeId, newspaperId;

		caught = null;
		try {
			this.authenticate(user);
			volumeId = super.getEntityId(volumeBeanName);
			newspaperId = super.getEntityId(newspaperBeanName);
			volume = this.volumeService.findOne(volumeId);
			newspaperAdd = this.newspaperService.findOne(newspaperId);
			volume.removeNewspapers(newspaperAdd);
			volume = this.volumeService.save(volume);
			this.volumeService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
