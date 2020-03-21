package org.hypnotriod.conversationengine.engine.service;

import org.hypnotriod.conversationengine.engine.entity.SpokenQuery;
import org.hypnotriod.conversationengine.engine.vo.UtteranceData;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import static org.hypnotriod.conversationengine.engine.contants.TextManipulationConstants.UTTERANCE_DATA_PATTERN_REGEX;
import static org.hypnotriod.conversationengine.engine.contants.TextManipulationConstants.UTTERANCE_DATA_PATTERN;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class UtteranceDataParserService {

    public List<UtteranceData> parse(String utterance, SpokenQuery spokenQuery) {
        List<String> utteranceDatas = extractDatasFromUserUtterance(utterance, spokenQuery.getQuery());
        return parseSpokenQueryDatas(utteranceDatas, spokenQuery);
    }

    private List<String> extractDatasFromUserUtterance(String utterance, String query) {
        List<String> result = new ArrayList<>();
        String[] dummyLoads = query.split(UTTERANCE_DATA_PATTERN_REGEX);

        for (int i = 0; i < dummyLoads.length; i++) {
            int dataStartIndex = utterance.indexOf(dummyLoads[i]) + dummyLoads[i].length();
            int dataEndIndex = (i + 1 == dummyLoads.length)
                    ? utterance.length()
                    : utterance.indexOf(dummyLoads[i + 1]);
            String data = utterance.substring(dataStartIndex, dataEndIndex);
            result.add(data);
        }

        return result;
    }

    private List<UtteranceData> parseSpokenQueryDatas(List<String> utteranceDatas, SpokenQuery spokenQuery) {
        List<UtteranceData> result = new ArrayList<>();
        Matcher matcher = UTTERANCE_DATA_PATTERN.matcher(spokenQuery.getQuery());

        while (matcher.find()) {
            String dataPattern = matcher.group();
            String utteranceData = utteranceDatas.remove(0);

            result.add(parseUtteranceData(dataPattern, utteranceData));
        }

        return result;
    }

    private UtteranceData parseUtteranceData(String dataPattern, String utteranceData) {
        String trimmedPattern = dataPattern.substring(1, dataPattern.length() - 1);
        if (trimmedPattern.contains("?")) {
            return new UtteranceData(utteranceData);
        } else {
            String[] repositoryNameKey = trimmedPattern.split("\\.");
            String repositoryName = repositoryNameKey[0];
            String repositoryKey = repositoryNameKey[1];

            return new UtteranceData(repositoryName, repositoryKey, utteranceData);
        }
    }
}
