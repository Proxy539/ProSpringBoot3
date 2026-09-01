package com.apress.myretro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({MyRetroProperties.class})
public class MyRetroConfiguration {

    Logger log = LoggerFactory.getLogger(MyretroApplication.class);

    @Value("${users.server}")
    String server;

    @Value("${users.port}")
    Integer port;

    @Value("${users.username}")
    String username;

    @Value("${users.password}")
    String password;

    @Bean
    ApplicationListener<ApplicationReadyEvent> init(MyRetroProperties myRetroProperties) {
        return event -> {
            log.info("\nThe users service properties are:\n- Server: {}\n- Port: {}\n- Username: {}\n- Password: {}",
                    myRetroProperties.users.server,
                    myRetroProperties.users.port,
                    myRetroProperties.users.username,
                    myRetroProperties.users.password);

        };
    }

}
