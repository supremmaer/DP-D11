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
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreditCardTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private NewspaperService	newspaperService;


	// Tests ------------------------------------------------------------------
	@Test
	public void createAndSaveDriver() {
		final Object testingData[][] = {
			{	//Credit card correcta
				"customer1", "holdername", "brandnameTest", "6011933334883747", 12, 2020, 123, null
			}, {	//Credit card con nombre vacio
				"customer1", "", "brandnameTest", "6011933334883747", 12, 2020, 123, ConstraintViolationException.class
			}, {	//Credit card con nombre vacio
				"customer1", "holderName", "", "6011933334883747", 12, 2020, 123, ConstraintViolationException.class
			}, {	//Credit card con numero vacio
				"customer1", "holderName", "branName", "", 12, 2020, 123, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6],
					(Class<?>) testingData[i][7]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	// Ancillary methods ------------------------------------------------------
	protected void createAndSaveTemplate(final String beanName, final String holderName, final String brandName, final String number, final Integer expirationMonth, final Integer expirationYear, final Integer CVV, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		final CreditCard creditCard;

		try {
			this.authenticate(beanName);

			creditCard = this.creditCardService.create();
			creditCard.setHolderName(holderName);
			creditCard.setBrandName(brandName);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(expirationMonth);
			creditCard.setExpirationYear(expirationYear);
			creditCard.setCVV(CVV);
			this.creditCardService.save(creditCard);
			this.creditCardService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
