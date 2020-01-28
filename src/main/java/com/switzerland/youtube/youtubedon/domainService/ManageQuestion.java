package com.switzerland.youtube.youtubedon.domainService;

import com.switzerland.youtube.youtubedon.entities.DonateurEntity;
import com.switzerland.youtube.youtubedon.entities.DonsEntity;
import com.switzerland.youtube.youtubedon.entities.QuestionEntity;
import com.switzerland.youtube.youtubedon.repository.DonateurRepository;
import com.switzerland.youtube.youtubedon.repository.DonsRepository;
import com.switzerland.youtube.youtubedon.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class ManageQuestion {

    private static final Logger log = LoggerFactory.getLogger(ManageQuestion.class);

    private DonateurRepository donateurRepository;
    private DonsRepository donsRepository;
    private QuestionRepository questionRepository;
    private ManageGlobal manageGlobal;

    @Autowired
    public ManageQuestion(DonateurRepository donateurRepository, DonsRepository donsRepository,
                           QuestionRepository questionRepository, ManageGlobal manageGlobal) {
        this.donateurRepository = donateurRepository;
        this.donsRepository = donsRepository;
        this.questionRepository = questionRepository;
        this.manageGlobal = manageGlobal;
    }

    public void closeQuestion(String pseudo) throws Exception {
        DonateurEntity byPseudo = manageGlobal.getPseudo(pseudo);
        List<DonsEntity> byDonateurIdAndQuestionStatus =
                donsRepository.findByDonateurIdAndQuestionStatus(byPseudo.getId(), 1);
        if (CollectionUtils.isEmpty(byDonateurIdAndQuestionStatus)) {
            throw new Exception(String.format("Pseudo: %s n'a pas de question ouverte!", byPseudo));
        }
        Assert.isTrue(byDonateurIdAndQuestionStatus.size() == 1,
                "Il ne doit pas y avoir plus de 1 question ouverte");
        QuestionEntity question = byDonateurIdAndQuestionStatus.get(0).getQuestion();
        question.setStatus(0);
        questionRepository.save(question);
    }


}
