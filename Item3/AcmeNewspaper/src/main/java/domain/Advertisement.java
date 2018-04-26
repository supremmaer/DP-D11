
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Advertisement extends DomainEntity {

	private String	title;
	private String	banner;
	private String	targetPage;
	private boolean	taboo;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	@URL
	@NotBlank
	public String getTargetPage() {
		return this.targetPage;
	}

	@NotNull
	public boolean isTaboo() {
		return this.taboo;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setTargetPage(final String targetPage) {
		this.targetPage = targetPage;
	}

	public void setTaboo(final boolean taboo) {
		this.taboo = taboo;
	}


	// Relationships

	private Agent		agent;
	private CreditCard	creditCard;


	@Valid
	@ManyToOne(optional = false)
	public Agent getAgent() {
		return this.agent;
	}

	@ManyToOne(optional = false)
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
