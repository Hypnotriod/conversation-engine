package org.hypnotriod.conversationengine;

import com.google.inject.internal.util.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;
import static org.hypnotriod.conversationengine.CommandsInitializer.CMD_SHOW_SEARCH_HISTORY;
import static org.hypnotriod.conversationengine.CommandsInitializer.CMD_SEARCH;
import org.hypnotriod.conversationengine.engine.command.UtteranceCommand;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.hypnotriod.conversationengine.engine.annotation.Command;
import org.hypnotriod.conversationengine.engine.vo.ExecutionResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandResult;

/**
 *
 * @author Ilya Pikin
 */
@Command(CMD_SHOW_SEARCH_HISTORY)
public class ShowSearchHistoryCommand extends UtteranceCommand {

    @Override
    public UtteranceCommandResult execute(
            UtteranceRecognitionResult utteranceRecognitionResult,
            ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory) {

        List<String> searchHistory = utteranceRecognitionResultsHistory.stream()
                .filter(result -> result.getCommand().equals(CMD_SEARCH))
                .map(result -> result.getRecognizedDatas().get("request").getValue())
                .collect(Collectors.toList());

        if (searchHistory.size() > 0) {
            searchHistory.forEach(System.out::println);
        } else {
            System.out.println("No search history found");
        }

        return createResult(ExecutionResult.SUCCEED);
    }
}
