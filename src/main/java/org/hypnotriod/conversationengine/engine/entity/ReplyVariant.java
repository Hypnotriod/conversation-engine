package org.hypnotriod.conversationengine.engine.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "reply_variant")
public class ReplyVariant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String languageCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dialog_reply_id", nullable = false)
    private DialogReply dialogReply;

    public ReplyVariant() {
    }

    public ReplyVariant(String text, String languageCode) {
        this.text = text;
        this.languageCode = languageCode;
    }
}
