package org.hypnotriod.conversationengine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class CommandsInitializer {

    public static final String CMD_SEARCH = "SEARCH";
    public static final String CMD_SHOW_SEARCH_HISTORY = "SHOW_SEARCH_HISTORY";

    @Autowired
    SearchCommandHandler searchCommandHandler;

    @Autowired
    ShowSearchHistoryCommandHandler showSearchHistoryCommandHandler;
}
