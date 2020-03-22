package org.hypnotriod.conversationengine;

import com.google.inject.internal.util.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;
import static org.hypnotriod.conversationengine.CommandsInitializer.CMD_SHOW_SEARCH_HISTORY;
import static org.hypnotriod.conversationengine.CommandsInitializer.CMD_SEARCH;
import org.hypnotriod.conversationengine.engine.UtteranceCommand;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.springframework.stereotype.Component;
import org.hypnotriod.conversationengine.engine.UtteranceCommandName;

/**
 *
 * @author Ilya Pikin
 */
@Component
@UtteranceCommandName(CMD_SHOW_SEARCH_HISTORY)
public class ShowSearchHistoryCommand extends UtteranceCommand {

    @Override
    public boolean execute(
            UtteranceRecognitionResult utteranceRecognitionResult,
            ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory) {

        List<String> searchHistory = utteranceRecognitionResultsHistory.stream()
                .filter(result -> result.getCommand().equals(CMD_SEARCH))
                .map(result -> result.getRecognizedDatas().get(0).getValue())
                .collect(Collectors.toList());

        if (searchHistory.size() > 0) {
            searchHistory.forEach(System.out::println);
        } else {
            System.out.println("No search history found");
        }

        return true;
    }
}
