
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "publishMoment")
})
public class FollowUp extends DomainEntity {

	private String				title;
	private Date				publishMoment;
	private String				summary;
	private Collection<String>	pictures;
	private String				text;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPublishMoment() {
		return this.publishMoment;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getPictures() {
		return this.pictures;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setPublishMoment(final Date publishMoment) {
		this.publishMoment = publishMoment;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	public void setText(final String text) {
		this.text = text;
	}


	//Relationships

	private Article	article;


	@Valid
	@ManyToOne(optional = false)
	public Article getArticle() {
		return this.article;
	}

	public void setArticle(final Article article) {
		this.article = article;
	}

}
