package org.hypnotriod.conversationengine.engine;

import lombok.Getter;
import lombok.Setter;
import org.hypnotriod.conversationengine.engine.processor.UtteranceProcessor;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
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

    @Getter
    @Setter
    private String context = BASE_CONTEXT;

    public UtteranceCommandHandlerResult processUtterance(String utterance) {
        return utteranceProcessor.process(utterance, context);
    }
}
