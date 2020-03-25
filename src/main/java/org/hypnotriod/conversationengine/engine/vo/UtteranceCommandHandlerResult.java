package org.hypnotriod.conversationengine.engine.vo;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class UtteranceCommandHandlerResult {

    private final CommandHandlerResult result;

    public UtteranceCommandHandlerResult(CommandHandlerResult result) {
        this.result = result;
    }
}
