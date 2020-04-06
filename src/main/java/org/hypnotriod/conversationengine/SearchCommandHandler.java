package org.hypnotriod.conversationengine;

import com.google.inject.internal.util.ImmutableList;
import static org.hypnotriod.conversationengine.Constants.CMD_SEARCH;
import org.hypnotriod.conversationengine.engine.annotation.CommandHandler;
import org.hypnotriod.conversationengine.engine.commandhandler.UtteranceCommandHandler;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import static org.hypnotriod.conversationengine.Constants.CONTEXT_NO;
import static org.hypnotriod.conversationengine.Constants.REPLY_WHAT_DO_YOU_WANT_TO_SEARCH;

/**
 *
 * @author Ilya Pikin
 */
@CommandHandler(commandName = CMD_SEARCH, contextNames = {CONTEXT_NO})
public class SearchCommandHandler extends UtteranceCommandHandler {

    @Override
    public UtteranceCommandHandlerResult handle(
            UtteranceRecognitionResult utteranceRecognitionResult,
            ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory) {

        return getResultComplete(REPLY_WHAT_DO_YOU_WANT_TO_SEARCH);
    }

}
