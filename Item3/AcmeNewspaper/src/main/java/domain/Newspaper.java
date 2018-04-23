
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "taboo")
})
public class Newspaper extends DomainEntity {

	private String	title;
	private String	description;
	private Date	publicationDate;
	private String	picture;
	private boolean	publicity;
	private boolean	taboo;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPublicationDate() {
		return this.publicationDate;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}

	@NotNull
	public Boolean getPublicity() {
		return this.publicity;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public void setPublicity(final Boolean publicity) {
		this.publicity = publicity;
	}


	//Relationships
	private Collection<Article>	articles;


	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(final Collection<Article> articles) {
		this.articles = articles;
	}

	public void addArticle(final Article article) {
		this.articles.add(article);
	}

	public void removeArticle(final Article article) {
		this.articles.remove(article);
	}

	public boolean isTaboo() {
		return this.taboo;
	}

	public void setTaboo(final boolean taboo) {
		this.taboo = taboo;
	}

}
