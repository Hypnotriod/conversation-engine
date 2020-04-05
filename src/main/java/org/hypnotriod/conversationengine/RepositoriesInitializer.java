package org.hypnotriod.conversationengine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.hypnotriod.conversationengine.engine.entity.DialogQuery;
import org.hypnotriod.conversationengine.customrepository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.hypnotriod.conversationengine.engine.service.DialogQueryService;
import org.hypnotriod.conversationengine.customentity.Destination;
import org.hypnotriod.conversationengine.customentity.Vehicle;
import org.hypnotriod.conversationengine.customrepository.VechicleRepository;
import org.hypnotriod.conversationengine.engine.entity.DialogContext;
import static org.hypnotriod.conversationengine.Constants.CMD_SEARCH;
import static org.hypnotriod.conversationengine.Constants.CMD_SEARCH_FOR;
import static org.hypnotriod.conversationengine.Constants.CMD_SHOW_SEARCH_HISTORY;
import static org.hypnotriod.conversationengine.Constants.CONTEXT_SEARCH_FOR;
import static org.hypnotriod.conversationengine.Constants.CONTEXT_NO;
import static org.hypnotriod.conversationengine.Constants.REPLY_WHAT_DO_YOU_WANT_TO_SEARCH;
import org.hypnotriod.conversationengine.engine.entity.DialogReply;
import org.hypnotriod.conversationengine.engine.entity.ReplyVariant;
import org.hypnotriod.conversationengine.engine.service.DialogContextService;
import org.hypnotriod.conversationengine.engine.service.DialogReplyService;

/**
 *
 * @author Ilya Pikin
 */
@Component
public class RepositoriesInitializer {

    @Autowired
    private DialogQueryService dialogQueryService;

    @Autowired
    DialogContextService dialogContextService;

    @Autowired
    DialogReplyService dialogReplyService;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private VechicleRepository vechicleRepository;

    @PostConstruct
    private void init() {
        dialogQueryService.save(new DialogQuery("search for [request:?]", "en-US", CMD_SEARCH_FOR, mapToDialogContexts(CONTEXT_NO)));
        dialogQueryService.save(new DialogQuery("google for [request:?]", "en-US", CMD_SEARCH_FOR, mapToDialogContexts(CONTEXT_NO)));
        dialogQueryService.save(new DialogQuery("search for", "en-US", CMD_SEARCH, mapToDialogContexts(CONTEXT_NO)));
        dialogQueryService.save(new DialogQuery("google for", "en-US", CMD_SEARCH, mapToDialogContexts(CONTEXT_NO)));
        dialogQueryService.save(new DialogQuery("search", "en-US", CMD_SEARCH, mapToDialogContexts(CONTEXT_NO)));
        dialogQueryService.save(new DialogQuery("google", "en-US", CMD_SEARCH, mapToDialogContexts(CONTEXT_NO)));
        dialogQueryService.save(new DialogQuery("[request:?]", "en-US", CMD_SEARCH_FOR, mapToDialogContexts(CONTEXT_SEARCH_FOR)));
        dialogQueryService.save(new DialogQuery("show search history", "en-US", CMD_SHOW_SEARCH_HISTORY, mapToDialogContexts(CONTEXT_NO)));

        dialogReplyService.save(new DialogReply(REPLY_WHAT_DO_YOU_WANT_TO_SEARCH, CONTEXT_SEARCH_FOR,
                mapToReplyVariantList(new ReplyVariant("What do you want to search?", "en-US")), null));

        dialogQueryService.save(new DialogQuery("go to [key1:destination.value] by [key2:vehicle.value]", "en-US", "GO_TO_DESTINATION_BY_VECHICLE", mapToDialogContexts(CONTEXT_NO)));
        dialogQueryService.save(new DialogQuery("go to [key1:destination.value]", "en-US", "GO_TO_DESTINATION", mapToDialogContexts(CONTEXT_NO)));
        dialogQueryService.save(new DialogQuery("go to [key1:destination.value] right now", "en-US", "GO_TO_DESTINATION_NOW", mapToDialogContexts(CONTEXT_NO)));

        destinationRepository.save(new Destination("home"));
        destinationRepository.save(new Destination("work"));

        vechicleRepository.save(new Vehicle("car"));
        vechicleRepository.save(new Vehicle("bus"));
        vechicleRepository.save(new Vehicle("several words test"));
    }

    private List<DialogContext> mapToDialogContexts(String... contextNames) {
        return Arrays.asList(contextNames).stream()
                .map(contextName -> fetchDialogContextByName(contextName))
                .collect(Collectors.toList());
    }

    private DialogContext fetchDialogContextByName(String contextName) {
        DialogContext dialogContext = dialogContextService.findByName(contextName);
        if (dialogContext == null) {
            dialogContext = new DialogContext(contextName);
            dialogContextService.save(dialogContext);
        }
        return dialogContext;
    }

    private List<ReplyVariant> mapToReplyVariantList(ReplyVariant... replyVariants) {
        return Arrays.asList(replyVariants).stream().collect(Collectors.toList());
    }
}
