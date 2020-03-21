package org.hypnotriod.conversationengine.engine.repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hypnotriod.conversationengine.engine.vo.UtteranceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
                "SELECT id, %s FROM %s WHERE %s = ?1",
                utteranceCustomData.getRepositoryKey(),
                utteranceCustomData.getRepositoryName(),
                utteranceCustomData.getRepositoryKey()
        );
    }
}
