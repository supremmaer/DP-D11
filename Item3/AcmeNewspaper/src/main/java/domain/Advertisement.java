
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Advertisement extends DomainEntity {

	private String	title;
	private String	url;
	private boolean	taboo;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@URL
	@NotBlank
	public String getUrl() {
		return this.url;
	}

	@NotNull
	public boolean isTaboo() {
		return this.taboo;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setTaboo(final boolean taboo) {
		this.taboo = taboo;
	}


	// Relationships

	private Agent		agent;
	private CreditCard	creditCard;


	@NotNull
	public Agent getAgent() {
		return this.agent;
	}

	@NotNull
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setAgent(final Agent agent) {
		this.agent = agent;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
