package org.hypnotriod.conversationengine;

import com.google.inject.internal.util.ImmutableList;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hypnotriod.conversationengine.CommandsInitializer.CMD_SEARCH;
import org.hypnotriod.conversationengine.engine.command.UtteranceCommand;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.hypnotriod.conversationengine.engine.annotation.Command;
import org.hypnotriod.conversationengine.engine.vo.ExecutionResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandResult;

/**
 *
 * @author Ilya Pikin
 */
@Command(CMD_SEARCH)
public class SearchCommand extends UtteranceCommand {

    @Override
    public UtteranceCommandResult execute(
            final UtteranceRecognitionResult utteranceRecognitionResult,
            final ImmutableList<UtteranceRecognitionResult> utteranceRecognitionResultsHistory) {

        String searchFor = utteranceRecognitionResult.getRecognizedDatas().get("request").getValue();

        System.out.println("Searching for " + searchFor);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("\"C:/Program Files (x86)/Google/Chrome/Application/chrome.exe\"", "https://www.google.com/search?q=" + searchFor.replaceAll(" ", "%20"));
            processBuilder.start();
        } catch (IOException ex) {
            Logger.getLogger(SearchCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

        return createResult(ExecutionResult.SUCCEED);
    }

}
