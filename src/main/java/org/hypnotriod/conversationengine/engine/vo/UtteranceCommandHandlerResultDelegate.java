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
    private final String context;

    public UtteranceCommandHandlerResultDelegate(String command, String context) {
        this.command = command;
        this.context = context;
    }
}
