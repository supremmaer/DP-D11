
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import domain.CreditCard;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	//Managed Repository ----
	@Autowired
	private CustomerRepository	customerRepository;


	//Constructors
	public CustomerService() {
		super();
	}

	public Customer create() {
		Customer result;

		result = new Customer();
		result.setCreditCard(new HashSet<CreditCard>());

		return result;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> result;

		result = this.customerRepository.findAll();

		return result;
	}

	public void delete(final Customer customer) {

		this.customerRepository.delete(customer);

	}

	public Customer save(final Customer customer) {
		Customer result;

		result = this.customerRepository.save(customer);
		return result;
	}

	public Customer findOne(final int customerId) {
		Customer result;

		result = this.customerRepository.findOne(customerId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.customerRepository.flush();
	}

	public Double ratioSubscriptorsVScustomer() {
		return this.customerRepository.ratioSubscriptorsVScustomer();
	}

}
