package org.hypnotriod.conversationengine.engine.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 *
 * @author Ilya Pikin
 */
@Entity
@Data
@Table(name = "spoken_context",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name"})})
public class SpokenContext implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "contexts")
    private List<SpokenQuery> spokenQuery;
    private String name;

    public SpokenContext() {
    }

    public SpokenContext(String name) {
        this.name = name;
    }
}
