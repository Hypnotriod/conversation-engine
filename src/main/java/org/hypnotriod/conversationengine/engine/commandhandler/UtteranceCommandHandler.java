package org.hypnotriod.conversationengine.engine.commandhandler;

import org.hypnotriod.conversationengine.engine.processor.UtteranceCommandProcessor;
import com.google.inject.internal.util.ImmutableList;
import javax.annotation.PostConstruct;
import org.hypnotriod.conversationengine.engine.ConversationEngine;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.hypnotriod.conversationengine.engine.annotation.CommandHandler;
import org.hypnotriod.conversationengine.engine.service.DialogReplyService;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResultComplete;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResultContinue;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResultDelegate;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResultReject;

/**
 *
 * @author Ilya Pikin
 */
public abstract class UtteranceCommandHandler {

    @Autowired
    private DialogReplyService dialogReplyService;

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

        if (commandName == null || commandName.commandName().isEmpty()) {
            throw new RuntimeException("Command " + this + " should have @CommandHandler annotation with proper name");
        }

        return commandName.commandName();
    }

    public String[] getContexts() {
        CommandHandler commandName = AnnotationUtils.findAnnotation(this.getClass(), CommandHandler.class);

        if (commandName == null || commandName.contextNames().length == 0) {
            throw new RuntimeException("Command " + this + " should have @CommandHandler annotation with one or more context");
        }

        return commandName.contextNames();
    }

    public UtteranceCommandHandlerResultComplete getResultComplete(String dialogReplyName) {
        return new UtteranceCommandHandlerResultComplete(
                dialogReplyService.findByName(dialogReplyName)
        );
    }

    public UtteranceCommandHandlerResultContinue getResultContinue(String dialogReplyName) {
        return new UtteranceCommandHandlerResultContinue(
                dialogReplyService.findByName(dialogReplyName)
        );
    }

    public UtteranceCommandHandlerResultDelegate getResultDelegate(String commandName, String contextName) {
        return new UtteranceCommandHandlerResultDelegate(commandName, contextName);
    }

    public UtteranceCommandHandlerResultReject getResultDelegate() {
        return new UtteranceCommandHandlerResultReject();
    }

    abstract public UtteranceCommandHandlerResult handle(
            final UtteranceRecognitionResult utteranceRecognitionResult,
            final ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory);
}
