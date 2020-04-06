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
public class UtteranceCommandHandlerResultComplete extends UtteranceCommandHandlerResult {

    private final CommandHandlerResult result = CommandHandlerResult.COMPLETE;
    private final DialogReply dialogReply;

    public UtteranceCommandHandlerResultComplete(DialogReply dialogReply) {
        this.dialogReply = dialogReply;
    }

    @Override
    public String getContextName() {
        return Optional.ofNullable(dialogReply)
                .map(DialogReply::getContextName)
                .orElse(ConversationEngine.NO_CONTEXT);
    }
}
