package com.switzerland.youtube.youtubedon.repository;

import com.switzerland.youtube.youtubedon.entities.DonsEntity;
import com.switzerland.youtube.youtubedon.entities.QuestionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {




}
