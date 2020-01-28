package com.switzerland.youtube.youtubedon.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@IdClass(CompositeKey.class)
@Table(name = "Question")
public class QuestionEntity {

    public QuestionEntity() {}

    @Id
//    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "link_seq")
//    @SequenceGenerator(name="link_seq", sequenceName = "seq_question", allocationSize=1)
    private long DonsEntity_id;

    @Id
    private String mapPhraseStatus_ORDER;

    private String phrase;

    private int status;

    @CreationTimestamp
    private LocalDateTime datecreation;

    public long getId() {
        return DonsEntity_id;
    }
}
