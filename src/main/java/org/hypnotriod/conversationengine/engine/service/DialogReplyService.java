package org.hypnotriod.conversationengine.engine.service;

import org.hypnotriod.conversationengine.engine.entity.DialogReply;
import org.hypnotriod.conversationengine.engine.repository.DialogReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ilya Pikin
 */
@Service
public class DialogReplyService {

    @Autowired
    DialogReplyRepository dialogReplyRepository;

    public void save(DialogReply dialogReply) {
        dialogReplyRepository.save(dialogReply);
    }

    public DialogReply findByName(String name) {
        return dialogReplyRepository.findByName(name);
    }
}
