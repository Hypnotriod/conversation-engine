package org.hypnotriod.conversationengine.engine.vo;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class UtteranceCommandHandlerResultDelegate extends UtteranceCommandHandlerResult {

    private final CommandHandlerResult result = CommandHandlerResult.DELEGATE;
    private final String command;
    private final String contextName;

    public UtteranceCommandHandlerResultDelegate(String command, String contextName) {
        this.command = command;
        this.contextName = contextName;
    }
}
