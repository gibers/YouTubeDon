package com.switzerland.youtube.youtubedon.entities;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Donateur",
        uniqueConstraints = @UniqueConstraint(columnNames = "pseudo"))
public class DonateurEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "link_seq")
  @SequenceGenerator(name="link_seq", sequenceName = "seq_donateur", allocationSize=1)
  private long id;

  private String nom;
  private String prenom;
  private String pseudo;
  private String adresse;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime datecreation;

  @LastModifiedDate
  private LocalDateTime datemodification;

  protected DonateurEntity() {
  }

  public DonateurEntity(String nom, String prenom, String pseudo) {
    this.nom = nom;
    this.prenom = prenom;
    this.pseudo = pseudo;
  }


}
