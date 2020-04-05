package org.hypnotriod.conversationengine.engine.service;

import java.util.List;
import org.hypnotriod.conversationengine.engine.entity.DialogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hypnotriod.conversationengine.engine.repository.DialogQueryRepository;

/**
 *
 * @author Ilya Pikin
 */
@Service
public class DialogQueryService {

    @Autowired
    DialogQueryRepository dialogQueryRepository;

    public void save(DialogQuery query) {
        dialogQueryRepository.save(query);
    }

    public List<DialogQuery> findAllMathces(String utterance, String context) {
        return dialogQueryRepository.findAllMathces(utterance, context);
    }
}
