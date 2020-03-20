package org.hypnotriod.conversationengine.engine.service;

import java.util.List;
import org.hypnotriod.conversationengine.engine.entity.SpokenQuery;
import org.hypnotriod.conversationengine.engine.repository.SpokenQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ilya Pikin
 */
@Service
public class SpokenQueryService {

    @Autowired
    SpokenQueryRepository spokenQueryRepository;

    public void add(SpokenQuery query) {
        spokenQueryRepository.save(query);
    }

    public List<SpokenQuery> findAllMathces(String query) {
        return spokenQueryRepository.findAllMathces(query);
    }
}
