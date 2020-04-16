package org.hypnotriod.conversationengine.application.repository;

import org.hypnotriod.conversationengine.application.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author IPikin
 */
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

}
