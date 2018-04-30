
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.Actor;
import domain.Agent;
import domain.CreditCard;
import domain.Customer;
import domain.Newspaper;
import domain.Volume;
import forms.SubscribeForm;
import forms.SubscribeVolumeForm;

@Service
@Transactional
public class CreditCardService {

	//Managed Repository ----
	@Autowired
	private CreditCardRepository	creditCardRepository;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private NewspaperService		newspaperService;


	//Constructors
	public CreditCardService() {
		super();
	}

	public CreditCard create() {
		CreditCard result;

		final Collection<Newspaper> newspapers = new ArrayList<Newspaper>();
		final Collection<Volume> volumes = new ArrayList<Volume>();

		result = new CreditCard();

		result.setNewspapers(newspapers);
		result.setVolumes(volumes);

		return result;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result;

		result = this.creditCardRepository.findAll();

		return result;
	}

	public void delete(final CreditCard creditCard) {

		this.creditCardRepository.delete(creditCard);

	}

	public CreditCard save(final CreditCard creditCard) {
		CreditCard result;

		Collection<CreditCard> aux;
		Customer customer;
		customer = (Customer) this.actorService.findByPrincipal();
		result = this.creditCardRepository.save(creditCard);

		//añado la creditCard al customer
		aux = customer.getCreditCard();
		aux.add(result);
		customer.setCreditCard(aux);
		this.customerService.save(customer);
		return result;
	}

	public CreditCard saveCCAgent(final CreditCard creditCard) {//El save para guardar creditcard para agentes

		CreditCard result;
		Agent agent;
		agent = (Agent) this.actorService.findByPrincipal();
		creditCard.setAgent(agent);

		result = this.creditCardRepository.save(creditCard);

		return result;
	}

	public CreditCard saveAddNewspaper(final CreditCard creditCard) {
		CreditCard result;

		result = this.creditCardRepository.save(creditCard);

		return result;
	}

	public CreditCard findOne(final int creditCardId) {
		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.creditCardRepository.flush();
	}

	public Collection<CreditCard> findByCustomer(final int customerid) {
		return this.creditCardRepository.findByCustomer(customerid);
	}

	public Collection<CreditCard> findByAgent(final int agentid) {
		return this.creditCardRepository.findByAgent(agentid);
	}

	public SubscribeForm createForm(final Newspaper newspaper) {
		SubscribeForm result;

		result = new SubscribeForm();
		result.setNewspaper(newspaper);

		return result;
	}

	public SubscribeVolumeForm createForm(final Volume volume) {
		SubscribeVolumeForm result;

		result = new SubscribeVolumeForm();
		result.setVolume(volume);

		return result;
	}

	public void subscribe(final SubscribeForm subscribeForm) {

		final Newspaper newspaper = subscribeForm.getNewspaper();
		final CreditCard creditCard = subscribeForm.getCreditCard();

		final Collection<Newspaper> aux = creditCard.getNewspapers();
		aux.add(newspaper);
		creditCard.setNewspapers(aux);
		this.saveAddNewspaper(creditCard);

	}

	public void subscribe(final SubscribeVolumeForm subscribeVolumeForm) {
		Volume volume;
		CreditCard creditCard;
		Collection<Volume> aux;
		volume = subscribeVolumeForm.getVolume();
		creditCard = subscribeVolumeForm.getCreditCard();
		Actor principal;
		Customer customer;

		principal = this.actorService.findByPrincipal();

		Assert.isTrue(principal instanceof Customer);

		customer = (Customer) principal;

		Assert.isTrue(!creditCard.getVolumes().contains(volume));
		Assert.isTrue(customer.getCreditCard().contains(creditCard));

		aux = creditCard.getVolumes();
		aux.add(volume);
		creditCard.setVolumes(aux);
		this.saveAddNewspaper(creditCard);

	}

}
