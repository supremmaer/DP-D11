
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u join u.users f where f.id=?1")
	Collection<User> findFollowingMe(int id);

	@Query("select u from User u where (select a from Article a where a.id = ?1) member of u.articles")
	User UserByArticle(int articleId);

	@Query("select u from User u join u.chirps c where c.id =?1")
	User findByChirpId(int id);

	@Query("select u from User u join u.articles a where a.id =?1")
	User findByArticleId(int id);

	@Query("select u from User u join u.newspapers n where n.id =?1")
	User findByNewspaperId(int id);

	//----dashboard

	@Query("select avg(u.newspapers.size) from User u")
	Double averageNewspaperperUser();

	//Standar Deviation
	@Query("select sqrt(sum(u.newspapers.size*u.newspapers.size) / count(u.newspapers.size) - (avg(u.newspapers.size) * avg(u.newspapers.size))) from User u")
	Double standardNewspaperperUser();

	@Query("select avg(u.articles.size) from User u")
	Double averageArticlesperUser();

	//Standar Deviation
	@Query("select sqrt(sum(u.articles.size*u.articles.size) / count(u.articles.size) - (avg(u.articles.size) * avg(u.articles.size))) from User u")
	Double standarArticlesperUser();

	@Query("select count(u)*100.0/(select count(n) from User n) from User u where u.newspapers.size>0")
	Double ratioWriterNespaper();

	@Query("select count(u)*100.0/(select count(n) from User n) from User u where u.articles.size>0")
	Double ratioWritersArticle();

	@Query("select avg(u.chirps.size) from User u")
	Double avgChirpsPerUser();

	@Query("select sqrt(sum(u.chirps.size*u.chirps.size) / count(u.chirps.size) - (avg(u.chirps.size.size) * avg(u.chirps.size.size))) from User u")
	Double standardChirpsperUser();

	@Query("select count(u)*100.0/(select count(n) from User n) from User u where u.chirps.size >= (select avg(un.chirps.size)*1.75 from User un)")
	Double ratioUserwithMoreChirps();

	//---dasboard fin		
}
