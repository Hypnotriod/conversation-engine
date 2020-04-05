package org.hypnotriod.conversationengine.engine.service;

import org.hypnotriod.conversationengine.engine.entity.DialogQuery;
import org.hypnotriod.conversationengine.engine.vo.UtteranceData;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import static org.hypnotriod.conversationengine.engine.contants.TextManipulationConstants.REGEX_UTTERANCE_DATA;
import static org.hypnotriod.conversationengine.engine.contants.TextManipulationConstants.PATTERN_UTTERANCE_DATA;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class UtteranceDataParserService {

    public List<UtteranceData> parse(String utterance, DialogQuery dialogQuery) {
        List<String> utteranceDatas = extractDatasFromUserUtterance(utterance, dialogQuery.getQuery());
        return parseSpokenQueryDatas(utteranceDatas, dialogQuery);
    }

    private List<String> extractDatasFromUserUtterance(String utterance, String query) {
        List<String> result = new ArrayList<>();
        String[] dummyLoads = query.split(REGEX_UTTERANCE_DATA);

        if (dummyLoads.length == 0) {
            dummyLoads = new String[]{""};
        }

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

    private List<UtteranceData> parseSpokenQueryDatas(List<String> utteranceDatas, DialogQuery dialogQuery) {
        List<UtteranceData> result = new ArrayList<>();
        Matcher matcher = PATTERN_UTTERANCE_DATA.matcher(dialogQuery.getQuery());

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
            String[] patternParams = trimmedPattern.split(":");
            String utteranceDataKey = patternParams[0];

            return new UtteranceData(utteranceDataKey, utteranceData);
        } else {
            String[] patternParams = trimmedPattern.split(":|\\.");
            String utteranceDataKey = patternParams[0];
            String repositoryName = patternParams[1];
            String repositoryKey = patternParams[2];

            return new UtteranceData(utteranceDataKey, utteranceData, repositoryName, repositoryKey);
        }
    }
}
