package com.switzerland.youtube.youtubedon.repository;

import com.switzerland.youtube.youtubedon.entities.DonateurEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonateurRepository extends CrudRepository<DonateurEntity, Long> {

    List<DonateurEntity> findByPrenom (String prenom);
    DonateurEntity findByPseudo (String pseudo);

}
