package org.hypnotriod.conversationengine.engine.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hypnotriod.conversationengine.engine.vo.RecognizedUtteranceCustomData;
import org.hypnotriod.conversationengine.engine.vo.RecognizedUtteranceData;
import org.hypnotriod.conversationengine.engine.entity.SpokenQuery;
import org.hypnotriod.conversationengine.engine.vo.UtteranceData;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.hypnotriod.conversationengine.engine.service.CustomDataService;
import org.hypnotriod.conversationengine.engine.service.SpokenQueryService;
import org.hypnotriod.conversationengine.engine.service.UtteranceDataParserService;
import org.hypnotriod.conversationengine.engine.vo.CustomDataMatch;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class UtteranceProcessor {

    @Autowired
    private CustomDataService customDataService;

    @Autowired
    private SpokenQueryService spokenQueryService;

    @Autowired
    private UtteranceDataParserService utteranceDataParserService;

    @Autowired
    private UtteranceCommandProcessor utteranceCommandProcessor;

    public UtteranceCommandHandlerResult process(String utterance, String context) {
        utterance = prepareUtterance(utterance);

        List<SpokenQuery> matchedSpokenQuerys = spokenQueryService.findAllMathces(utterance, context);
        List<UtteranceRecognitionResult> utteranceRecognitionResults = processSpokenQueries(matchedSpokenQuerys, utterance, context);

        return utteranceCommandProcessor.processUtteranceRecognitionResults(utteranceRecognitionResults);
    }

    private String prepareUtterance(String utterance) {
        return StringUtils.trimTrailingWhitespace(utterance).toLowerCase();
    }

    private List<UtteranceRecognitionResult> processSpokenQueries(List<SpokenQuery> spokenQueries, String utterance, String context) {
        List<UtteranceRecognitionResult> utteranceRecognitionResults = new ArrayList<>();

        spokenQueries.forEach((spokenQuery) -> {
            List<UtteranceData> utteranceDatas = utteranceDataParserService.parse(utterance, spokenQuery);
            UtteranceRecognitionResult utteranceRecognitionResult = proceesSpokenQueryDatas(utteranceDatas, spokenQuery, context);
            if (utteranceRecognitionResult != null) {
                utteranceRecognitionResults.add(utteranceRecognitionResult);
            }
        });

        return utteranceRecognitionResults;
    }

    private UtteranceRecognitionResult proceesSpokenQueryDatas(List<UtteranceData> utteranceDatas, SpokenQuery spokenQuery, String context) {
        Map<String, RecognizedUtteranceData> recognizedUtteranceDatas = new HashMap<>();

        utteranceDatas.forEach((utteranceData) -> {
            if (utteranceData.getRepositoryKey() != null && utteranceData.getRepositoryName() != null) {
                List<CustomDataMatch> customDataMatches = customDataService.getAllMatches(utteranceData);

                if (!customDataMatches.isEmpty()) {
                    recognizedUtteranceDatas.put(
                            utteranceData.getKey(),
                            new RecognizedUtteranceCustomData(
                                    utteranceData.getKey(),
                                    utteranceData.getValue(),
                                    utteranceData.getRepositoryName(),
                                    utteranceData.getRepositoryKey(),
                                    customDataMatches));
                }
            } else {
                recognizedUtteranceDatas.put(
                        utteranceData.getKey(),
                        new RecognizedUtteranceData(utteranceData.getKey(), utteranceData.getValue()));
            }
        });

        if (utteranceDatas.size() == recognizedUtteranceDatas.size()) {
            return new UtteranceRecognitionResult(
                    spokenQuery.getQuery(),
                    spokenQuery.getLanguage(),
                    spokenQuery.getCommand(),
                    context,
                    recognizedUtteranceDatas);
        } else {
            return null;
        }
    }
}
