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
@Table(name = "dialog_context",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name"})})
public class DialogContext implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "contexts")
    private List<DialogQuery> dialogQuery;
    private String name;

    public DialogContext() {
    }

    public DialogContext(String name) {
        this.name = name;
    }
}
