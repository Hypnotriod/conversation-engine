package org.hypnotriod.conversationengine;

import com.google.inject.internal.util.ImmutableList;
import static org.hypnotriod.conversationengine.Constants.CMD_SEARCH;
import static org.hypnotriod.conversationengine.Constants.CONTEXT_SEARCH_FOR;
import org.hypnotriod.conversationengine.engine.annotation.CommandHandler;
import org.hypnotriod.conversationengine.engine.commandhandler.UtteranceCommandHandler;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResultContinue;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import static org.hypnotriod.conversationengine.Constants.CONTEXT_NO;

/**
 *
 * @author Ilya Pikin
 */
@CommandHandler(command = CMD_SEARCH, contexts = {CONTEXT_NO})
public class SearchCommandHandler extends UtteranceCommandHandler {

    @Override
    public UtteranceCommandHandlerResult handle(
            UtteranceRecognitionResult utteranceRecognitionResult,
            ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory) {

        System.out.println("What do you want to search?");

        return new UtteranceCommandHandlerResultContinue(CONTEXT_SEARCH_FOR);
    }

}
