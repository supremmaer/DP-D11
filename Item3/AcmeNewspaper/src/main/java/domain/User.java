
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	//Relationships

	private Collection<User>		users;
	private Collection<Chirp>		chirps;
	private Collection<Newspaper>	newspapers;
	private Collection<Article>		articles;
	private Collection<FollowUp>	followUp;


	@ManyToMany
	@NotNull
	public Collection<User> getUsers() {
		return this.users;
	}

	@OneToMany
	@NotNull
	public Collection<Chirp> getChirps() {
		return this.chirps;
	}

	@OneToMany
	@NotNull
	public Collection<Newspaper> getNewspapers() {
		return this.newspapers;
	}

	@OneToMany
	@NotNull
	public Collection<Article> getArticles() {
		return this.articles;
	}

	@OneToMany
	@NotNull
	public Collection<FollowUp> getFollowUp() {
		return this.followUp;
	}

	public void setUsers(final Collection<User> users) {
		this.users = users;
	}

	public void setChirps(final Collection<Chirp> chirps) {
		this.chirps = chirps;
	}

	public void setNewspapers(final Collection<Newspaper> newspapers) {
		this.newspapers = newspapers;
	}

	public void setArticles(final Collection<Article> articles) {
		this.articles = articles;
	}

	public void setFollowUp(final Collection<FollowUp> followUp) {
		this.followUp = followUp;
	}

	public void addArticle(final Article article) {
		this.articles.add(article);

	}
	public void removeArticle(final Article article) {
		this.articles.remove(article);

	}

	public void addFollowUp(final FollowUp followUp) {
		this.followUp.add(followUp);

	}
	public void removeFollowUp(final FollowUp followUp) {
		this.followUp.remove(followUp);

	}

}
