
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Actor;
import domain.Administrator;
import domain.Chirp;
import domain.User;

@Service
@Transactional
public class ChirpService {

	//Managed Repository ----
	@Autowired
	private ChirpRepository	chirpRepository;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private UserService		userService;

	@Autowired
	private ConfigService	configService;


	//Constructors
	public ChirpService() {
		super();
	}

	public Chirp create() {
		Chirp result;

		result = new Chirp();
		result.setContainsTaboo(false);

		return result;
	}

	public Collection<Chirp> findAll() {
		Collection<Chirp> result;

		result = this.chirpRepository.findAll();

		return result;
	}

	public void delete(final Chirp chirp) {
		User user;
		Collection<Chirp> chirps;

		Assert.isTrue(this.actorService.findByPrincipal() instanceof Administrator);
		user = this.userService.findByChirpId(chirp.getId());
		chirps = user.getChirps();
		chirps.remove(chirp);
		user.setChirps(chirps);
		this.userService.save(user);
		this.chirpRepository.delete(chirp);

	}

	public Chirp save(final Chirp chirp) {
		Chirp result;
		Actor principal;
		User owner;
		Collection<Chirp> chirps;

		Assert.isTrue(chirp.getId() == 0);

		principal = this.actorService.findByPrincipal();
		owner = (User) principal;
		chirp.setMoment(new Date());
		if (this.configService.isTaboo(chirp.getTitle()) || this.configService.isTaboo(chirp.getDescription()))
			chirp.setContainsTaboo(true);
		result = this.chirpRepository.save(chirp);
		chirps = owner.getChirps();
		chirps.add(result);
		owner.setChirps(chirps);
		this.userService.save(owner);

		return result;
	}
	public Chirp findOne(final int chirpId) {
		Chirp result;

		result = this.chirpRepository.findOne(chirpId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Chirp> findByUserId(final int userId) {
		Collection<Chirp> result;

		result = this.chirpRepository.findByUserId(userId);
		Assert.notNull(result);

		return result;
	}
	public Collection<Chirp> findByFollowed(final int userId) {
		Collection<Chirp> result;

		result = this.chirpRepository.findByUsersFollowed(userId);
		Assert.notNull(result);

		return result;
	}
	public Collection<Chirp> findByTabooWords() {
		final Collection<Chirp> result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		result = this.chirpRepository.findByTabooWords();

		return result;
	}
	public void flush() {
		this.chirpRepository.flush();
	}

}
