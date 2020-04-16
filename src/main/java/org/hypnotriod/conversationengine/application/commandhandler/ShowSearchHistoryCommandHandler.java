package org.hypnotriod.conversationengine.application.commandhandler;

import com.google.inject.internal.util.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;
import static org.hypnotriod.conversationengine.application.constants.Constants.CMD_SEARCH_FOR;
import static org.hypnotriod.conversationengine.application.constants.Constants.CONTEXT_NO;
import static org.hypnotriod.conversationengine.application.constants.Constants.CMD_SHOW_SEARCH_HISTORY;
import static org.hypnotriod.conversationengine.application.constants.Constants.REPLY_SHOWING_SEARCH_HISTORY;
import static org.hypnotriod.conversationengine.application.constants.Constants.REPLY_NO_SEARCH_HISTORY_FOUND;
import org.hypnotriod.conversationengine.engine.commandhandler.UtteranceCommandHandler;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
import org.hypnotriod.conversationengine.engine.annotation.CommandHandler;

/**
 *
 * @author Ilya Pikin
 */
@CommandHandler(commandName = CMD_SHOW_SEARCH_HISTORY, contextNames = {CONTEXT_NO})
public class ShowSearchHistoryCommandHandler extends UtteranceCommandHandler {

    @Override
    public UtteranceCommandHandlerResult handle(
            UtteranceRecognitionResult utteranceRecognitionResult,
            ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory) {

        List<String> searchHistory = utteranceRecognitionResultsHistory.stream()
                .filter(result -> result.getCommandName().equals(CMD_SEARCH_FOR))
                .map(result -> result.fetchRecognizedUtteranceData("request").getValue())
                .collect(Collectors.toList());

        if (searchHistory.size() > 0) {
            return getResultComplete(REPLY_SHOWING_SEARCH_HISTORY);
            //searchHistory.forEach(System.out::println);
        } else {
            return getResultComplete(REPLY_NO_SEARCH_HISTORY_FOUND);
            //System.out.println("No search history found");
        }
    }
}
