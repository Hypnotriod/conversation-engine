package org.hypnotriod.conversationengine.customrepository;

import org.hypnotriod.conversationengine.customentity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ilya Pikin
 */
@Repository
public interface VechicleRepository extends JpaRepository<Vehicle, Long> {

}
