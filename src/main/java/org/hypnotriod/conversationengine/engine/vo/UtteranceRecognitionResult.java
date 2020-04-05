package org.hypnotriod.conversationengine.engine.vo;

import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class UtteranceRecognitionResult {

    private final String query;
    private final String languageCode;
    private final String commandName;
    private final String contextName;
    private final Map<String, RecognizedUtteranceData> recognizedDatas;

    public UtteranceRecognitionResult(String query, String languageCode, String commandName, String contextName, Map<String, RecognizedUtteranceData> recognizedDatas) {
        this.query = query;
        this.languageCode = languageCode;
        this.commandName = commandName;
        this.contextName = contextName;
        this.recognizedDatas = recognizedDatas;
    }

    public RecognizedUtteranceData fetchRecognizedUtteranceData(String key) {
        return recognizedDatas.get(key);
    }

    public RecognizedUtteranceCustomData fetchRecognizedUtteranceCustomData(String key) {
        return (RecognizedUtteranceCustomData) Optional.ofNullable(recognizedDatas.get(key))
                .filter(value -> value instanceof RecognizedUtteranceCustomData).orElse(null);
    }
}
