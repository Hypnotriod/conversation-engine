package org.hypnotriod.conversationengine.engine.vo;

import lombok.Getter;
import lombok.ToString;
import org.hypnotriod.conversationengine.engine.ConversationEngine;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class UtteranceCommandHandlerResultComplete extends UtteranceCommandHandlerResult {

    private final CommandHandlerResult result = CommandHandlerResult.COMPLETE;
    private final String contextName = ConversationEngine.NO_CONTEXT;
}
