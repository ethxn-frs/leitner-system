package com.leitner;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class LeitnerApplicationTests {

    static {
        // Charger les variables d'environnement .env.test si profil "test"
        Dotenv dotenv = Dotenv.configure().filename(".env.test").ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        // Debug : Vérification du fichier chargé
        System.out.println("🔹 Test - Chargement des variables d'environnement depuis .env.test");
    }

    @Test
    void contextLoads() {
        System.out.println("✅ Using database: " + System.getProperty("spring.datasource.url"));
    }
}
