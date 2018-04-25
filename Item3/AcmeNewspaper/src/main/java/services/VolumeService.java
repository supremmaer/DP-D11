
package services;

import java.util.Collection;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.VolumeRepository;

import domain.Volume;



@Service
@Transactional
public class VolumeService {

	//Managed Repository ----
	@Autowired
	private VolumeRepository volumeRepository;

	//Constructors
	public VolumeService() {
		super();
	}

	public Volume create() {
		Volume result;

		result = new Volume();

		return result;
	}

	public Collection<Volume> findAll() {
		Collection<Volume> result;

		result = this.volumeRepository.findAll();

		return result;
	}

	public void delete(final Volume volume) {

		this.volumeRepository.delete(volume);

	}

	public Volume save(final Volume volume) {
		Volume result;

		result = this.volumeRepository.save(volume);
		return result;
	}

	public Volume findOne(final int volumeId) {
		Volume result;

		result = this.volumeRepository.findOne(volumeId);
		Assert.notNull(result);

		return result;
	}

}
