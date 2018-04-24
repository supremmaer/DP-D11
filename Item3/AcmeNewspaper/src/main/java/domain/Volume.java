
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Volume extends DomainEntity {

	private String	title;
	private String	description;
	private Integer	year;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotNull
	public Integer getYear() {
		return this.year;
	}


	//Relationships

	private User					user;
	private Collection<Newspaper>	newspapers;


	@NotNull
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@ManyToMany
	public Collection<Newspaper> getNewspapers() {
		return this.newspapers;
	}

	public void setNewspapers(final Collection<Newspaper> newspapers) {
		this.newspapers = newspapers;
	}
}
