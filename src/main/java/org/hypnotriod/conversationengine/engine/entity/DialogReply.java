package org.hypnotriod.conversationengine.engine.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 *
 * @author Ilya Pikin
 */
@Entity
@Data
@Table(name = "dialog_reply",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name"})})
public class DialogReply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contextName;
    @OneToMany(
            mappedBy = "dialogReply",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @MapKey(name = "languageCode")
    private Map<String, ReplyVariant> spokenReplies;
    @OneToMany(
            mappedBy = "dialogReply",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @MapKey(name = "languageCode")
    private Map<String, ReplyVariant> displayReplies;

    public DialogReply() {
    }

    public DialogReply(String name, String contextName, List<ReplyVariant> spokenReplies, List<ReplyVariant> displayReplies) {
        this.name = name;
        this.contextName = contextName;
        this.spokenReplies = (spokenReplies != null)
                ? spokenReplies.stream()
                        .map(replyVariant -> setDialogReply(replyVariant))
                        .collect(Collectors.toMap(ReplyVariant::getLanguageCode, Function.identity()))
                : new HashMap<>();
        this.displayReplies = (displayReplies != null)
                ? displayReplies.stream()
                        .map(replyVariant -> setDialogReply(replyVariant))
                        .collect(Collectors.toMap(ReplyVariant::getLanguageCode, Function.identity()))
                : new HashMap<>();
    }

    private ReplyVariant setDialogReply(ReplyVariant replyVariant) {
        replyVariant.setDialogReply(this);
        return replyVariant;
    }
}
