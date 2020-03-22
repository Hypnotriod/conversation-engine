package org.hypnotriod.conversationengine.engine;

import org.hypnotriod.conversationengine.engine.processor.UtteranceCommandProcessor;
import com.google.inject.internal.util.ImmutableList;
import javax.annotation.PostConstruct;
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
        UtteranceCommandName commandName = AnnotationUtils.findAnnotation(this.getClass(), UtteranceCommandName.class);

        if (commandName == null || commandName.value().isEmpty()) {
            throw new RuntimeException("Command " + this + " should have @UtteranceCommandName annotation with proper name");
        }

        return commandName.value();
    }

    abstract public boolean execute(
            final UtteranceRecognitionResult utteranceRecognitionResult,
            final ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory);
}
