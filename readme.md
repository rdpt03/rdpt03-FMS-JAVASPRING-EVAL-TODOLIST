# TodoList – Application Spring Boot
## Description

Il s’agit d’une application web TodoList simple développée avec Spring Boot.
Elle permet aux utilisateurs authentifiés de gérer leurs tâches personnelles, tandis que les visiteurs peuvent consulter une page d’accueil avec un contenu de démonstration.

Chaque tâche contient :

   - Nom
   - Description
   - Date
   - Catégorie
   - État (To Do / In Progress / Done)

---
## Fonctionnalités Utilisateur

Les utilisateurs authentifiés peuvent :

   - Créer de nouvelles tâches
   - Modifier des tâches existantes
   - Supprimer des tâches
   - Consulter leur liste de tâches
   - Filtrer les tâches par catégorie ou par date
   - Suivre l’état des tâches (To Do / In Progress / Done)

---
## Fonctionnalités Visiteur

Les utilisateurs non authentifiés peuvent :

   - Accéder à la page d’accueil
   - Consulter des tâches fictives (mock data)
   - Ne peuvent pas créer, modifier ou supprimer des tâches
---
## Fonctionnalités Bonus
   - Système de catégories pour organiser les tâches
   - Système d’authentification avec Spring Security
   - Validation des formulaires et association des tâches à l’utilisateur

---
## Expressions des besoins – TodoList
1. Utilisateur connecté :
   - Peut ajouter, modifier, supprimer et consulter ses tâches.
   - Chaque tâche possède un nom, une description, une date, une catégorie et un état (ToDo / In Progress / Done).
   - Peut filtrer ou trier les tâches par catégorie ou par date.
2. Visiteur non connecté :
   - Peut consulter un ensemble de tâches fictives sur la page d’accueil.
   - Ne peut pas modifier ou ajouter de tâches.
   - Bonus / fonctionnalités supplémentaires :
   - L’affichage peut être organisé par colonnes : ToDo, In Progress, Done.
---
## Spécifications Techniques – TodoList
   - Langage : Java 17
   - Framework Backend : Spring Boot 4.0.5
   - Modules utilisés :
      - Spring Data JPA
      - Spring Security
      - Spring Web
      - Base de données
      - MariaDB Driver
   
   - Frontend
      - Spring
      - Bootstrap 

   - Outils / IDE
       - IntelliJ IDEA (édition Community ou Ultimate)
       - GIT + SourceTree
----
## Sécurité
   - Authentification via Spring Security
   - Mots de passe chiffrés avec BCrypt
   - Chaque utilisateur ne peut accéder qu’à ses propres tâches

## Lancer le projet
   1. Cloner le dépôt :
      - `git clone https://github.com/rdpt03/rdpt03-FMS-JAVASPRING-EVAL-TODOLIST`
   2. Configurer la base de données dans application.properties
   3. Lancer l’application :
      - `./mvnw spring-boot:run`
   4. Accéder à :
      - `http://localhost:8080`