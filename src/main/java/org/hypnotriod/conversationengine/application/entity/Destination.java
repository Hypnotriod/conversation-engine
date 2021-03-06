package org.hypnotriod.conversationengine.application.entity;

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
@Table(name = "destination")
public class Destination implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    public Destination() {
    }

    public Destination(String value) {
        this.value = value;
    }
}
