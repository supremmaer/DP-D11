
package forms;

import domain.CreditCard;
import domain.Newspaper;

public class SubscribeForm {

	private CreditCard	creditCard;
	private Newspaper	newspaper;


	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditcard) {
		this.creditCard = creditcard;
	}

	public Newspaper getNewspaper() {
		return this.newspaper;
	}

	public void setNewspaper(final Newspaper newspaper) {
		this.newspaper = newspaper;
	}

}
