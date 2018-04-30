
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Volume;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Integer> {

	@Query("select v from Volume v where v.user.id=?1")
	Collection<Volume> findByPrincipalId(int id);

	@Query("select c.volumes from Customer cu join cu.creditCard c where cu.id=?1")
	Collection<Volume> findByCustomerID(int customerID);

	@Query("select v from Volume v join v.newspapers n where n.id=?1")
	Collection<Volume> findByNewspaper(int id);

}
