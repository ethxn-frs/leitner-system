# 📝 Leitner System API

## 📜 Description du Projet
Le **Leitner System API** est une application permettant la gestion de fiches d’apprentissage basées sur le **système Leitner**.  
Elle permet aux utilisateurs de créer des cartes d’apprentissage et d'effectuer des révisions de manière optimisée grâce à un **système de répétition espacée**.

L’API est développée avec **Spring Boot**, suit les principes **SOLID**, **DDD** et applique une **architecture hexagonale**.  
Elle utilise **PostgreSQL** pour le stockage des données et **Spring Security** pour la gestion des utilisateurs.

---

## 🚀 Installation & Configuration
### 📦 Prérequis
- **Java 21** installé
- **Gradle** installé (`brew install gradle` sur macOS)
- **PostgreSQL** installé et en cours d'exécution

---

### 📂 Configuration du `.env`
Avant de lancer l'application, créez un fichier `.env` dans **`src/main/resources/`** :

📌 **Emplacement** :  
`leitner-system/src/main/resources/.env`

📌 **Contenu du fichier `.env`** :
```ini
DB_HOST=
DB_PORT=
DB_NAME=
DB_USER=
DB_PASSWORD=
```

## 🏗 Build & Lancement du Projet
### ⚙️ Construire le projet
./gradlew build

### 🚀 Démarrer l’application

./gradlew bootRun
L'application sera accessible sur : http://localhost:8080

### 🔬 Lancer les tests

🧪 Exécuter les tests unitaires & d'intégration

./gradlew test

⚠️ Les tests utilisent une base de données dédiée (leitner_db_test).

📌 Fichier de configuration des tests :
📍 src/test/resources/application-test.properties

## 📜 Documentation Swagger
### L'API est documentée avec Swagger et accessible via :

📌 Accéder à la documentation :

http://localhost:8080/swagger-ui.html
📌 Fichier OpenAPI/Swagger :
📍 src/main/resources/Swagger.yml

### 🛠 Commandes utiles

Build le projet	./gradlew build
Lancer l'application	./gradlew bootRun
Exécuter les tests	./gradlew test

