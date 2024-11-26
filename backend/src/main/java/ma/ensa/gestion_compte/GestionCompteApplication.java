package ma.ensa.gestion_compte;

import ma.ensa.gestion_compte.entities.Compte;
import ma.ensa.gestion_compte.entities.TypeCompte;
import ma.ensa.gestion_compte.repositories.CompteRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication //classe principale d'une application Spring Boot
public class GestionCompteApplication {
	public static void main(String[] args) {
		//Lancement de l'application Spring Boot
		SpringApplication.run(GestionCompteApplication.class, args);
		//initialise le contexte de l'application Spring, démarre le serveur web intégré et exécute les beans nécessaires
	}

	@Bean // la méthode retourne un objet géré par le conteneur Spring
	CommandLineRunner start (CompteRepository compteRepository) {
		return args -> {
			//Ajoute de nouveaux comptes à la base de données
			compteRepository.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.EPARGNE));
			compteRepository.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.COURANT));
			compteRepository.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.EPARGNE));

			compteRepository.findAll().forEach(c -> {
				//Récupère tous les comptes dans la base de données sous forme de liste
				// Parcourt chaque compte de la liste
				System.out.println(c.toString()); //affiche la representation textuelle de chanque compte ds la console
			});
		};
	}
}
