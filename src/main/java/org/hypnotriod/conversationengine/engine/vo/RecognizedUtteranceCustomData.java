package org.hypnotriod.conversationengine.engine.vo;

import java.util.List;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class RecognizedUtteranceCustomData extends RecognizedUtteranceData {

    private final String repositoryName;
    private final String repositoryKey;
    private final List<CustomDataMatch> customDataMatches;

    public RecognizedUtteranceCustomData(String key, String value, String repositoryName, String repositoryKey, List<CustomDataMatch> customDataMatches) {
        super(key, value);

        this.repositoryName = repositoryName;
        this.repositoryKey = repositoryKey;
        this.customDataMatches = customDataMatches;
    }
}
