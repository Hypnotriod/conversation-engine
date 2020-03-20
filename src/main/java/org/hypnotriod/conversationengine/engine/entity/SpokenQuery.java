package org.hypnotriod.conversationengine.engine.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String context;

    public SpokenQuery() {
    }

    public SpokenQuery(String regexp, String query, String language, String command, String context) {
        this.regexp = regexp;
        this.query = query;
        this.language = language;
        this.command = command;
        this.context = context;
    }
}
