
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import forms.ActorForm;

@Service
@Transactional
public class ActorService {

	//Managed Repository ----
	@Autowired
	private ActorRepository	actorRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private UserService				userService;

	@Autowired
	private CustomerService			customerService;


	//Constructors
	public ActorService() {
		super();
	}

	public Actor create() {
		Actor result;

		result = new Actor();

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();

		return result;
	}

	public void delete(final Actor actor) {

		this.actorRepository.delete(actor);

	}

	public Actor save(final Actor actor) {
		Actor result;

		result = this.actorRepository.save(actor);
		return result;
	}

	public Actor findOne(final int actorId) {
		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		return result;
	}

	public boolean isLogged() {
		boolean result = false;
		Object principal;

		principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserAccount)
			result = true;
		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Actor result;
		result = this.actorRepository.findByUserAccount(userAccount.getId());

		return result;
	}

	public void flush() {
		this.actorRepository.flush();
	}

	public Actor create(final String actorType) {
		Actor result;
		UserAccount userAccount;
		Authority authority;

		userAccount = new UserAccount();
		authority = new Authority();

		if (actorType.equals(Authority.ADMIN)) {
			Assert.isTrue(this.findByPrincipal() instanceof Administrator);
			authority.setAuthority(Authority.ADMIN);
			result = this.administratorService.create();
		} else if (actorType.equals(Authority.USER)) {
			authority.setAuthority(Authority.USER);
			result = this.userService.create();
		} else if (actorType.equals(Authority.CUSTOMER)) {
			authority.setAuthority(Authority.CUSTOMER);
			result = this.customerService.create();
		} else
			throw new ServiceException("Invalid actor type parameter");

		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);

		return result;
	}

	public Actor create(final ActorForm actorForm) {
		Actor result;

		Assert.isTrue(actorForm.getPassword().equals(actorForm.getPassword2()));

		result = this.create(actorForm.getAuthority());
		result.setPostalAddress(actorForm.getAddress());
		result.setEmailAddress(actorForm.getEmail());
		result.setName(actorForm.getName());
		result.setPhoneNumber(actorForm.getPhone());
		result.setSurname(actorForm.getSurname());
		result.getUserAccount().setUsername(actorForm.getUsername());
		result.getUserAccount().setPassword(actorForm.getPassword());

		return result;
	}

	public Actor register(final Actor actor) {
		Assert.notNull(actor);
		final Actor result;

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String pass = encoder.encodePassword(actor.getUserAccount().getPassword(), null);
		actor.getUserAccount().setPassword(pass);

		result = this.save(actor);

		return result;
	}
}
