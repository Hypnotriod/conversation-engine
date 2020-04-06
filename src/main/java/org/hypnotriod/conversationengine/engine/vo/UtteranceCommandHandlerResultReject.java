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
public class UtteranceCommandHandlerResultReject extends UtteranceCommandHandlerResult {

    private final CommandHandlerResult result = CommandHandlerResult.REJECT;
    private final DialogReply dialogReply = null;
    private final String contextName = null;
}
