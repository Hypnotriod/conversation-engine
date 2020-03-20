package org.hypnotriod.conversationengine.engine.vo;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class UtteranceCustomData {

    private final String tableName;
    private final String tableKey;
    private final String value;

    public UtteranceCustomData(String tableName, String tableKey, String value) {
        this.tableName = tableName;
        this.tableKey = tableKey;
        this.value = value;
    }
}
