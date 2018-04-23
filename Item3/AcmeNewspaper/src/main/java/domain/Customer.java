
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	//Relationships

	private Collection<CreditCard>	creditCard;


	@NotNull
	@OneToMany
	public Collection<CreditCard> getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final Collection<CreditCard> creditCards) {
		this.creditCard = creditCards;
	}

}
