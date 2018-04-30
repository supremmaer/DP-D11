
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

	@Query("select c.creditCard from Customer c where c.id=?1")
	Collection<CreditCard> findByCustomer(int customerid);

	@Query("select c from CreditCard c where c.agent.id=?1")
	Collection<CreditCard> findByAgent(int agentid);

	@Query("select c from CreditCard c join c.newspapers n where n.id=?1")
	Collection<CreditCard> findByNewspaper(int newspaperId);
}
