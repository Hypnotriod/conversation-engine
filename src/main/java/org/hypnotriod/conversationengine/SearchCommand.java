package org.hypnotriod.conversationengine;

import org.hypnotriod.conversationengine.engine.UtteranceCommand;
import org.hypnotriod.conversationengine.engine.UtteranceCommandName;
import org.hypnotriod.conversationengine.engine.vo.UtteranceRecognitionResult;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ilya Pikin
 */
@Component
@UtteranceCommandName("SEARCH")
public class SearchCommand extends UtteranceCommand {

    @Override
    public boolean execute(UtteranceRecognitionResult utteranceRecognitionResult) {
        System.out.println("Searching for " + utteranceRecognitionResult.getRecognizedDatas().get(0).getValue());

        return true;
    }

}
