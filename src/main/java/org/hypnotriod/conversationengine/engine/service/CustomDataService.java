package org.hypnotriod.conversationengine.engine.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import org.hypnotriod.conversationengine.engine.vo.RecognizedCustomData;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCustomData;
import org.hypnotriod.conversationengine.engine.repository.CustomDataRepository;
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

    public List<RecognizedCustomData.IdValueMatch> getAllIdValueMatches(UtteranceCustomData utteranceCustomData) {
        List<Object[]> matches = customDataRepository.getAllIdValueMatches(utteranceCustomData);
        return convertToIdValueMathes(matches);
    }

    private List<RecognizedCustomData.IdValueMatch> convertToIdValueMathes(List<Object[]> idValueMatches) {
        return idValueMatches.stream().map(
                idValue -> new RecognizedCustomData.IdValueMatch(
                        ((BigInteger) idValue[0]).longValue(), (String) idValue[1]))
                .collect(Collectors.toList());
    }
}
