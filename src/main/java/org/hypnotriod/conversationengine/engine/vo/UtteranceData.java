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
    private final String value;

    public UtteranceData(String repositoryName, String repositoryKey, String value) {
        this.repositoryName = repositoryName;
        this.repositoryKey = repositoryKey;
        this.value = value;
    }

    public UtteranceData(String value) {
        this.repositoryName = null;
        this.repositoryKey = null;
        this.value = value;
    }
}
