package com.switzerland.youtube.youtubedon.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "Dons")
public class DonsEntity {

    public DonsEntity() {}

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "link_seq")
    @SequenceGenerator(name="link_seq", sequenceName = "seq_dons", allocationSize=1)
    private long id;

    @ManyToOne
    private DonateurEntity donateur;

    @ManyToOne
    private QuestionEntity question;

    private long montant;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime datecreation;

    @ElementCollection
    @CollectionTable(name = "question")
//    @MapKeyColumn(name = "phrase")
    @OrderColumn
    @Column(name = "phrase")
    protected List<String> mapPhraseStatus = new ArrayList<>();

}
