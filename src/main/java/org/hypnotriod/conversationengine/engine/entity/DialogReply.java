package org.hypnotriod.conversationengine.engine.entity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private List<ReplyVariant> spokenReplies;
    @OneToMany(
            mappedBy = "dialogReply",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<ReplyVariant> displayReplies;

    public DialogReply() {
    }

    public DialogReply(String name, String contextName, List<ReplyVariant> spokenReplies, List<ReplyVariant> displayReplies) {
        this.name = name;
        this.contextName = contextName;
        this.spokenReplies = spokenReplies;
        this.displayReplies = displayReplies;

        if (this.spokenReplies != null) {
            this.spokenReplies.forEach(replyVariant -> setDialogReply(replyVariant));
        }
        if (this.displayReplies != null) {
            this.displayReplies.forEach(replyVariant -> setDialogReply(replyVariant));
        }
    }

    public ReplyVariant getSpokenReply(String languageCode) {
        List<ReplyVariant> replyVariants = getSpokenReplies(languageCode);
        return replyVariants != null && replyVariants.size() > 0 ? replyVariants.get(0) : null;
    }

    public ReplyVariant getDisplayReply(String languageCode) {
        List<ReplyVariant> replyVariants = getDisplayReplies(languageCode);
        return replyVariants != null && replyVariants.size() > 0 ? replyVariants.get(0) : null;
    }

    public List<ReplyVariant> getSpokenReplies(String languageCode) {
        return spokenReplies.stream()
                .filter(replyVariant -> replyVariant.getLanguageCode().equals(languageCode))
                .collect(Collectors.toList());
    }

    public List<ReplyVariant> getDisplayReplies(String languageCode) {
        return displayReplies.stream()
                .filter(replyVariant -> replyVariant.getLanguageCode().equals(languageCode))
                .collect(Collectors.toList());
    }

    private ReplyVariant setDialogReply(ReplyVariant replyVariant) {
        replyVariant.setDialogReply(this);
        return replyVariant;
    }
}
