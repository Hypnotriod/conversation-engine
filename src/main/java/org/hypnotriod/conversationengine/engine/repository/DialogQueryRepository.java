package org.hypnotriod.conversationengine.engine.repository;

import org.hypnotriod.conversationengine.engine.entity.DialogQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import javax.transaction.Transactional;
import static org.hypnotriod.conversationengine.engine.contants.QueryConstants.QUERY_DIALOG_QUERY_ALL_PATTERN_MATCHES;

/**
 *
 * @author Ilya Pikin
 */
@Repository
@Transactional
public interface DialogQueryRepository extends JpaRepository<DialogQuery, Long> {

    @Query(value = QUERY_DIALOG_QUERY_ALL_PATTERN_MATCHES, nativeQuery = true)
    List<DialogQuery> findAllMathces(String utterance, String context);
}
