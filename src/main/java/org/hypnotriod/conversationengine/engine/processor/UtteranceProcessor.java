package org.hypnotriod.conversationengine.engine.processor;

import java.util.ArrayList;
import java.util.List;
import org.hypnotriod.conversationengine.engine.vo.RecognizedCustomData;
import org.hypnotriod.conversationengine.engine.entity.SpokenQuery;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCustomData;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.hypnotriod.conversationengine.engine.service.CustomDataService;
import org.hypnotriod.conversationengine.engine.service.SpokenQueryService;
import org.hypnotriod.conversationengine.engine.service.UtteranceCustomDataParserService;
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
    DialogState dialogState;

    @Autowired
    CustomDataService customDataService;

    @Autowired
    SpokenQueryService spokenQueryService;

    @Autowired
    UtteranceCustomDataParserService utteranceCustomDataParserService;

    public void process(String utterance) {
        utterance = prepareUtterance(utterance);

        List<UtteranceRecognitionResult> utteranceRecognitionResults
                = processSpokenQueries(spokenQueryService.findAllMathces(utterance), utterance);

        if (utteranceRecognitionResults.size() > 0) {
            System.out.println(utteranceRecognitionResults.toString());
        }
    }

    private String prepareUtterance(String utterance) {
        return StringUtils.trimTrailingWhitespace(utterance).toLowerCase();
    }

    private List<UtteranceRecognitionResult> processSpokenQueries(List<SpokenQuery> spokenQueries, String utterance) {
        List<UtteranceRecognitionResult> utteranceRecognitionResults = new ArrayList<>();

        spokenQueries.forEach((spokenQuery) -> {
            List<UtteranceCustomData> utteranceCustomDatas = utteranceCustomDataParserService.parse(utterance, spokenQuery);
            UtteranceRecognitionResult utteranceRecognitionResult = proceesSpokenQueryDatas(utteranceCustomDatas, spokenQuery);
            if (utteranceRecognitionResult != null) {
                utteranceRecognitionResults.add(utteranceRecognitionResult);
            }
        });

        return utteranceRecognitionResults;
    }

    private UtteranceRecognitionResult proceesSpokenQueryDatas(List<UtteranceCustomData> utteranceCustomDatas, SpokenQuery spokenQuery) {
        List<RecognizedCustomData> recognizedCustomDatas = new ArrayList<>();

        utteranceCustomDatas.forEach((utteranceCustomData) -> {
            List<RecognizedCustomData.IdValueMatch> idValueMatches = customDataService.getAllIdValueMatches(utteranceCustomData);

            if (!idValueMatches.isEmpty()) {
                recognizedCustomDatas.add(new RecognizedCustomData(
                        utteranceCustomData.getTableName(),
                        utteranceCustomData.getTableKey(),
                        idValueMatches));
            }
        });

        if (utteranceCustomDatas.size() == recognizedCustomDatas.size()) {
            return new UtteranceRecognitionResult(
                    spokenQuery.getQuery(),
                    spokenQuery.getLanguage(),
                    spokenQuery.getCommand(),
                    dialogState.getContext(),
                    recognizedCustomDatas);
        } else {
            return null;
        }
    }
}
