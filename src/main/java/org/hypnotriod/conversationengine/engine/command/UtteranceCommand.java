package org.hypnotriod.conversationengine.engine.command;

import org.hypnotriod.conversationengine.engine.processor.UtteranceCommandProcessor;
import com.google.inject.internal.util.ImmutableList;
import javax.annotation.PostConstruct;
import org.hypnotriod.conversationengine.engine.annotation.Command;
import org.hypnotriod.conversationengine.engine.vo.ExecutionResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

/**
 *
 * @author Ilya Pikin
 */
public abstract class UtteranceCommand {

    @Autowired
    private UtteranceCommandProcessor utteranceCommandProcessor;

    @PostConstruct
    private void register() {
        utteranceCommandProcessor.registerUtteranceCommand(this);
    }

    public String getName() {
        Command commandName = AnnotationUtils.findAnnotation(this.getClass(), Command.class);

        if (commandName == null || commandName.value().isEmpty()) {
            throw new RuntimeException("Command " + this + " should have @UtteranceCommandName annotation with proper name");
        }

        return commandName.value();
    }

    protected UtteranceCommandResult createResult(ExecutionResult executionResult) {
        return new UtteranceCommandResult(executionResult);
    }

    abstract public UtteranceCommandResult execute(
            final UtteranceRecognitionResult utteranceRecognitionResult,
            final ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory);
}
