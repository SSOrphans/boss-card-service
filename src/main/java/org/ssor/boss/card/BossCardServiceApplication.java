package org.ssor.boss.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("org.ssor.boss.core.repository")
public class BossCardServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BossCardServiceApplication.class, args);
	}
}
