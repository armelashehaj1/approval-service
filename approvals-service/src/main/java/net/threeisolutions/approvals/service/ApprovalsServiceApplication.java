package net.threeisolutions.approvals.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("net.threeisolutions.approvals.service.config.properties")
public class ApprovalsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApprovalsServiceApplication.class, args);
    }
}
