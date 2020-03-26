package org.hypnotriod.conversationengine.engine.processor;

import com.google.inject.internal.util.ImmutableList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private final Map<String, Map<String, UtteranceCommandHandler>> utteranceCommandHandlers = new HashMap<>();
    private final List<UtteranceRecognitionResult> utteranceRecognitionResultsHistory = new LinkedList<>();

    public void registerUtteranceCommand(UtteranceCommandHandler command) {
        String commandName = command.getName();
        String[] commandContexts = command.getContexts();

        for (String commandContext : commandContexts) {
            if (!utteranceCommandHandlers.containsKey(commandContext)) {
                utteranceCommandHandlers.put(commandContext, new HashMap<>());
            }
            Map<String, UtteranceCommandHandler> utteranceCommandHandlersForContext = utteranceCommandHandlers.get(commandContext);
            if (utteranceCommandHandlersForContext.containsKey(commandName)) {
                throw new RuntimeException("Command with same name " + commandName + ", and context " + commandContext + " already exists!");
            }
            utteranceCommandHandlersForContext.put(commandName, command);
        }
    }

    public UtteranceCommandHandlerResult processUtteranceRecognitionResults(List<UtteranceRecognitionResult> utteranceRecognitionResults) {
        for (UtteranceRecognitionResult utteranceRecognitionResult : utteranceRecognitionResults) {
            Optional<UtteranceCommandHandler> command = Optional.ofNullable(
                    utteranceCommandHandlers.get(utteranceRecognitionResult.getContext()))
                    .map(values -> values.get(utteranceRecognitionResult.getCommand()));

            if (command.isPresent()) {
                UtteranceCommandHandlerResult commandResult
                        = command.get().handle(utteranceRecognitionResult, ImmutableList.copyOf(utteranceRecognitionResultsHistory));
                if (commandResult.getResult() == CommandHandlerResult.SUCCEED) {
                    storeUtteranceRecognitionResult(utteranceRecognitionResult);
                }
                return commandResult;
            }
        }
        return createDefaultCommandHandlerResult(utteranceRecognitionResults);
    }

    private void storeUtteranceRecognitionResult(UtteranceRecognitionResult utteranceRecognitionResult) {
        utteranceRecognitionResultsHistory.add(utteranceRecognitionResult);
        if (utteranceRecognitionResultsHistory.size() > RECOGNITION_RESULT_HISTORY_SIZE_MAX) {
            utteranceRecognitionResultsHistory.remove(0);
        }
    }

    private UtteranceCommandHandlerResult createDefaultCommandHandlerResult(List<UtteranceRecognitionResult> utteranceRecognitionResults) {
        return new UtteranceCommandHandlerResult(
                (utteranceRecognitionResults.size() > 0) ? CommandHandlerResult.UNHANDLED : CommandHandlerResult.REJECTED
        );
    }
}
