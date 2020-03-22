package org.hypnotriod.conversationengine.engine;

import org.hypnotriod.conversationengine.engine.processor.UtteranceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class ConversationEngine {

    public static String BASE_CONTEXT = "BASE_CONTEXT";

    @Autowired
    private UtteranceProcessor utteranceProcessor;

    private String context = BASE_CONTEXT;

    public boolean processUtterance(String utterance) {
        return utteranceProcessor.process(utterance, context);
    }

    public void changeContext(String context) {
        this.context = context;
    }
}
