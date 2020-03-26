package org.hypnotriod.conversationengine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.hypnotriod.conversationengine.engine.entity.SpokenQuery;
import org.hypnotriod.conversationengine.customrepository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.hypnotriod.conversationengine.engine.service.SpokenQueryService;
import org.hypnotriod.conversationengine.customentity.Destination;
import org.hypnotriod.conversationengine.customentity.Vehicle;
import org.hypnotriod.conversationengine.customrepository.VechicleRepository;
import org.hypnotriod.conversationengine.engine.entity.SpokenContext;
import org.hypnotriod.conversationengine.engine.repository.SpokenContextRepository;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class RepositoriesInitializer {

    @Autowired
    private SpokenQueryService spokenQueryService;

    @Autowired
    SpokenContextRepository spokenContextRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private VechicleRepository vechicleRepository;

    @PostConstruct
    private void init() {
        spokenQueryService.save(new SpokenQuery("search for [request:?]", "en-US", "SEARCH", mapToSpokenContexts("BASE_CONTEXT")));
        spokenQueryService.save(new SpokenQuery("google for [request:?]", "en-US", "SEARCH", mapToSpokenContexts("BASE_CONTEXT")));
        spokenQueryService.save(new SpokenQuery("google [request:?]", "en-US", "SEARCH", mapToSpokenContexts("BASE_CONTEXT")));
        spokenQueryService.save(new SpokenQuery("show search history", "en-US", "SHOW_SEARCH_HISTORY", mapToSpokenContexts("BASE_CONTEXT")));
        spokenQueryService.save(new SpokenQuery("go to [key1:destination.value] by [key2:vehicle.value]", "en-US", "GO_TO_DESTINATION_BY_VECHICLE", mapToSpokenContexts("BASE_CONTEXT")));
        spokenQueryService.save(new SpokenQuery("go to [key1:destination.value]", "en-US", "GO_TO_DESTINATION", mapToSpokenContexts("BASE_CONTEXT")));
        spokenQueryService.save(new SpokenQuery("go to [key1:destination.value] right now", "en-US", "GO_TO_DESTINATION_NOW", mapToSpokenContexts("BASE_CONTEXT")));

        destinationRepository.save(new Destination("home"));
        destinationRepository.save(new Destination("work"));

        vechicleRepository.save(new Vehicle("car"));
        vechicleRepository.save(new Vehicle("bus"));
        vechicleRepository.save(new Vehicle("several words test"));
    }

    private List<SpokenContext> mapToSpokenContexts(String... contextNames) {
        return Arrays.asList(contextNames).stream()
                .map(contextName -> fetchSpokenContextByName(contextName))
                .collect(Collectors.toList());
    }

    private SpokenContext fetchSpokenContextByName(String contextName) {
        SpokenContext spokenContext = spokenContextRepository.findByName(contextName);
        if (spokenContext == null) {
            spokenContext = new SpokenContext(contextName);
            spokenContextRepository.save(spokenContext);
        }
        return spokenContext;
    }
}
