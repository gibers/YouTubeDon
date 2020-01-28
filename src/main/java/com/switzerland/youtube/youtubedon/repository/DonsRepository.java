package com.switzerland.youtube.youtubedon.repository;

import com.switzerland.youtube.youtubedon.entities.DonsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonsRepository extends CrudRepository<DonsEntity, Long> {

    List<DonsEntity> findByDonateurId(long idDonateur);

    List<DonsEntity> findByDonateurIdAndQuestionPhrase(long idDonateur, String question);

    List<DonsEntity> findByDonateurIdAndQuestionDonsEntity(long idDonateur, long idQuestion);

    List<DonsEntity> findByDonateurIdAndQuestionStatus(long idDonateur, int status);

//    @Override
//    Iterable<DonsEntity> findAllById(Iterable<Long> iterable);
}
