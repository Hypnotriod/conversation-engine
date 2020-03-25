package org.hypnotriod.conversationengine.engine.vo;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Ilya Pikin
 */
@Getter
@ToString
public class CustomDataMatch {

    private final Long id;
    private final String value;
    private final boolean fullMatch;

    public CustomDataMatch(Long id, String value, boolean fullMatch) {
        this.id = id;
        this.value = value;
        this.fullMatch = fullMatch;
    }
}
