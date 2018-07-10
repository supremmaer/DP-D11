
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdvertisementRepository;
import domain.Advertisement;
import domain.Agent;
import domain.CreditCard;
import domain.Newspaper;

@Service
@Transactional
public class AdvertisementService {

	//Managed Repository ----
	@Autowired
	private AdvertisementRepository	advertisementRepository;

	@Autowired
	private ConfigService			configService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CreditCardService		creditCardService;


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
		final Newspaper newspaper = advertisement.getNewspaper();
		final CreditCard creditCard = advertisement.getCreditCard();
		Agent agent;
		agent = (Agent) this.actorService.findByPrincipal();
		Assert.isTrue(creditCard != null, "creditCard.error.null");
		Assert.isTrue(agent.getId() == creditCard.getAgent().getId());

		final DateTime dt = new DateTime();
		final int mes = advertisement.getCreditCard().getExpirationMonth();
		final int anio = advertisement.getCreditCard().getExpirationYear();
		Assert.isTrue(dt.getYear() <= anio, "creditCard.error.expired");
		if (dt.getYear() == anio)
			Assert.isTrue(dt.getMonthOfYear() < mes, "creditCard.error.expired");
		Advertisement result;

		advertisement.setAgent(agent);
		result = this.advertisementRepository.save(advertisement);

		final Collection<Newspaper> aux = creditCard.getNewspapers();
		aux.add(newspaper);
		creditCard.setNewspapers(aux);
		this.creditCardService.saveAddNewspaper(creditCard);

		this.isTaboo(result.getTitle(), result);
		this.isTaboo(result.getBanner(), result);
		this.isTaboo(result.getTargetPage(), result);
		return result;
	}

	public Advertisement findOne(final int advertisementId) {
		Advertisement result;

		result = this.advertisementRepository.findOne(advertisementId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Advertisement> findByNewspaper(final int adverisementID) {
		Collection<Advertisement> result;
		result = this.advertisementRepository.findByNewspaper(adverisementID);
		return result;
	}

	//Other business methods ----------------------------------------------------

	public Boolean isTaboo(final String string, final Advertisement advertisement) {
		Boolean result = false;
		String lower;
		if (string != null) {
			lower = string.toLowerCase();

			if (advertisement.getTitle().contains(lower)) {
				advertisement.setTaboo(true);
				result = true;
				this.advertisementRepository.save(advertisement);
			}
		}
		return result;
	}
	public Collection<Advertisement> findTaboo() {
		Collection<Advertisement> result;

		result = this.advertisementRepository.findTaboo();

		return result;

	}
	public void flush() {
		this.advertisementRepository.flush();
	}

	//Dashboard

	public Double ratioAdvertisementsTaboo() {
		return this.advertisementRepository.ratioAdvertisementsTaboo();
	}

	//Dashboard

}
