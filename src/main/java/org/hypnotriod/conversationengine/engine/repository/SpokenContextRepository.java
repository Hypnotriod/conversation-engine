package org.hypnotriod.conversationengine.engine.repository;

import org.hypnotriod.conversationengine.engine.entity.SpokenContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ilya Pikin
 */
@Repository
public interface SpokenContextRepository extends JpaRepository<SpokenContext, Long> {

    SpokenContext findByName(String name);
}
