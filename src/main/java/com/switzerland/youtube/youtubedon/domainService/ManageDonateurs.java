package com.switzerland.youtube.youtubedon.domainService;

import com.switzerland.youtube.youtubedon.dtos.CreateDonateurDto;
import com.switzerland.youtube.youtubedon.dtos.DonDto;
import com.switzerland.youtube.youtubedon.entities.DonateurEntity;
import com.switzerland.youtube.youtubedon.entities.DonsEntity;
import com.switzerland.youtube.youtubedon.entities.QuestionEntity;
import com.switzerland.youtube.youtubedon.repository.DonateurRepository;
import com.switzerland.youtube.youtubedon.repository.DonsRepository;
import com.switzerland.youtube.youtubedon.repository.QuestionRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManageDonateurs {

    private static final Logger log = LoggerFactory.getLogger(ManageDonateurs.class);

    private DonateurRepository donateurRepository;
    private DonsRepository donsRepository;
    private QuestionRepository questionRepository;
    private ManageGlobal manageGlobal;

    @Autowired
    public ManageDonateurs(DonateurRepository donateurRepository, DonsRepository donsRepository,
                           QuestionRepository questionRepository, ManageGlobal manageGlobal) {
        this.donateurRepository = donateurRepository;
        this.donsRepository = donsRepository;
        this.questionRepository = questionRepository;
        this.manageGlobal = manageGlobal;
    }

    public void insertNewDonateur(CreateDonateurDto donateurDto) {
        ModelMapper modelMapper = new ModelMapper();
        DonateurEntity donateurEntity = modelMapper.map(donateurDto, DonateurEntity.class);
//        CreateDonateurDto createDonateurDto = new CreateDonateurDto();
        DonateurEntity save = donateurRepository.save(donateurEntity);
    }

    public void insertDon(DonDto donDto) throws Exception {
        log.debug("lancement de la méthode insertDon.");
        ModelMapper modelMapper = new ModelMapper();
        DonateurEntity byPseudo = manageGlobal.getPseudo(donDto.getPseudo());
        List<DonsEntity> lesDonsOuverts = listDonRelyToOpenQ(byPseudo);

        log.info(String.format("lesDonsOuverts: %s ", lesDonsOuverts));
        log.info(String.format("donDto.getQuestion(): %s ", donDto.getQuestion()));
        log.info(String.format("trimToEmpty(): %s ", StringUtils.trimToEmpty(donDto.getQuestion()) ) );

        // Si dons Ouverts et nouvelle question, on rejette le don.
        if (!lesDonsOuverts.isEmpty() && !StringUtils.trimToEmpty(donDto.getQuestion()).isEmpty()) {
            // renvoyer le don.
            log.info("rejet, pas d'insertion de nouvelle question.");
            String collect = lesDonsOuverts.stream().map(x -> x.getQuestion().getPhrase())
                    .collect(Collectors.joining(" -- "));
            throw new Exception("Des questions cont encore ouvertes: " + collect);
        } else if(!lesDonsOuverts.isEmpty() && StringUtils.trimToEmpty(donDto.getQuestion()).isEmpty()) {
            // insérer dons.
            log.info("OK_ dons ouverts et pas de question");
            donAutorise(donDto);
            long idQuestion = lesDonsOuverts.get(0).getQuestion().getId();
            insertIntoExistingQuestion(byPseudo, idQuestion, donDto.getMontant());
        } else if (lesDonsOuverts.isEmpty() && !StringUtils.trimToEmpty(donDto.getQuestion()).isEmpty()) {
            log.info("OK_ insertion d'une nouvelle question et d'un don");
            donAutorise(donDto);
            QuestionEntity questionEntity = insererQuestion(donDto.getQuestion(), 1);
            insererDon(byPseudo, questionEntity, donDto.getMontant());
        } else if (lesDonsOuverts.isEmpty() && StringUtils.trimToEmpty(donDto.getQuestion()).isEmpty()) {
            log.info("insertion d'un don simple relié à question vide");
            insertDonRelyToEmptyQuestion(byPseudo, donDto.getMontant());
        }

//        DonsEntity donsEntity = modelMapper.map(donDto, DonsEntity.class);
//        donsEntity.setDonateur(byPseudo);
//        donsEntity.setQuestion("ccccccc");
//        DonsEntity save = donsRepository.save(donsEntity);

    }

    private void donAutorise(DonDto donDto) throws Exception {
        Long montant = donDto.getMontant();
        if(montant < 1) {
            throw new Exception("pas de don inférieur à 1€.");
        }
    }

    private void insertIntoExistingQuestion(DonateurEntity byPseudo, long idQuestion, Long montant) throws Exception {
        Optional<QuestionEntity> byId = questionRepository.findById(idQuestion);
        if(byId.isPresent()) {
            insererDon(byPseudo, byId.get(), montant);
        } else {
            throw new Exception("Pas de question avec l'id: " + idQuestion);
        }
    }

    private DonsEntity insererDon(DonateurEntity donateur, QuestionEntity question, long montant) {
        DonsEntity donsEntity = new DonsEntity();
        donsEntity.setMontant(montant);
        donsEntity.setDonateur(donateur);
        donsEntity.setQuestion(question);
        DonsEntity save = donsRepository.save(donsEntity);
        return save;
    }

    private QuestionEntity insererQuestion(String question, int status) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setPhrase(question);
        questionEntity.setStatus(status);
        QuestionEntity save = questionRepository.save(questionEntity);
        return save;
    }

    private boolean hasDonsOuvert(DonateurEntity donateur) {
        if (listDonRelyToOpenQ(donateur).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void insertDonRelyToEmptyQuestion(DonateurEntity donateur, long montant) throws Exception {
        List<DonsEntity> byDonateurId = donsRepository.findByDonateurIdAndQuestionPhrase(donateur.getId(), "---");
        long idQuestionVide;
        if(!byDonateurId.isEmpty()) {
            idQuestionVide = byDonateurId.get(0).getQuestion().getId();
        } else {
            QuestionEntity questionInserted = insererQuestion("---", 0);
            idQuestionVide = questionInserted.getId();
        }
        insertIntoExistingQuestion(donateur, idQuestionVide, montant);
    }

    private List<DonsEntity> listDonRelyToOpenQ(DonateurEntity donateur) {
        List<DonsEntity> byDonateurId = donsRepository.findByDonateurId(donateur.getId());
        List<DonsEntity> listDonsRelyToOpenQuestion = byDonateurId.stream()
                .filter(x -> x.getQuestion().getStatus() == 1)
                .collect(Collectors.toList());
        return listDonsRelyToOpenQuestion;
    }

}
