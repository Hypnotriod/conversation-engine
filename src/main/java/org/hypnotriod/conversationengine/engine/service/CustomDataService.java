package org.hypnotriod.conversationengine.engine.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import org.hypnotriod.conversationengine.engine.vo.UtteranceData;
import org.hypnotriod.conversationengine.engine.repository.CustomDataRepository;
import org.hypnotriod.conversationengine.engine.vo.CustomDataMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ilya Pikin
 */
@Service
public class CustomDataService {

    @Autowired
    CustomDataRepository customDataRepository;

    public List<CustomDataMatch> getAllMatches(UtteranceData utteranceData) {
        List<Object[]> matches = customDataRepository.getAllIdValueMatches(utteranceData);
        return convertToCustomDataMathes(matches, utteranceData);
    }

    private List<CustomDataMatch> convertToCustomDataMathes(List<Object[]> idValueMatches, UtteranceData utteranceData) {
        return idValueMatches.stream()
                .map(idValue -> new CustomDataMatch(
                    ((BigInteger) idValue[0]).longValue(),
                    (String) idValue[1],
                    utteranceData.getValue().equals(idValue[1])))
                .collect(Collectors.toList());
    }
}
