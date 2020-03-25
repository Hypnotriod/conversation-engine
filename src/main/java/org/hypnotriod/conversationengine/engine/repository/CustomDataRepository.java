package org.hypnotriod.conversationengine.engine.repository;

import static org.hypnotriod.conversationengine.engine.contants.QueryConstants.QUERY_CUSTOM_DATA_ID_VALUE_MATCHES;
import org.hypnotriod.conversationengine.engine.vo.UtteranceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Ilya Pikin
 */
@Repository
public class CustomDataRepository {

    @Autowired
    EntityManager entityManager;

    public List<Object[]> getAllIdValueMatches(UtteranceData utteranceCustomData) {
        List<Object[]> resultList = new ArrayList<>();
        Query query = entityManager.createNativeQuery(
                buildSelectAllIdValueFromTableWherevalueLikeQuery(utteranceCustomData))
                .setParameter(1, utteranceCustomData.getValue());
        try {
            resultList = query.getResultList();
        } catch (Exception ex) {
        }
        return resultList;
    }

    private String buildSelectAllIdValueFromTableWherevalueLikeQuery(UtteranceData utteranceCustomData) {
        return String.format(
                QUERY_CUSTOM_DATA_ID_VALUE_MATCHES,
                utteranceCustomData.getRepositoryKey(),
                utteranceCustomData.getRepositoryName(),
                utteranceCustomData.getRepositoryKey()
        );
    }
}
