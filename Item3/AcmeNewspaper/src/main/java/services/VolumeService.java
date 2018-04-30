
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.VolumeRepository;
import domain.Actor;
import domain.Newspaper;
import domain.User;
import domain.Volume;

@Service
@Transactional
public class VolumeService {

	//Managed Repository ----
	@Autowired
	private VolumeRepository	volumeRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NewspaperService	newspaperService;


	//Constructors
	public VolumeService() {
		super();
	}

	public Volume create() {
		Volume result;
		Collection<Newspaper> newspapers;
		User user;

		user = (User) this.actorService.findByPrincipal();
		newspapers = new ArrayList<Newspaper>();
		result = new Volume();
		result.setNewspapers(newspapers);
		result.setUser(user);

		return result;
	}

	public Collection<Volume> findAll() {
		Collection<Volume> result;

		result = this.volumeRepository.findAll();

		return result;
	}

	public Collection<Volume> findByPrincipal() {
		Collection<Volume> result;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		result = this.volumeRepository.findByPrincipalId(actor.getId());

		return result;
	}

	public void delete(final Volume volume) {

		this.volumeRepository.delete(volume);

	}

	public Volume save(final Volume volume) {
		Volume result;
		Collection<Newspaper> newspapers;
		User user;

		user = (User) this.actorService.findByPrincipal();
		newspapers = new ArrayList<Newspaper>();
		if (volume.getId() == 0) {
			volume.setNewspapers(newspapers);
			volume.setUser(user);
		}
		Assert.isTrue(volume.getUser().getId() == user.getId(), "volume.error.author");
		result = this.volumeRepository.save(volume);

		return result;
	}
	public Volume saveByAdmin(final Volume volume) {
		Volume result;

		result = this.volumeRepository.save(volume);

		return result;
	}

	public Collection<Volume> findByCustomerId(final int id) {
		Collection<Volume> result;

		result = this.volumeRepository.findByCustomerID(id);

		return result;
	}

	public Volume findOne(final int volumeId) {
		Volume result;

		result = this.volumeRepository.findOne(volumeId);
		Assert.notNull(result);

		return result;
	}

	public Volume addNewspaper(final Integer volumeId, final Integer newspaperId) {
		Volume result, volume;
		Newspaper newspaper;

		volume = this.findOne(volumeId);
		newspaper = this.newspaperService.findOne(newspaperId);
		volume.addNewspapers(newspaper);
		result = this.save(volume);

		return result;
	}

	public Volume removeNewspaper(final int volumeId, final int newspaperId) {
		Volume result, volume;
		Newspaper newspaper;

		volume = this.findOne(volumeId);
		newspaper = this.newspaperService.findOne(newspaperId);
		volume.removeNewspapers(newspaper);
		result = this.save(volume);

		return result;

	}
	public Volume removeNewspaperByAdmin(final int volumeId, final int newspaperId) {
		Volume result, volume;
		Newspaper newspaper;

		volume = this.findOne(volumeId);
		newspaper = this.newspaperService.findOne(newspaperId);
		volume.removeNewspapers(newspaper);
		result = this.saveByAdmin(volume);

		return result;

	}

	public void flush() {
		this.volumeRepository.flush();
	}

	public Collection<Volume> findByNewspaper(final int id) {
		return this.volumeRepository.findByNewspaper(id);
	}

	//dashboard

	public Double averageNewspaperPerVolumen() {
		return this.volumeRepository.averageNewspaperPerVolumen();
	}

	//dashboard
}
