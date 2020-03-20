package org.hypnotriod.conversationengine.engine.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Ilya Pikin
 */
@Entity
@Data
@Table(name = "spoken_context")
public class SpokenContext implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "spoken_query_id")
    private SpokenQuery spokenQuery;
    private String name;

    public SpokenContext() {
    }

    public SpokenContext(String name) {
        this.name = name;
    }
}
