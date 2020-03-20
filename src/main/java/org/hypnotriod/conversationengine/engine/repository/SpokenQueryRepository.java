package org.hypnotriod.conversationengine.engine.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.hypnotriod.conversationengine.engine.entity.SpokenQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ilya Pikin
 */
@Repository
@Transactional
public interface SpokenQueryRepository extends JpaRepository<SpokenQuery, Long> {

    @Query(value = "SELECT * FROM spoken_query WHERE ?1 ~ spoken_query.regexp", nativeQuery = true)
    List<SpokenQuery> findAllMathces(String query);
}
