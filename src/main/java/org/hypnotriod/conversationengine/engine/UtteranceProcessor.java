package org.hypnotriod.conversationengine.engine;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author Ilya Pikin
 */
@Component
class UtteranceProcessor {

    @Autowired
    private CustomDataService customDataService;

    @Autowired
    private SpokenQueryService spokenQueryService;

    @Autowired
    private UtteranceDataParserService utteranceDataParserService;

    private final Map<String, UtteranceCommand> utteranceCommands = new HashMap<>();

    public void registerUtteranceCommand(UtteranceCommand command) {
        UtteranceCommandName commandName = AnnotationUtils.findAnnotation(command.getClass(), UtteranceCommandName.class);

        if (commandName == null) {
            throw new RuntimeException("Command " + command + " should have @UtteranceCommandName annotation");
        }
        if (utteranceCommands.containsKey(commandName.value())) {
            throw new RuntimeException("Command with " + commandName.value() + " already duplicated");
        }
        utteranceCommands.put(commandName.value(), command);
    }

    public void process(String utterance, String context) {
        utterance = prepareUtterance(utterance);

        List<SpokenQuery> matchedSpokenQuerys = spokenQueryService.findAllMathces(utterance, context);
        List<UtteranceRecognitionResult> utteranceRecognitionResults = processSpokenQueries(matchedSpokenQuerys, utterance, context);

        for (UtteranceRecognitionResult utteranceRecognitionResult : utteranceRecognitionResults) {
            UtteranceCommand command = utteranceCommands.get(utteranceRecognitionResult.getCommand());
            if (command != null && command.execute(utteranceRecognitionResult) == true) {
                break;
            } else {
                System.out.println(utteranceRecognitionResults.toString());
            }
        }
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
                    context,
                    recognizedUtteranceDatas);
        } else {
            return null;
        }
    }
}
