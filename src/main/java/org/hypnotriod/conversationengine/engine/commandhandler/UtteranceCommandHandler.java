package org.hypnotriod.conversationengine.engine.commandhandler;

import org.hypnotriod.conversationengine.engine.processor.UtteranceCommandProcessor;
import com.google.inject.internal.util.ImmutableList;
import javax.annotation.PostConstruct;
import org.hypnotriod.conversationengine.engine.ConversationEngine;
import org.hypnotriod.conversationengine.engine.vo.CommandHandlerResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.hypnotriod.conversationengine.engine.annotation.CommandHandler;

/**
 *
 * @author Ilya Pikin
 */
public abstract class UtteranceCommandHandler {

    @Autowired
    private UtteranceCommandProcessor utteranceCommandProcessor;

    @Autowired
    private ConversationEngine conversationEngine;

    @PostConstruct
    private void register() {
        utteranceCommandProcessor.registerUtteranceCommand(this);
    }

    public String getName() {
        CommandHandler commandName = AnnotationUtils.findAnnotation(this.getClass(), CommandHandler.class);

        if (commandName == null || commandName.value().isEmpty()) {
            throw new RuntimeException("Command " + this + " should have @CommandHandler annotation with proper name");
        }

        return commandName.value();
    }

    public String[] getContexts() {
        CommandHandler commandName = AnnotationUtils.findAnnotation(this.getClass(), CommandHandler.class);

        if (commandName == null || commandName.contexts().length == 0) {
            throw new RuntimeException("Command " + this + " should have @CommandHandler annotation with one or more context");
        }

        return commandName.contexts();
    }

    protected void changeContext(String context) {
        conversationEngine.setContext(context);
    }

    protected UtteranceCommandHandlerResult createResult(CommandHandlerResult executionResult) {
        return new UtteranceCommandHandlerResult(executionResult);
    }

    protected UtteranceCommandHandlerResult createSuccedResult() {
        return new UtteranceCommandHandlerResult(CommandHandlerResult.SUCCEED);
    }

    protected UtteranceCommandHandlerResult createRejectedResult() {
        return new UtteranceCommandHandlerResult(CommandHandlerResult.REJECTED);
    }

    abstract public UtteranceCommandHandlerResult handle(
            final UtteranceRecognitionResult utteranceRecognitionResult,
            final ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory);
}
