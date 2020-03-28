package org.hypnotriod.conversationengine.engine.vo;

/**
 *
 * @author Ilya Pikin
 */
public enum CommandHandlerResult {
    REJECT("REJECT"),
    DELEGATE("DELEGATE"),
    CONTINUE("CONTINUE"),
    COMPLETE("COMPLETE");

    private final String value;

    CommandHandlerResult(String name) {
        this.value = name;
    }

    @Override
    public String toString() {
        return value;
    }
}
