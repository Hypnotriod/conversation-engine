package org.hypnotriod.conversationengine.engine;

import javax.annotation.PostConstruct;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Ilya Pikin
 */
public abstract class UtteranceCommand {

    @Autowired
    private UtteranceProcessor utteranceProcessor;

    @PostConstruct
    private void register() {
        utteranceProcessor.registerUtteranceCommand(this);
    }

    abstract public boolean execute(UtteranceRecognitionResult utteranceRecognitionResult);
}
