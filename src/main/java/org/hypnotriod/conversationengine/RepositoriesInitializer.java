package org.hypnotriod.conversationengine;

import javax.annotation.PostConstruct;
import org.hypnotriod.conversationengine.engine.entity.SpokenQuery;
import org.hypnotriod.conversationengine.customrepository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.hypnotriod.conversationengine.engine.service.SpokenQueryService;
import org.hypnotriod.conversationengine.customentity.Destination;
import org.hypnotriod.conversationengine.customentity.Vehicle;
import org.hypnotriod.conversationengine.customrepository.VechicleRepository;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class RepositoriesInitializer {

    @Autowired
    private SpokenQueryService spokenQueryService;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private VechicleRepository vechicleRepository;

    @PostConstruct
    private void init() {
        spokenQueryService.add(new SpokenQuery("^go to [[:alpha:] ]+ by [[:alpha:] ]+$", "go to [destination.value] by [vehicle.value]", "en-US", "GO_TO_DESTINATION_BY_VECHICLE", "BASE"));
        spokenQueryService.add(new SpokenQuery("^go to [[:alpha:] ]+$", "go to [destination.value]", "en-US", "GO_TO_DESTINATION", "BASE"));
        spokenQueryService.add(new SpokenQuery("^go to [[:alpha:] ]+ right now", "go to [destination.value] right now", "en-US", "GO_TO_DESTINATION_NOW", "BASE"));

        destinationRepository.save(new Destination("home"));
        destinationRepository.save(new Destination("work"));

        vechicleRepository.save(new Vehicle("car"));
        vechicleRepository.save(new Vehicle("bus"));
    }
}
