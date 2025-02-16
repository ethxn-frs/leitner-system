package com.leitner;

import io.github.cdimascio.dotenv.Dotenv;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Leitner API", version = "1.0", description = "API pour gérer les cartes Leitner"))
public class LeitnerApplication {

    public static void main(String[] args) {
        // Détermine si le profil actif est "test"
        String activeProfile = System.getProperty("spring.profiles.active", "default");

        // Charge .env ou .env.test en fonction du profil actif
        Dotenv dotenv = activeProfile.equals("test")
                ? Dotenv.configure().filename(".env.test").ignoreIfMissing().load()
                : Dotenv.configure().ignoreIfMissing().load();

        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        // Lancer l’application
        var context = SpringApplication.run(LeitnerApplication.class, args);

        // Vérifier quelle base de données est utilisée
        String dbUrl = context.getEnvironment().getProperty("spring.datasource.url");
        System.out.println("✅ Using database: " + dbUrl);
    }
}
