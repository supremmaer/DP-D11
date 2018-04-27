
package forms;

import domain.CreditCard;
import domain.Volume;

public class SubscribeVolumeForm {

	private CreditCard	creditCard;
	private Volume		volume;


	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditcard) {
		this.creditCard = creditcard;
	}

	public Volume getVolume() {
		return this.volume;
	}

	public void setVolume(final Volume volume) {
		this.volume = volume;
	}

}
