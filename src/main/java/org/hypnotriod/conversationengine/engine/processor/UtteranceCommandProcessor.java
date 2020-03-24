package org.hypnotriod.conversationengine.engine.processor;

import com.google.inject.internal.util.ImmutableList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.hypnotriod.conversationengine.engine.command.UtteranceCommand;
import org.hypnotriod.conversationengine.engine.vo.ExecutionResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class UtteranceCommandProcessor {

    private static final int RECOGNITION_RESULT_HISTORY_SIZE_MAX = 500;

    private final Map<String, UtteranceCommand> utteranceCommands = new HashMap<>();
    private final List<UtteranceRecognitionResult> utteranceRecognitionResultsHistory = new LinkedList<>();

    public void registerUtteranceCommand(UtteranceCommand command) {
        String commandName = command.getName();
        if (utteranceCommands.containsKey(commandName)) {
            throw new RuntimeException("Duplicated command " + command + " with name " + commandName);
        }
        utteranceCommands.put(commandName, command);
    }

    public UtteranceCommandResult processUtteranceRecognitionResults(List<UtteranceRecognitionResult> utteranceRecognitionResults) {
        for (UtteranceRecognitionResult utteranceRecognitionResult : utteranceRecognitionResults) {
            UtteranceCommand command = utteranceCommands.get(utteranceRecognitionResult.getCommand());
            if (command != null) {
                UtteranceCommandResult commandResult
                        = command.execute(utteranceRecognitionResult, ImmutableList.copyOf(utteranceRecognitionResultsHistory));
                if (commandResult.getResult() == ExecutionResult.SUCCEED) {
                    storeSuccessedUtteranceRecognitionResult(utteranceRecognitionResult);
                }
                return commandResult;
            }
        }
        return new UtteranceCommandResult(ExecutionResult.REJECTED);
    }

    private void storeSuccessedUtteranceRecognitionResult(UtteranceRecognitionResult utteranceRecognitionResult) {
        utteranceRecognitionResultsHistory.add(utteranceRecognitionResult);
        if (utteranceRecognitionResultsHistory.size() > RECOGNITION_RESULT_HISTORY_SIZE_MAX) {
            utteranceRecognitionResultsHistory.remove(0);
        }
    }
}
