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

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ConfigService	configService;


	// Tests ------------------------------------------------------------------

	@Test
	public void addTabooWordDriver() {
		final Object testingData[][] = {

			//un Admin añade una palabra correctamente
			{
				"admin", "Buenas", null
			},
			//un Admin añade una palabra repetida
			{
				"admin", "sex", IllegalArgumentException.class
			},
			//un admin añade una palabra nula
			{
				"admin", null, IllegalArgumentException.class
			},
			//un usuario añade una palabra 
			{
				"user1", "Buenas", IllegalArgumentException.class
			},
			//un customer añade una palabra
			{
				"customer1", "Buenas", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.addTabooWordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void removeTabooWordDriver() {
		final Object testingData[][] = {
			//un Admin elimina una palabra correctamente
			{
				"admin", "sex", null
			},
			//un usuario elimina una palabra
			{
				"user1", "sexo", IllegalArgumentException.class
			},
			//un customer elimina una palabra
			{
				"customer1", "sexo", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.removeTabooWordTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------
	protected void addTabooWordTemplate(final String beanName, final String word, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			this.authenticate(beanName);
			this.configService.addTabooWord(word);
			this.configService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void removeTabooWordTemplate(final String beanName, final String word, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			this.authenticate(beanName);
			this.configService.removeTabooWord(word);
			this.configService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
