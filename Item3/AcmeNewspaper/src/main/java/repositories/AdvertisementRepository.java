
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Advertisement;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {

	@Query("select a from Advertisement a where a.newspaper.id=?1")
	Collection<Advertisement> findByNewspaper(int id);

	@Query("select a from Advertisement a where a.taboo=1")
	Collection<Advertisement> findTaboo();

	@Query("select count(a)*100.0/(select count(na) from Advertisement na) from Advertisement a where a.taboo=true")
	Double ratioAdvertisementsTaboo();

}
