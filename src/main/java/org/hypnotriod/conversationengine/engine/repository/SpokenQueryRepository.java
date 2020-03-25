package org.hypnotriod.conversationengine.engine.repository;

import static org.hypnotriod.conversationengine.engine.contants.QueryConstants.QUERY_SPOKEN_QUERY_ALL_PATTERN_MATCHES;
import org.hypnotriod.conversationengine.engine.entity.SpokenQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author Ilya Pikin
 */
@Repository
@Transactional
public interface SpokenQueryRepository extends JpaRepository<SpokenQuery, Long> {

    @Query(value = QUERY_SPOKEN_QUERY_ALL_PATTERN_MATCHES, nativeQuery = true)
    List<SpokenQuery> findAllMathces(String utterance, String context);
}
