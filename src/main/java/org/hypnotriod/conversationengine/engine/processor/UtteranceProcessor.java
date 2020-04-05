package org.hypnotriod.conversationengine.engine.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hypnotriod.conversationengine.engine.vo.RecognizedUtteranceCustomData;
import org.hypnotriod.conversationengine.engine.vo.RecognizedUtteranceData;
import org.hypnotriod.conversationengine.engine.entity.DialogQuery;
import org.hypnotriod.conversationengine.engine.vo.UtteranceData;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.hypnotriod.conversationengine.engine.service.CustomDataService;
import org.hypnotriod.conversationengine.engine.service.DialogQueryService;
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
    private DialogQueryService dialogQueryService;

    @Autowired
    private UtteranceDataParserService utteranceDataParserService;

    @Autowired
    private UtteranceCommandProcessor utteranceCommandProcessor;

    public UtteranceCommandHandlerResult process(String utterance, String contextName) {
        utterance = prepareUtterance(utterance);

        List<DialogQuery> matchedSpokenQuerys = dialogQueryService.findAllMathces(utterance, contextName);
        List<UtteranceRecognitionResult> utteranceRecognitionResults = processSpokenQueries(matchedSpokenQuerys, utterance, contextName);

        return utteranceCommandProcessor.processUtteranceRecognitionResults(utteranceRecognitionResults);
    }

    private String prepareUtterance(String utterance) {
        return StringUtils.trimTrailingWhitespace(utterance).toLowerCase();
    }

    private List<UtteranceRecognitionResult> processSpokenQueries(List<DialogQuery> dialogQueries, String utterance, String contextName) {
        List<UtteranceRecognitionResult> utteranceRecognitionResults = new ArrayList<>();

        dialogQueries.forEach((dialogQuery) -> {
            List<UtteranceData> utteranceDatas = utteranceDataParserService.parse(utterance, dialogQuery);
            UtteranceRecognitionResult utteranceRecognitionResult = proceesSpokenQueryDatas(utteranceDatas, dialogQuery, contextName);
            if (utteranceRecognitionResult != null) {
                utteranceRecognitionResults.add(utteranceRecognitionResult);
            }
        });

        return utteranceRecognitionResults;
    }

    private UtteranceRecognitionResult proceesSpokenQueryDatas(List<UtteranceData> utteranceDatas, DialogQuery dialogQuery, String contextName) {
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
                    dialogQuery.getQuery(),
                    dialogQuery.getLanguageCode(),
                    dialogQuery.getCommandName(),
                    contextName,
                    recognizedUtteranceDatas);
        } else {
            return null;
        }
    }
}
