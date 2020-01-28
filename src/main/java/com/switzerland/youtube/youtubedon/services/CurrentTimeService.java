package com.switzerland.youtube.youtubedon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@ConfigurationProperties(prefix = "my")
public class CurrentTimeService {

    @Autowired
    private Environment env;

    public String getCurrentDateTime() {
        String message = String.format("hello from the environnement %s , current Time est : ",
                env.getActiveProfiles());
        LocalDateTime localDateTime = LocalDateTime.now();
        servers.forEach(x -> System.out.println(x));
        return servers.size() + " -> " + message + localDateTime.toString();
    }

    private List<String> servers = new ArrayList<String>();

    public List<String> getServers() {
        return this.servers;
    }

}
