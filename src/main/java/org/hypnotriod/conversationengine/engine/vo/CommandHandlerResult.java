package org.hypnotriod.conversationengine.engine.vo;

/**
 *
 * @author Ilya Pikin
 */
public enum CommandHandlerResult {
    UNHANDLED("UNHANDLED"),
    REJECTED("REJECTED"),
    SUCCEED("SUCCEED");

    private final String value;

    CommandHandlerResult(String name) {
        this.value = name;
    }

    @Override
    public String toString() {
        return value;
    }
}
