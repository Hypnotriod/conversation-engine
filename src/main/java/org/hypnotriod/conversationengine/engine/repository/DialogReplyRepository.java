package org.hypnotriod.conversationengine.engine.repository;

import javax.transaction.Transactional;
import org.hypnotriod.conversationengine.engine.entity.DialogReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ilya Pikin
 */
@Repository
@Transactional
public interface DialogReplyRepository extends JpaRepository<DialogReply, Long> {

}
