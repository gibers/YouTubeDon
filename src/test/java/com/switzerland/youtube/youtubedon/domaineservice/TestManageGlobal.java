package com.switzerland.youtube.youtubedon.domaineservice;

import com.switzerland.youtube.youtubedon.domainService.ManageGlobal;
import com.switzerland.youtube.youtubedon.dtos.CustomException;
import com.switzerland.youtube.youtubedon.entities.DonateurEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@ImportResource("/application.yml")
@ConfigurationProperties(prefix = "my")
public class TestManageGlobal {

    private static final Logger log = LoggerFactory.getLogger(TestManageGlobal.class);

    @Autowired
    private ManageGlobal manageGlobal;

    @Autowired
    private Environment env;

    // /!\ le getter est obligatoire pour que l'attribut de classe (servers) soit rempli.
    private List<String> servers = new ArrayList<String>();
    public List<String> getServers() {
        return servers;
    }

    @Test
    public void testGetPseudo() throws Exception {
        log.debug("lancement test1() env.getActiveProfiles() " + env.getActiveProfiles()[0]);
        log.debug(String.format("les servers sont: %s ", servers));
        assertTrue(manageGlobal != null);
        DonateurEntity joe2 = manageGlobal.getPseudo("joe2");
        assertTrue(joe2 != null);
    }

    @Test
    public void testGetPseudo_error() {
        assertThrows(CustomException.class, () -> {
            manageGlobal.getPseudo("joe21");
        });
    }

}
