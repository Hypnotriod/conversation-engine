package org.hypnotriod.conversationengine.application.commandhandler;

import com.google.inject.internal.util.ImmutableList;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hypnotriod.conversationengine.engine.commandhandler.UtteranceCommandHandler;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
import org.hypnotriod.conversationengine.engine.annotation.CommandHandler;
import static org.hypnotriod.conversationengine.application.constants.Constants.CMD_SEARCH_FOR;
import static org.hypnotriod.conversationengine.application.constants.Constants.CONTEXT_NO;
import static org.hypnotriod.conversationengine.application.constants.Constants.CONTEXT_SEARCH_FOR;
import static org.hypnotriod.conversationengine.application.constants.Constants.REPLY_SEARCHING;

/**
 *
 * @author Ilya Pikin
 */
@CommandHandler(commandName = CMD_SEARCH_FOR, contextNames = {CONTEXT_NO, CONTEXT_SEARCH_FOR})
public class SearchForCommandHandler extends UtteranceCommandHandler {

    @Override
    public UtteranceCommandHandlerResult handle(
            final UtteranceRecognitionResult utteranceRecognitionResult,
            final ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory) {

        String searchFor = utteranceRecognitionResult.fetchRecognizedUtteranceData("request").getValue();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("\"C:/Program Files (x86)/Google/Chrome/Application/chrome.exe\"", "https://www.google.com/search?q=" + searchFor.replaceAll(" ", "%20"));
            processBuilder.start();
        } catch (IOException ex) {
            Logger.getLogger(SearchForCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return getResultComplete(REPLY_SEARCHING);
    }
}
