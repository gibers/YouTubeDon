package com.switzerland.youtube.youtubedon.domainService;

import com.switzerland.youtube.youtubedon.dtos.CustomException;
import com.switzerland.youtube.youtubedon.entities.DonateurEntity;
import com.switzerland.youtube.youtubedon.repository.DonateurRepository;
import com.switzerland.youtube.youtubedon.repository.DonsRepository;
import com.switzerland.youtube.youtubedon.repository.QuestionRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ManageGlobal {

    private static final Logger log = LoggerFactory.getLogger(ManageGlobal.class);

    private DonateurRepository donateurRepository;
    private DonsRepository donsRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public ManageGlobal(DonateurRepository donateurRepository, DonsRepository donsRepository,
                          QuestionRepository questionRepository) {
        this.donateurRepository = donateurRepository;
        this.donsRepository = donsRepository;
        this.questionRepository = questionRepository;
    }

    public DonateurEntity getPseudo(String pseudo) throws CustomException {
        DonateurEntity byPseudo = donateurRepository.findByPseudo(pseudo);
        if(byPseudo == null) {
            throw new CustomException(String.format("Pseudo %s est inconnu!", pseudo));
        }
        Assert.isTrue(StringUtils.trimToEmpty(byPseudo.getPseudo()).length()>0 ,
                "Pseudo in Donateur Table should never be null or empty!");
        return byPseudo;
    }

}
