package org.hypnotriod.conversationengine;

import com.google.inject.internal.util.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;
import static org.hypnotriod.conversationengine.CommandsInitializer.CMD_SHOW_SEARCH_HISTORY;
import static org.hypnotriod.conversationengine.CommandsInitializer.CMD_SEARCH;
import static org.hypnotriod.conversationengine.CommandsInitializer.CONTEXT_BASE;
import org.hypnotriod.conversationengine.engine.commandhandler.UtteranceCommandHandler;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.hypnotriod.conversationengine.engine.vo.CommandHandlerResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
import org.hypnotriod.conversationengine.engine.annotation.CommandHandler;

/**
 *
 * @author Ilya Pikin
 */
@CommandHandler(value = CMD_SHOW_SEARCH_HISTORY, contexts = {CONTEXT_BASE})
public class ShowSearchHistoryCommandHandler extends UtteranceCommandHandler {

    @Override
    public UtteranceCommandHandlerResult handle(
            UtteranceRecognitionResult utteranceRecognitionResult,
            ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory) {

        List<String> searchHistory = utteranceRecognitionResultsHistory.stream()
                .filter(result -> result.getCommand().equals(CMD_SEARCH))
                .map(result -> result.fetchRecognizedUtteranceData("request").getValue())
                .collect(Collectors.toList());

        if (searchHistory.size() > 0) {
            searchHistory.forEach(System.out::println);
        } else {
            System.out.println("No search history found");
        }

        return createResult(CommandHandlerResult.SUCCEED);
    }
}
