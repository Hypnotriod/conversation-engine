package org.hypnotriod.conversationengine.engine.vo;

import java.util.Optional;
import lombok.Getter;
import lombok.ToString;
import org.hypnotriod.conversationengine.engine.ConversationEngine;
import org.hypnotriod.conversationengine.engine.entity.DialogReply;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class UtteranceCommandHandlerResultContinue extends UtteranceCommandHandlerResult {

    private final CommandHandlerResult result = CommandHandlerResult.CONTINUE;
    private final DialogReply dialogReply;

    public UtteranceCommandHandlerResultContinue(DialogReply dialogReply) {
        this.dialogReply = dialogReply;
    }

    @Override
    public String getContextName() {
        return Optional.ofNullable(dialogReply)
                .map(DialogReply::getContextName)
                .orElse(ConversationEngine.NO_CONTEXT);
    }
}
