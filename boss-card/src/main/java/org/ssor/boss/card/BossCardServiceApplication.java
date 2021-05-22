package org.ssor.boss.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "org.ssor.boss.core.repository" })
@EntityScan(basePackages = { "org.ssor.boss.core.entity" })
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@EnableSwagger2
public class BossCardServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BossCardServiceApplication.class, args);
    }
}
