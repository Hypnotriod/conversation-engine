package org.hypnotriod.conversationengine.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class ConversationEngine {

    @Autowired
    private DialogState dialogState;

    @Autowired
    private UtteranceProcessor utteranceProcessor;

    public void processUtterance(String utterance) {
        utteranceProcessor.process(utterance, dialogState.getContext());
    }

    public void changeContext(String context) {
        dialogState.setContext(context);
    }
}
