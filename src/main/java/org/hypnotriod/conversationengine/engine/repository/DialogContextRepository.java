package org.hypnotriod.conversationengine.engine.repository;

import org.hypnotriod.conversationengine.engine.entity.DialogContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ilya Pikin
 */
@Repository
public interface DialogContextRepository extends JpaRepository<DialogContext, Long> {

    DialogContext findByName(String name);
}
