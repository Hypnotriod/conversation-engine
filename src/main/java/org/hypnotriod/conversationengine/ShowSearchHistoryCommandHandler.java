package org.hypnotriod.conversationengine;

import com.google.inject.internal.util.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;
import static org.hypnotriod.conversationengine.Constants.CMD_SHOW_SEARCH_HISTORY;
import org.hypnotriod.conversationengine.engine.commandhandler.UtteranceCommandHandler;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
import org.hypnotriod.conversationengine.engine.annotation.CommandHandler;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResultComplete;
import static org.hypnotriod.conversationengine.Constants.CMD_SEARCH_FOR;
import static org.hypnotriod.conversationengine.Constants.CONTEXT_NO;

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
            searchHistory.forEach(System.out::println);
        } else {
            System.out.println("No search history found");
        }

        return new UtteranceCommandHandlerResultComplete();
    }
}
