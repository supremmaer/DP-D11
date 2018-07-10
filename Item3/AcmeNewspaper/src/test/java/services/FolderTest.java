
package services;

import java.util.HashSet;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Folder;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FolderTest extends AbstractTest {

	@Autowired
	private MessageService	messageService;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------------
	@Test
	public void createFolderDriver() {
		final Object testingData[][] = {
			{	//user1 crea una carpeta
				"user1", "folderTest", "rootFolderUser1", null
			}, {
				"user1", "folderTest", "inboxFolderUser1", null
			}, {	//user1 crea una carpeta sin nombre
				"user1", "", "rootFolderUser1", ConstraintViolationException.class
			}, {	//user1 crea una carpeta, la carpeta padre no existe
				"user1", "folder1", "nopeFolder", NumberFormatException.class
			}, {	//user1 crea una carpeta dentro de una de user2
				"user1", "folder1", "rootFolderUser2", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createFolderTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	protected void createFolderTemplate(final String beanName, final String folderName, final String parent, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {

			this.authenticate(beanName);
			final Folder folder = this.folderService.create();
			final int parentId = this.getEntityId(parent);

			folder.setName(folderName);
			folder.setParent(this.folderService.findOne(parentId));
			folder.setSystemFolders(false);
			folder.setFolders(new HashSet<Folder>());

			final Folder result = this.folderService.save(folder);

			this.folderService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
