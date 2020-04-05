package org.hypnotriod.conversationengine.engine.vo;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class UtteranceCommandHandlerResultContinue extends UtteranceCommandHandlerResult {

    private final CommandHandlerResult result = CommandHandlerResult.CONTINUE;
    private final String contextName;

    public UtteranceCommandHandlerResultContinue(String contextName) {
        this.contextName = contextName;
    }
}
