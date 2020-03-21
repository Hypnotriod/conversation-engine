package org.hypnotriod.conversationengine.engine.processor;

import java.util.ArrayList;
import java.util.List;
import org.hypnotriod.conversationengine.engine.vo.RecognizedUtteranceCustomData;
import org.hypnotriod.conversationengine.engine.vo.RecognizedUtteranceData;
import org.hypnotriod.conversationengine.engine.entity.SpokenQuery;
import org.hypnotriod.conversationengine.engine.vo.UtteranceData;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.hypnotriod.conversationengine.engine.service.CustomDataService;
import org.hypnotriod.conversationengine.engine.service.SpokenQueryService;
import org.hypnotriod.conversationengine.engine.service.UtteranceDataParserService;
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
    DialogState dialogState; //todo: find better solution?

    @Autowired
    CustomDataService customDataService;

    @Autowired
    SpokenQueryService spokenQueryService;

    @Autowired
    UtteranceDataParserService utteranceDataParserService;

    public void process(String utterance) {
        utterance = prepareUtterance(utterance);

        List<SpokenQuery> matchedSpokenQuerys = spokenQueryService.findAllMathces(utterance, dialogState.getContext());
        List<UtteranceRecognitionResult> utteranceRecognitionResults = processSpokenQueries(matchedSpokenQuerys, utterance);

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
            List<UtteranceData> utteranceDatas = utteranceDataParserService.parse(utterance, spokenQuery);
            UtteranceRecognitionResult utteranceRecognitionResult = proceesSpokenQueryDatas(utteranceDatas, spokenQuery);
            if (utteranceRecognitionResult != null) {
                utteranceRecognitionResults.add(utteranceRecognitionResult);
            }
        });

        return utteranceRecognitionResults;
    }

    private UtteranceRecognitionResult proceesSpokenQueryDatas(List<UtteranceData> utteranceDatas, SpokenQuery spokenQuery) {
        List<RecognizedUtteranceData> recognizedUtteranceDatas = new ArrayList<>();

        utteranceDatas.forEach((utteranceData) -> {
            if (utteranceData.getRepositoryKey() != null && utteranceData.getRepositoryName() != null) {
                List<RecognizedUtteranceCustomData.IdValueMatch> idValueMatches = customDataService.getAllIdValueMatches(utteranceData);

                if (!idValueMatches.isEmpty()) {
                    recognizedUtteranceDatas.add(new RecognizedUtteranceCustomData(
                            utteranceData.getRepositoryName(),
                            utteranceData.getRepositoryKey(),
                            utteranceData.getValue(),
                            idValueMatches));
                }
            } else {
                recognizedUtteranceDatas.add(new RecognizedUtteranceData(utteranceData.getValue()));
            }
        });

        if (utteranceDatas.size() == recognizedUtteranceDatas.size()) {
            return new UtteranceRecognitionResult(
                    spokenQuery.getQuery(),
                    spokenQuery.getLanguage(),
                    spokenQuery.getCommand(),
                    dialogState.getContext(),
                    recognizedUtteranceDatas);
        } else {
            return null;
        }
    }
}
