package org.hypnotriod.conversationengine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private final Map<String, SpokenContext> spokenContexts = new HashMap<>();

    @PostConstruct
    private void init() {
        spokenQueryService.save(new SpokenQuery("^go to [[:alpha:] ]+ by [[:alpha:] ]+$", "go to [destination.value] by [vehicle.value]", "en-US", "GO_TO_DESTINATION_BY_VECHICLE", mapToSpokenContexts("BASE_CONTEXT")));
        spokenQueryService.save(new SpokenQuery("^go to [[:alpha:] ]+$", "go to [destination.value]", "en-US", "GO_TO_DESTINATION", mapToSpokenContexts("BASE_CONTEXT")));
        spokenQueryService.save(new SpokenQuery("^go to [[:alpha:] ]+ right now", "go to [destination.value] right now", "en-US", "GO_TO_DESTINATION_NOW", mapToSpokenContexts("BASE_CONTEXT", "TEST_CONTEXT")));

        destinationRepository.save(new Destination("home"));
        destinationRepository.save(new Destination("work"));

        vechicleRepository.save(new Vehicle("car"));
        vechicleRepository.save(new Vehicle("bus"));
    }

    private List<SpokenContext> mapToSpokenContexts(String... contextNames) {
        return Arrays.asList(contextNames).stream()
                .map(contextName -> fetchSpokenContextByName(contextName))
                .collect(Collectors.toList());
    }

    private SpokenContext fetchSpokenContextByName(String contextName) {
        if (!spokenContexts.containsKey(contextName)) {
            SpokenContext spokenContext = new SpokenContext(contextName);
            spokenContextRepository.save(spokenContext);
            spokenContexts.put(contextName, spokenContext);
        }
        return spokenContexts.get(contextName);
    }
}
