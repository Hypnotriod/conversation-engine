package org.hypnotriod.conversationengine.engine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hypnotriod.conversationengine.engine.entity.SpokenQuery;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCustomData;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class UtteranceCustomDataParserService {

    public static final String DATA_PATTERN_REGEX = "\\[[a-z\\.]+\\]";
    public static final Pattern DATA_PATTERN = Pattern.compile(DATA_PATTERN_REGEX);

    public List<UtteranceCustomData> parse(String utterance, SpokenQuery spokenQuery) {
        List<String> utteranceDatas = extractDatasFromUserUtterance(utterance, spokenQuery.getQuery());
        return parseSpokenQueryDatas(utteranceDatas, spokenQuery);
    }

    private List<String> extractDatasFromUserUtterance(String utterance, String query) {
        List<String> result = new ArrayList<>();
        String[] dummyLoads = query.split(DATA_PATTERN_REGEX);

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

    private List<UtteranceCustomData> parseSpokenQueryDatas(List<String> utteranceDatas, SpokenQuery spokenQuery) {
        List<UtteranceCustomData> result = new ArrayList<>();
        Matcher matcher = DATA_PATTERN.matcher(spokenQuery.getQuery());

        while (matcher.find()) {
            String dataPattern = matcher.group();
            String data = utteranceDatas.remove(0);

            result.add(parseSpokenQueryData(dataPattern, data));
        }

        return result;
    }

    private UtteranceCustomData parseSpokenQueryData(String dataPattern, String data) {
        String trimmedPattern = dataPattern.substring(1, dataPattern.length() - 1);
        String[] repositoryNameKey = trimmedPattern.split("\\.");
        String repositoryName = repositoryNameKey[0];
        String repositoryKey = repositoryNameKey[1];

        return new UtteranceCustomData(repositoryName, repositoryKey, data);
    }
}
