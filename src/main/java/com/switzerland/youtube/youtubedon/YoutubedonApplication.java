package com.switzerland.youtube.youtubedon;

import com.switzerland.youtube.youtubedon.entities.DonateurEntity;
import com.switzerland.youtube.youtubedon.repository.DonateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class YoutubedonApplication {

    private static final Logger log = LoggerFactory.getLogger(YoutubedonApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(YoutubedonApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(DonateurRepository repository) {
//        return (args) -> {
//            // save a few customers
//            repository.save(new DonateurEntity("Jack", "Bauer", "joe1"));
//            repository.save(new DonateurEntity("Chloe", "O'Brian", "joe2"));
//            repository.save(new DonateurEntity("Kim", "Bauer", "joe3"));
//            repository.save(new DonateurEntity("David", "Palmer", "joe4"));
//            repository.save(new DonateurEntity("Michelle", "Dessler", "joe5"));
//
//            // fetch all customers
//            log.info("Customers found with findAll():");
//            log.info("-------------------------------");
//            for (DonateurEntity donateur : repository.findAll()) {
//                log.info(donateur.toString());
//            }
//            log.info("");
//
//            // fetch an individual customer by ID
//            Optional<DonateurEntity> customer = repository.findById(1L);
//            log.info("Customer found with findById(1L):");
//            log.info("--------------------------------");
//            log.info(customer.toString());
//            log.info("");
//
//            // fetch customers by last name
//            log.info("Customer found with findByLastName('Bauer'):");
//            log.info("--------------------------------------------");
//            repository.findByPrenom("Bauer").forEach(bauer -> {
//                log.info(bauer.toString());
//            });
//            // for (Customer bauer : repository.findByLastName("Bauer")) {
//            // 	log.info(bauer.toString());
//            // }
//            log.info("");
//        };
//    }

}
