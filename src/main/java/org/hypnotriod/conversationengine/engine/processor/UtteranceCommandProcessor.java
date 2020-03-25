package org.hypnotriod.conversationengine.engine.processor;

import com.google.inject.internal.util.ImmutableList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.hypnotriod.conversationengine.engine.commandhandler.UtteranceCommandHandler;
import org.hypnotriod.conversationengine.engine.vo.CommandHandlerResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class UtteranceCommandProcessor {

    private static final int RECOGNITION_RESULT_HISTORY_SIZE_MAX = 500;

    private final Map<String, UtteranceCommandHandler> utteranceCommandHandlers = new HashMap<>();
    private final List<UtteranceRecognitionResult> utteranceRecognitionResultsHistory = new LinkedList<>();

    public void registerUtteranceCommand(UtteranceCommandHandler command) {
        String commandName = command.getName();
        if (utteranceCommandHandlers.containsKey(commandName)) {
            throw new RuntimeException("Duplicated command " + command + " with name " + commandName);
        }
        utteranceCommandHandlers.put(commandName, command);
    }

    public UtteranceCommandHandlerResult processUtteranceRecognitionResults(List<UtteranceRecognitionResult> utteranceRecognitionResults) {
        for (UtteranceRecognitionResult utteranceRecognitionResult : utteranceRecognitionResults) {
            UtteranceCommandHandler command = utteranceCommandHandlers.get(utteranceRecognitionResult.getCommand());
            if (command != null) {
                UtteranceCommandHandlerResult commandResult
                        = command.handle(utteranceRecognitionResult, ImmutableList.copyOf(utteranceRecognitionResultsHistory));
                if (commandResult.getResult() == CommandHandlerResult.SUCCEED) {
                    storeSuccessedUtteranceRecognitionResult(utteranceRecognitionResult);
                }
                return commandResult;
            }
        }
        return createDefaultCommandResult(utteranceRecognitionResults);
    }

    private void storeSuccessedUtteranceRecognitionResult(UtteranceRecognitionResult utteranceRecognitionResult) {
        utteranceRecognitionResultsHistory.add(utteranceRecognitionResult);
        if (utteranceRecognitionResultsHistory.size() > RECOGNITION_RESULT_HISTORY_SIZE_MAX) {
            utteranceRecognitionResultsHistory.remove(0);
        }
    }

    private UtteranceCommandHandlerResult createDefaultCommandResult(List<UtteranceRecognitionResult> utteranceRecognitionResults) {
        return new UtteranceCommandHandlerResult(
                (utteranceRecognitionResults.size() > 0) ? CommandHandlerResult.UNHANDLED : CommandHandlerResult.REJECTED
        );
    }
}
