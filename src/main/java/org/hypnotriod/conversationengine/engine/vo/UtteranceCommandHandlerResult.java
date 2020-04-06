package org.hypnotriod.conversationengine.engine.vo;

import com.google.inject.internal.util.ImmutableList;
import org.hypnotriod.conversationengine.engine.entity.DialogReply;

/**
 *
 * @author Ilya Pikin
 */
public abstract class UtteranceCommandHandlerResult {

    private UtteranceRecognitionResult utteranceRecognitionResult = null;
    private ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory = null;

    public abstract CommandHandlerResult getResult();

    public abstract String getContextName();

    public abstract DialogReply getDialogReply();

    public UtteranceRecognitionResult getUtteranceRecognitionResult() {
        return utteranceRecognitionResult;
    }

    public void setUtteranceRecognitionResult(UtteranceRecognitionResult utteranceRecognitionResult) {
        this.utteranceRecognitionResult = utteranceRecognitionResult;
    }

    public ImmutableList<UtteranceRecognitionResult> getUtteranceRecognitionResultsHistory() {
        return utteranceRecognitionResultsHistory;
    }

    public void setUtteranceRecognitionResultsHistory(ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory) {
        this.utteranceRecognitionResultsHistory = utteranceRecognitionResultsHistory;
    }
}
