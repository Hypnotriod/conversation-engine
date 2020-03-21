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

    @Getter
    @ToString
    public static final class IdValueMatch {

        private final Long id;
        private final String value;

        public IdValueMatch(Long id, String value) {
            this.id = id;
            this.value = value;
        }
    }

    private final String tableName;
    private final String tableKey;
    private final List<IdValueMatch> idValueMatches;

    public RecognizedUtteranceCustomData(String tableName, String tableKey, String value, List<IdValueMatch> idValueMatches) {
        super(value);

        this.tableName = tableName;
        this.tableKey = tableKey;
        this.idValueMatches = idValueMatches;
    }
}
