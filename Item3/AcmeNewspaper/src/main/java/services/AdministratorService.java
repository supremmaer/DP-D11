
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	//Managed Repository ----
	@Autowired
	private AdministratorRepository	administratorRepository;


	//Constructors
	public AdministratorService() {
		super();
	}

	public Administrator create() {
		Administrator result;

		result = new Administrator();

		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();

		return result;
	}

	public void delete(final Administrator administrator) {

		this.administratorRepository.delete(administrator);

	}

	public Administrator save(final Administrator administrator) {
		Administrator result;

		result = this.administratorRepository.save(administrator);
		return result;
	}

	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.administratorRepository.flush();
	}

}
