package org.hypnotriod.conversationengine.engine.service;

import org.hypnotriod.conversationengine.engine.entity.DialogContext;
import org.hypnotriod.conversationengine.engine.repository.DialogContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ilya Pikin
 */
@Service
public class DialogContextService {

    @Autowired
    DialogContextRepository dialogContextRepository;

    public DialogContext findByName(String name) {
        return dialogContextRepository.findByName(name);
    }

    public void save(DialogContext dialogContext) {
        dialogContextRepository.save(dialogContext);
    }
}
