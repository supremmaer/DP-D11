
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class CreditCard extends DomainEntity {

	private String	holderName;
	private String	brandName;
	private String	number;
	private int		expirationMonth;
	private int		expirationYear;
	private int		CVV;


	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}

	@NotBlank
	public String getBrandName() {
		return this.brandName;
	}

	@CreditCardNumber
	@Pattern(regexp = "[0-9]{16}")
	public String getNumber() {
		return this.number;
	}

	@Range(min = 1, max = 12)
	public int getExpirationMonth() {
		return this.expirationMonth;
	}

	public int getExpirationYear() {
		return this.expirationYear;
	}

	@Range(min = 100, max = 999)
	public int getCVV() {
		return this.CVV;
	}

	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public void setExpirationMonth(final int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public void setExpirationYear(final int expirationYear) {
		this.expirationYear = expirationYear;
	}

	public void setCVV(final int cVV) {
		this.CVV = cVV;
	}


	//Relationships

	private Collection<Newspaper>	newspapers;


	@ManyToMany
	@NotNull
	public Collection<Newspaper> getNewspapers() {
		return this.newspapers;
	}

	public void setNewspapers(final Collection<Newspaper> newspapers) {
		this.newspapers = newspapers;
	}

}
