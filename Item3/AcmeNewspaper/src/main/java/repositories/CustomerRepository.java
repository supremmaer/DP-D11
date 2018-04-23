
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select sum(c.newspapers.size)*100.0/(select count(cu) from Customer cu) from CreditCard c where c.newspapers.size >= 0")
	Double ratioSubscriptorsVScustomer();

}
