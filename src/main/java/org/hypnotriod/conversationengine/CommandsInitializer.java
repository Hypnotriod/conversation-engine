package org.hypnotriod.conversationengine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class CommandsInitializer {

    @Autowired
    SearchCommand searchCommand;
}
