package org.hypnotriod.conversationengine.engine;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ilya Pikin
 */
@Component
@Getter
@Setter
@ToString
class DialogState {

    public static String BASE_CONTEXT = "BASE_CONTEXT";

    private String context = BASE_CONTEXT;
}
