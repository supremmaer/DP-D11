
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.Agent;
import domain.CreditCard;
import domain.Customer;
import domain.Newspaper;
import domain.Volume;
import forms.SubscribeForm;

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
		result.setAgent(null);

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
		final CreditCard result = creditCard;

		Collection<CreditCard> aux;
		Customer customer;
		customer = (Customer) this.actorService.findByPrincipal();
		this.creditCardRepository.save(result);

		//a�ado la creditCard al customer
		aux = customer.getCreditCard();
		aux.add(result);
		customer.setCreditCard(aux);
		this.customerService.save(customer);
		return result;
	}

	public CreditCard saveCCAgent(final CreditCard creditCard) {//El save para guardar creditcard para agentes

		final CreditCard result = creditCard;
		Agent agent;
		agent = (Agent) this.actorService.findByPrincipal();
		result.setAgent(agent);

		this.creditCardRepository.save(creditCard);

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

	public SubscribeForm createForm(final Newspaper newspaper) {
		SubscribeForm result;

		result = new SubscribeForm();
		result.setNewspaper(newspaper);

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

}
