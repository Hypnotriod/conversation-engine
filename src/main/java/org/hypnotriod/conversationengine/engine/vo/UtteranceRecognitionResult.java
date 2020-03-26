package org.hypnotriod.conversationengine.engine.vo;

import java.util.Map;
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
    private final String language;
    private final String command;
    private final String context;
    private final Map<String, RecognizedUtteranceData> recognizedDatas;

    public UtteranceRecognitionResult(String query, String language, String command, String context, Map<String, RecognizedUtteranceData> recognizedDatas) {
        this.query = query;
        this.language = language;
        this.command = command;
        this.context = context;
        this.recognizedDatas = recognizedDatas;
    }

    public RecognizedUtteranceData fetchRecognizedUtteranceData(String key) {
        return recognizedDatas.get(key);
    }

    public RecognizedUtteranceCustomData fetchRecognizedUtteranceCustomData(String key) {
        RecognizedUtteranceCustomData result = null;
        RecognizedUtteranceData recognizedUtteranceData = recognizedDatas.get(key);
        if (recognizedUtteranceData.getClass() == RecognizedUtteranceCustomData.class) {
            result = (RecognizedUtteranceCustomData) recognizedUtteranceData;
        }
        return result;
    }
}
