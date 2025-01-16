# projetFullStackAshrafMauroNael
Projet développement full stack - Spring Boot React - Nael, Mauro, Ashraf


Lien vers les tests postman : https://www.postman.com/onsite-ote/workspace/back

## Description

### Cette application est une solution Full-Stack utilisant Spring Boot pour le back-end et React pour le front-end. Voici les principales fonctionnalités de la partie back-end :
Gestion des événements d'authentification (connexion, inscription, ..).
Communication avec la base de données pour la gestion des données persistantes.
Exposition d'une API REST accessible depuis la partie front-end.

### Prérequis

Maven pour la gestion des dépendances et le build du projet

Node.js et npm pour le front-end

Base de données (par exemple MySQL ou PostgreSQL, selon votre configuration)

### Installation

Back-End (Spring Boot)

Clonez le dépôt Git :

git clone https://github.com/ash9466/projetFullStackAshrafMauroNael.git

Accédez au dossier du projet :

  '''bash
  cd projetFullStackAshrafMauroNael

  '''bash
  cd src/main/java/com.fullstack.projet

  - Aller dans le fichier ProjetApplication.java
    - Exécuter le fichier

## Réponses aux questions du document

  ### 1. Concernant le degré de conformité de notre projet aux principes Clean Code, nous avons fait de notre possible pour que les noms des variables, fonctions et modules soient aussi
  descriptifs que possible afin que la logique générale du projet atteigne facilement l'attention du lecteur, mais aussi afin que nous soyons plus productifs dans notre travail en modélisant
  au maximum notre pensée de facon haut niveau à travers ces principes de nommage.

  Dès que nous percevions la possibilité d'un bloc de code qui pourrait provoquer une erreur, nous avons encapsulé ces parties dans un bloc try en gérant toutes les exceptions possibles quant aux requêtes API ou de validation de données 

   #### Exemple d'une fonction bien nommée ayant un objectif précis et respectant une fonctionnalité.
   
   ##### Elle exécute un bloc de code encapsulé dans un try pouvant générer des exceptions qui sont bien manipuler.
   
     ```bash
      @PreAuthorize("hasAuthority('TEACHER') || hasAuthority('ADMIN')")
      @GetMapping("/tool/{toolId}")
      public ResponseEntity<Object> getFeedbacksByToolId(@PathVariable Long toolId) {
          LOG.info("Getting feedbacks for tool {}", toolId);
          try{
              List<FeedbackDto> feedbackDtos = feedbackService.getFeedbacksByToolId(toolId).stream().map(FeedbackDto::fromFeedback).toList();
              LOG.info("Successfully retrieved feedbacks");
              return ResponseEntity.ok(feedbackDtos);
          } catch (ValidationException e) {
              return ErrorHandlingUtils.handleBadRequest(LOG,e);
          } catch (Exception e) {
              return ErrorHandlingUtils.handleInternalServerError(LOG, "L'ajout du feedback a echoué", e);
          }
      }

  ### 2. Les principes SOLID

  #### 2.1 Principe SRP respectée

       Nous avons bien implémenter ce principe essentiel à la bonne maintenance d'une application et de son développement itératif et simpliste. Chaque classe et fonction a une résponsabilité unique et isolée.
      Dans nos folders "Controller", "Exceptions" et "Utils", cette norme est respectée avec les classes UserController et AuthenticationController pour les controllers ou ErrorBody et ValidationException
      pour les exceptions

  #### 2.2 Principe DIP respectée

      Nous avons usé du principe d'injection de dépendances de service ou respository dans les controller.

      ```bash
       public UserController(IUserService userService) {
         this.userService = userService;
      }

  #### Principe ISP respectée

     Les interfaces comme IJwtService et IFeedbackService sont spévifiques à des fonctionnalités bien précises.
     Aucun signe de surcharge des interfaces (méthodes non utilisées ou inutiles dans le code).

  #### Principe LSP respectée
  
    Le principe LSP est bien respecté grâce à l’utilisation des interfaces pour abstraire les comportements.
     
  #### Principe OCP respectée
    
    Les classes concrètes telles que JwtService ou FeedbackService ne doivent pas implémenter de nouvelles méthodes pour ajouter de nouvelles fonctionnalités. 
    La maintenabilité du code est donc respectée.
