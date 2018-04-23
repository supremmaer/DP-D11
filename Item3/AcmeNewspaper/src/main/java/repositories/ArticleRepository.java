
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	@Query("select a from Article a where a.publishMoment is not null and (a.title like CONCAT('%',?1,'%') or a.summary like CONCAT('%',?1,'%') or a.text like CONCAT('%',?1,'%'))")
	Collection<Article> findByKeyword(String keyword);

	@Query("select a from User u join u.articles a where u.id=?1 and a.publishMoment is not null")
	Collection<Article> findAllPublishedByUser(int userId);

	@Query("select a from Article a where a.publishMoment is not null")
	Collection<Article> findAllPublished();

	@Query("select a from User u join u.articles a where u.id=?1")
	Collection<Article> findByUser(int userId);

	@Query("select a from Article a where a.taboo=1")
	Collection<Article> findTaboo();

}
