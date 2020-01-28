package com.switzerland.youtube.youtubedon.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateDonateurDto {

    private String nom;
    private String prenom;
    private String pseudo;
    private String adresse;
    private LocalDate datecreation;
    private LocalDate datemodification;


}
