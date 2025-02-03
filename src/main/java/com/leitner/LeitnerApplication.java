package com.leitner;

import io.github.cdimascio.dotenv.Dotenv;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Leitner API", version = "1.0", description = "API pour gérer les cartes Leitner"))
public class LeitnerApplication {

    static {
        String activeProfile = System.getProperty("spring.profiles.active", "default");
        Dotenv dotenv = activeProfile.equals("test") ? Dotenv.configure().filename(".env.test").load()
                : Dotenv.configure().load();

        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    public static void main(String[] args) {
        SpringApplication.run(LeitnerApplication.class, args);
    }
}
