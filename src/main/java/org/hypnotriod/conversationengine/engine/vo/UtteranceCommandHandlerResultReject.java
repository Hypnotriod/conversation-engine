package org.hypnotriod.conversationengine.engine.vo;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class UtteranceCommandHandlerResultReject extends UtteranceCommandHandlerResult {

    private final CommandHandlerResult result = CommandHandlerResult.REJECT;
    private final String contextName = null;
}
