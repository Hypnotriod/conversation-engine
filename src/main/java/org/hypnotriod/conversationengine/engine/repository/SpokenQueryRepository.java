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

    @Query(value = "SELECT * FROM spoken_query sq "
            + "LEFT JOIN spoken_query_spoken_context sqsc "
            + "ON sq.id = sqsc.spoken_query_id "
            + "LEFT JOIN spoken_context sc "
            + "ON sc.id = sqsc.spoken_context_id "
            + "WHERE sc.name = ?2 "
            + "AND ?1 ~ sq.regexp", nativeQuery = true)
    List<SpokenQuery> findAllMathces(String utterance, String context);
}
