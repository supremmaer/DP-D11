
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "containsTaboo")
})
public class Chirp extends DomainEntity {

	private String	title;
	private Date	moment;
	private String	description;
	private boolean	containsTaboo;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotNull
	public boolean isContainsTaboo() {
		return this.containsTaboo;
	}

	public void setContainsTaboo(final boolean containsTaboo) {
		this.containsTaboo = containsTaboo;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	//Relationships

}
