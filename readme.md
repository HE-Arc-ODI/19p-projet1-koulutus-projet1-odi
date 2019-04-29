# Koulutus
>description du projet

Notre application a pour but à l'aide de JAX-RS et du squelette de projet fourni de gérer un programme contenant des sessions, des courses ainsi que des participants.
Koulutus (apprendre en Finnois) est une api pour la gestion de formations continues utilisée par des collectivités publiques et des associations. 

***
## Mise en place
>Comment mettre en place le projet pour pouvoir le tester chez soi

### Serveur Tomcat
Il vous faudra configurer un serveur Tomcat en localhost pour lancer le projet.
> 'collectionmultimedia:war exploded' artifact

### Les requêtes Jaxrs
Notre projet fonctionne grâce aux requêtes Jaxrs(PUT, GET, POST) ces requêtes sont définies dans postman à des fins de tests. 

- Créer un programme 
>POST ..
- Lister les programmes 
>GET ..
- Lister les participants
>GET..
- Ajouter un nouveau participant 
>POST..
- Récupérer les détails d'un participant
>GET..
- Supprimer un participant
>DELET..
- Modifier les détails d'un participant
>PUT
- Lister les courses auxquelles est enregistré le participant
>GET 
- Lister toutes les sessions pour un programme et une course donnée 
>GET
- Créer une session pour un programme et une course donnée
>POST
- Récupérer tout les participants d'une course donnée
>GET 
- Enregistrer un participant à une course
>POST
- Récupérer les détails d'une session par rapport à une course et un programme donnés
>GET
- Supprimer une session par rapport à une course et un programme donnés
>DELET
- Modifier une session existante 
>PUT

## Fait avec
>Présentation des technologies/outils utilisés

- JAX-RS
- Maven

## Auteurs
>Les auteurs

- Carmine Perna Gutiérrez
- Fatima Majid
