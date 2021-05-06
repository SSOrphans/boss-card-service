package org.ssor.boss.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "org.ssor.*", exclude = {SecurityAutoConfiguration.class})
@EntityScan(basePackages = "org.ssor.boss.*")
@EnableJpaRepositories(basePackages = "org.ssor.boss.*")
@EnableSwagger2
public class BossCardServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BossCardServiceApplication.class, args);
    }
}
