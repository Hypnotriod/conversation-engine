package org.hypnotriod.conversationengine.application.repository;

import org.hypnotriod.conversationengine.application.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ilya Pikin
 */
@Repository
public interface VechicleRepository extends JpaRepository<Vehicle, Long> {

}
