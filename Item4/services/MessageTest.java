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
import domain.Actor;
import domain.Folder;
import domain.Message;
import forms.MessageForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageTest extends AbstractTest {

	@Autowired
	private MessageService messageService;

	@Autowired
	private FolderService folderService;

	@Autowired
	private ActorService actorService;

	// Tests ------------------------------------------------------------------
	@Test
	public void sendMessageDriver() {
		final Object testingData[][] = {
			{	//user1 envía un mensaje
				"user1", "title1", "user2@mail.com", "message text", false, null
			},
			{	//user1 envía un mensaje a una dirección errónea
				"user1", "title1", "wrongmail", "message text", false, ConstraintViolationException.class
			},
			{	//user1 envía un mensaje con campos en blanco
				"user1", "", "user2@mail.com", "message text", false, ConstraintViolationException.class
			},
			{
				"user1", "title1", "", "message text", false, ConstraintViolationException.class
			},
			{
				"user1", "title1", "user2@mail.com", "", false, ConstraintViolationException.class
			},
			{	//user1 envía un mensaje con spam
				"user1", "sex", "user2@mail.com", "message text", true, null
			},
			{
				"user1", "title1", "user2@mail.com", "viagra", true, null
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.sendMessageTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (boolean) testingData[i][4], (Class<?>) testingData[i][5]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void sendNotificationDriver() {
		final Object testingData[][] = {
			{	//admin envía una notificación
				"admin", "title1", "message text", false, null
			},
			{	//user1 envía una notificación
				"user1", "title1", "message text", false, IllegalArgumentException.class
			},
			{	//admin envía una notificación con campos en blanco
				"admin", "", "message text", false, ConstraintViolationException.class
			},
			{
				"admin", "title1", "", false, ConstraintViolationException.class
			},
			{	//admin envía una notificación con spam
				"admin", "sex", "message text", true, null
			},
			{
				"admin", "title1", "viagra", true, null
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.sendNotificationTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (boolean) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------
	protected void sendMessageTemplate(final String beanName, final String subject, final String recipient, final String text, final boolean spam, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {

			this.authenticate(beanName);
			final MessageForm messageForm = new MessageForm();

			messageForm.setSubject(subject);
			messageForm.setDestination(recipient);
			messageForm.setBody(text);
			messageForm.setPriority("High");

			final Message message = this.messageService.send(messageForm);
			final Collection<Actor> recipients = message.getRecipients();

			for (final Actor actor : recipients){
				final Folder folder = spam ? this.folderService.findFolderByActorAndName(actor, "Spam") : this.folderService.findInboxByActor(actor);
				Assert.isTrue(folder.getMessages().contains(message));
			}

			this.messageService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void sendNotificationTemplate(final String beanName, final String subject, final String text, final boolean spam, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {

			this.authenticate(beanName);
			final MessageForm messageForm = new MessageForm();

			messageForm.setSubject(subject);
			messageForm.setDestination("NOTIFICATION");
			messageForm.setBody(text);
			messageForm.setPriority("High");

			final Message message = this.messageService.send(messageForm);
			final Collection<Actor> recipients = this.actorService.findAll();

			for (final Actor actor : recipients){
				final Folder folder = spam ? this.folderService.findFolderByActorAndName(actor, "Spam") : this.folderService.findNotificationBoxByActor(actor);
				Assert.isTrue(folder.getMessages().contains(message));
			}

			this.messageService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
