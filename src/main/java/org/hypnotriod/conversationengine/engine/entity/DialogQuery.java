package org.hypnotriod.conversationengine.engine.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import static org.hypnotriod.conversationengine.engine.contants.TextManipulationConstants.REGEX_UTTERANCE_DATA;
import static org.hypnotriod.conversationengine.engine.contants.TextManipulationConstants.REGEX_DIALOG_QUERY_DATA;

/**
 *
 * @author Ilya Pikin
 */
@Entity
@Data
@Table(name = "dialog_query")
public class DialogQuery implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String regexp;
    private String query;
    private String languageCode;
    private String commandName;
    @ManyToMany
    @JoinTable(
            name = "dialog_query_dialog_context",
            joinColumns = @JoinColumn(name = "dialog_query_id"),
            inverseJoinColumns = @JoinColumn(name = "dialog_context_id"))
    private List<DialogContext> contexts;

    public DialogQuery() {
    }

    public DialogQuery(String query, String languageCode, String commandName, List<DialogContext> contexts) {
        this.regexp = buildRegExp(query);
        this.query = query;
        this.languageCode = languageCode;
        this.commandName = commandName;
        this.contexts = contexts;
    }

    private String buildRegExp(String query) {
        return "^" + query.replaceAll(REGEX_UTTERANCE_DATA, REGEX_DIALOG_QUERY_DATA) + "$";
    }
}
