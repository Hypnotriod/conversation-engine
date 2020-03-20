package org.hypnotriod.conversationengine.engine.processor;

import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ilya Pikin
 */
@Component
@Getter
@ToString
public class DialogState {

    public static String BASE_CONTEXT = "BASE_CONTEXT";

    private String context = BASE_CONTEXT;

    public void setContext(String context) {
        this.context = context;
    }
}
