package org.hypnotriod.conversationengine.engine.vo;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class RecognizedUtteranceData {

    private final String key;
    private final String value;

    public RecognizedUtteranceData(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
