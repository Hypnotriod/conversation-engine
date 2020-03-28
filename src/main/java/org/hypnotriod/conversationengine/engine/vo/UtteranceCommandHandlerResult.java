package org.hypnotriod.conversationengine.engine.vo;

/**
 *
 * @author Ilya Pikin
 */
public abstract class UtteranceCommandHandlerResult {

    public abstract CommandHandlerResult getResult();
    
    public abstract String getContext();
}
