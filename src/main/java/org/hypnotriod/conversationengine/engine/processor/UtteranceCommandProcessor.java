package org.hypnotriod.conversationengine.engine.processor;

import com.google.inject.internal.util.ImmutableList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.hypnotriod.conversationengine.engine.ConversationEngine;
import org.hypnotriod.conversationengine.engine.commandhandler.UtteranceCommandHandler;
import org.hypnotriod.conversationengine.engine.vo.CommandHandlerResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResultDelegate;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResultReject;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class UtteranceCommandProcessor {

    public static final int RECOGNITION_RESULT_HISTORY_SIZE_MAX = 500;

    @Autowired
    private ConversationEngine conversationEngine;

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
            UtteranceCommandHandler commandHandler = fetchUtteranceCommandHandler(utteranceRecognitionResult.getContextName(), utteranceRecognitionResult.getCommandName());
            if (commandHandler != null) {
                UtteranceCommandHandlerResult commandResult = processUtteranceCommandHandler(commandHandler, utteranceRecognitionResult);

                if (commandResult.getResult() != CommandHandlerResult.REJECT) {
                    return commandResult;
                }
            }
        }
        return new UtteranceCommandHandlerResultReject();
    }

    private UtteranceCommandHandlerResult processUtteranceCommandHandler(UtteranceCommandHandler commandHandler, UtteranceRecognitionResult utteranceRecognitionResult) {
        ImmutableList<UtteranceRecognitionResult> recognitionResultsHistory = ImmutableList.copyOf(utteranceRecognitionResultsHistory);
        UtteranceCommandHandlerResult commandResult
                = commandHandler.handle(utteranceRecognitionResult, recognitionResultsHistory);

        if (commandResult.getResult() == CommandHandlerResult.DELEGATE) {
            UtteranceCommandHandler followCommandHandler = fetchUtteranceCommandHandler(
                    commandResult.getContextName(),
                    ((UtteranceCommandHandlerResultDelegate) commandResult).getCommandName());

            return followCommandHandler != null
                    ? processUtteranceCommandHandler(followCommandHandler, utteranceRecognitionResult)
                    : new UtteranceCommandHandlerResultReject();
        }

        if (commandResult.getResult() == CommandHandlerResult.COMPLETE
                || commandResult.getResult() == CommandHandlerResult.CONTINUE) {
            storeUtteranceRecognitionResult(utteranceRecognitionResult);
            commandResult.setUtteranceRecognitionResult(utteranceRecognitionResult);
            commandResult.setUtteranceRecognitionResultsHistory(recognitionResultsHistory);
            conversationEngine.setContextName(commandResult.getContextName());
        }

        return commandResult;
    }

    private UtteranceCommandHandler fetchUtteranceCommandHandler(String context, String commandName) {
        return Optional.ofNullable(
                utteranceCommandHandlers.get(context))
                .map(values -> values.get(commandName)).orElse(null);
    }

    private void storeUtteranceRecognitionResult(UtteranceRecognitionResult utteranceRecognitionResult) {
        utteranceRecognitionResultsHistory.add(utteranceRecognitionResult);
        if (utteranceRecognitionResultsHistory.size() > RECOGNITION_RESULT_HISTORY_SIZE_MAX) {
            utteranceRecognitionResultsHistory.remove(0);
        }
    }
}
