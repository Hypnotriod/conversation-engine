package org.hypnotriod.conversationengine.engine.vo;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class UtteranceData {

    private final String repositoryName;
    private final String repositoryKey;
    private final String key;
    private final String value;

    public UtteranceData(String key, String value, String repositoryName, String repositoryKey) {
        this.key = key;
        this.value = value;
        this.repositoryName = repositoryName;
        this.repositoryKey = repositoryKey;
    }

    public UtteranceData(String key, String value) {
        this.key = key;
        this.value = value;
        this.repositoryName = null;
        this.repositoryKey = null;
    }
}
