package org.ssor.boss.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan("org.ssor.boss")
@EntityScan({ "org.ssor.boss.core.entity" })
@CrossOrigin(origins = "http://localhost:4200")
@EnableJpaRepositories({ "org.ssor.boss.core.repository" })
public class BossCardServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BossCardServiceApplication.class, args);
    }
}
