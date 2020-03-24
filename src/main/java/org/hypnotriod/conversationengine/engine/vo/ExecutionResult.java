package org.hypnotriod.conversationengine.engine.vo;

/**
 *
 * @author Ilya Pikin
 */
public enum ExecutionResult {
    REJECTED("REJECTED"),
    SUCCEED("SUCCEED");

    private final String value;

    ExecutionResult(String name) {
        this.value = name;
    }

    @Override
    public String toString() {
        return value;
    }
}
