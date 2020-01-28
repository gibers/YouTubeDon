package com.switzerland.youtube.youtubedon.controllers;

import com.switzerland.youtube.youtubedon.domainService.ManageDonateurs;
import com.switzerland.youtube.youtubedon.domainService.ManageQuestion;
import com.switzerland.youtube.youtubedon.dtos.CreateDonateurDto;
import com.switzerland.youtube.youtubedon.dtos.DonDto;
import com.switzerland.youtube.youtubedon.entities.DonateurEntity;
import com.switzerland.youtube.youtubedon.services.CurrentTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
public class Control1 {

    private static final Logger log = LoggerFactory.getLogger(Control1.class);

    @Autowired
    private CurrentTimeService currTimeService;

    @PersistenceContext
    EntityManager entityManager;

    private ManageDonateurs manageDonateurs;
    private ManageQuestion manageQuestion;

    @Autowired
    public Control1(ManageDonateurs manageDonateurs, ManageQuestion manageQuestion) {
        this.manageDonateurs = manageDonateurs;
        this.manageQuestion = manageQuestion;
    }

    @RequestMapping(value = "/currentDateTime")
    public String getCurrentDateTime() {
        DonateurEntity donateurEntity = entityManager.find(DonateurEntity.class, 1l);
        log.debug(String.format("Dans la methode getCurrentDateTime donateurEntity : %s ", donateurEntity ));
        return currTimeService.getCurrentDateTime();
    }

    @PostMapping( path = "/createDonateur",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = "application/json" )
    public void createDonateur(@RequestBody CreateDonateurDto donateurDto) {
        log.debug("appel de createDonateur avec param : " + donateurDto);
        manageDonateurs.insertNewDonateur(donateurDto);
    }

    @PostMapping( path = "/poserQuestion",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = "application/json" )
    public void poserQuestion(@RequestBody DonDto donDto) throws Exception {
        log.debug("don : " + donDto);
        manageDonateurs.insertDon(donDto);
    }

    @GetMapping( path = "/fermerquestion/{pseudo}")
    public String fermerquestion(@PathVariable("pseudo") String pseudo) throws Exception {
        log.debug(String.format("fermerquestion de %s", pseudo) );
        manageQuestion.closeQuestion(pseudo);

//        DonateurEntity donateurEntity = entityManager.find(DonateurEntity.class, 1l);
//        log.debug(String.format("Dans la methode getCurrentDateTime donateurEntity : %s ", donateurEntity ));
        return "";
    }

}
