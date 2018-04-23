
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FollowUp;

@Repository
public interface FollowUpRepository extends JpaRepository<FollowUp, Integer> {

	@Query("select f from FollowUp f where f.article.id=?1")
	Collection<FollowUp> findByArticleId(int id);

	@Query("select 1.0*count(f)/(select count(a) from Article a) from FollowUp f")
	Double avgFollowperArticle();

	@Query("select f from FollowUp f where f.publishMoment between f.article.publishMoment and f.article.publishMoment+7")
	Collection<FollowUp> followsOneWeakPublicated();

	@Query("select f from FollowUp f where f.publishMoment between f.article.publishMoment and f.article.publishMoment+14")
	Collection<FollowUp> followTwoWeakPublicated();

}
