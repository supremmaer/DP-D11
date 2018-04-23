
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {

	@Query("select c from User u join u.chirps c where u.id=?1")
	Collection<Chirp> findByUserId(int id);

	@Query("select c from User u join u.users f join f.chirps c where u.id=?1")
	Collection<Chirp> findByUsersFollowed(int id);

	@Query("select c from Chirp c where c.containsTaboo=true")
	Collection<Chirp> findByTabooWords();

}
