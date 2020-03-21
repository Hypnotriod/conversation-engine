package org.hypnotriod.conversationengine.engine.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Ilya Pikin
 */
@Entity
@Data
@Table(name = "spoken_query")
public class SpokenQuery implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String regexp;
    private String query;
    private String language;
    private String command;
    @ManyToMany
    @JoinTable(
            name = "spoken_query_spoken_context",
            joinColumns = @JoinColumn(name = "spoken_query_id"),
            inverseJoinColumns = @JoinColumn(name = "spoken_context_id"))
    private List<SpokenContext> contexts;

    public SpokenQuery() {
    }

    public SpokenQuery(String regexp, String query, String language, String command, List<SpokenContext> contexts) {
        this.regexp = regexp;
        this.query = query;
        this.language = language;
        this.command = command;
        this.contexts = contexts;
    }
}
