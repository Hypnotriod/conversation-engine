package org.hypnotriod.conversationengine.engine.vo;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class UtteranceCommandResult {

    private final ExecutionResult result;

    public UtteranceCommandResult(ExecutionResult result) {
        this.result = result;
    }
}
