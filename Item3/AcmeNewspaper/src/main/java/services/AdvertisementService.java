
package services;

import java.util.Collection;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdvertisementRepository;

import domain.Advertisement;



@Service
@Transactional
public class AdvertisementService {

	//Managed Repository ----
	@Autowired
	private AdvertisementRepository advertisementRepository;

	//Constructors
	public AdvertisementService() {
		super();
	}

	public Advertisement create() {
		Advertisement result;

		result = new Advertisement();

		return result;
	}

	public Collection<Advertisement> findAll() {
		Collection<Advertisement> result;

		result = this.advertisementRepository.findAll();

		return result;
	}

	public void delete(final Advertisement advertisement) {

		this.advertisementRepository.delete(advertisement);

	}

	public Advertisement save(final Advertisement advertisement) {
		Advertisement result;

		result = this.advertisementRepository.save(advertisement);
		return result;
	}

	public Advertisement findOne(final int advertisementId) {
		Advertisement result;

		result = this.advertisementRepository.findOne(advertisementId);
		Assert.notNull(result);

		return result;
	}

}
