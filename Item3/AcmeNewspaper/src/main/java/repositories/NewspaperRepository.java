
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Newspaper;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Integer> {

	@Query("select n from Newspaper n where n.title like CONCAT('%',?1,'%') or n.description like CONCAT('%',?1,'%')")
	Collection<Newspaper> findByCriteria(String criteria);

	@Query("select n from Newspaper n join n.articles a where a.id=?1")
	Newspaper findByArticleId(int id);

	@Query("select n from Newspaper n where n.taboo=1")
	Collection<Newspaper> findTaboo();

	@Query("select c.newspapers from CreditCard c where c.id=?1")
	Collection<Newspaper> findByCreditCardID(int cardID);

	@Query("select c.newspapers from Customer cu join cu.creditCard c where cu.id=?1")
	Collection<Newspaper> findByCustomerID(int customerID);

	//dashboard

	@Query("select avg(n.articles.size) from Newspaper n")
	Double averageArticlesPerNewspaper();

	//Standar Deviation
	@Query("select sqrt(sum(n.articles.size*n.articles.size) / count(n.articles.size) - (avg(n.articles.size) * avg(n.articles.size))) from Newspaper n")
	Double standardArticlesPerNewspaper();

	@Query("select n from Newspaper n where n.articles.size >= (select avg(tn.articles.size)*1.1 from Newspaper tn) order by n.articles.size DESC")
	Collection<Newspaper> findNewspapersWithMoreArticlesThanAverage();

	@Query("select n from Newspaper n where n.articles.size <= (select avg(tn.articles.size)*1.1 from Newspaper tn) order by n.articles.size DESC")
	Collection<Newspaper> findNewspapersWithLessArticlesThanAverage();

	@Query("select count(n)*100.0/(select count(nu) from Newspaper nu where nu.publicity=true) from Newspaper n where n.publicity=false")
	Double ratioPublicVsPrivate();

	@Query("select avg(n.articles.size) from Newspaper n where n.publicity=true")
	Double averageArticlesPerPrivateNewspaper();

	@Query("select avg(n.articles.size) from Newspaper n where n.publicity=false")
	Double averageArticlesPerPublicNewspaper();

	@Query("select n from User u join u.newspapers n where n.publicity=true and u.id=?1")
	Collection<Newspaper> findNewPrivateByUser(int id);

	@Query("select n from User u join u.newspapers n where n.publicity=false and u.id=?1")
	Collection<Newspaper> findNewPublicByUser(int id);

	//dashboard
}
