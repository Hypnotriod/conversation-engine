package org.hypnotriod.conversationengine.engine.vo;

import lombok.Getter;
import lombok.ToString;
import org.hypnotriod.conversationengine.engine.entity.DialogReply;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class UtteranceCommandHandlerResultDelegate extends UtteranceCommandHandlerResult {

    private final CommandHandlerResult result = CommandHandlerResult.DELEGATE;
    private final DialogReply dialogReply = null;
    private final String commandName;
    private final String contextName;

    public UtteranceCommandHandlerResultDelegate(String commandName, String contextName) {
        this.commandName = commandName;
        this.contextName = contextName;
    }
}
