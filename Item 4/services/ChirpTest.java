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

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chirp;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChirpTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ChirpService	chirpService;

	Date					fechaValida	= new Date();


	// Tests ------------------------------------------------------------------
	@Test
	public void createAndSaveDriver() {
		final Object testingData[][] = {
			{	//Creacion Chirp correcta
				"user1", "title1", new Date(), "descriptioooooon", false, null
			}, {//Creacion erronea Chirp sin usuario logueado 
				null, "title1", new Date(), "descriptioooooon", false, IllegalArgumentException.class
			}, {//Creacion erronea Chirp titulo en blanco
				"user1", "", new Date(), "descriptioooooon", false, ConstraintViolationException.class
			}, {//Creacion erronea Chirp titulo null
				"user1", null, new Date(), "descriptioooooon", false, NullPointerException.class
			}, {//Creacion erronea descripcion en blanco
				"user1", "title1", new Date(), "", false, ConstraintViolationException.class
			}, {//Creacion erronea descripcion null
				"user1", "title1", new Date(), null, false, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (String) testingData[i][3], (boolean) testingData[i][4], (Class<?>) testingData[i][5]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	// Ancillary methods ------------------------------------------------------
	protected void createAndSaveTemplate(final String beanName, final String title, final Date moment, final String description, final boolean containsTaboo, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {

			this.authenticate(beanName);

			Chirp chirp;

			chirp = this.chirpService.create();

			chirp.setTitle(title);
			chirp.setMoment(moment);
			chirp.setDescription(description);
			chirp.setContainsTaboo(containsTaboo);

			this.chirpService.save(chirp);

			this.chirpService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
