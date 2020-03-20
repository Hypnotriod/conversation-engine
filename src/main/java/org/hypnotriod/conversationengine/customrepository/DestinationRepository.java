package org.hypnotriod.conversationengine.customrepository;

import org.hypnotriod.conversationengine.customentity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author IPikin
 */
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

}
