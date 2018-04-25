
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	private String	name;
	private String	surname;
	private String	postalAddress;
	private String	phoneNumber;
	private String	emailAddress;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getPostalAddress() {
		return this.postalAddress;
	}

	@Pattern(regexp = "^\\+?\\d+$")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	@Email
	@NotBlank
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setPostalAddress(final String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmailAddress(final String emailAddress) {
		this.emailAddress = emailAddress;
	}


	//Relationships

	private UserAccount			userAccount;
	private Collection<Folder>	folders;
	private Collection<Message>	messagesSent;
	private Collection<Message>	messagesReceived;


	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@OneToMany
	@NotNull
	public Collection<Folder> getFolders() {
		return this.folders;
	}

	public void setFolders(final Collection<Folder> folders) {
		this.folders = folders;
	}

	@OneToMany(mappedBy = "sender")
	@NotNull
	public Collection<Message> getMessagesSent() {
		return this.messagesSent;
	}

	@OneToMany(mappedBy = "recipient")
	@NotNull
	public Collection<Message> getMessagesReceived() {
		return this.messagesReceived;
	}

	public void setMessagesSent(final Collection<Message> messagesSent) {
		this.messagesSent = messagesSent;
	}

	public void setMessagesReceived(final Collection<Message> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}
}
