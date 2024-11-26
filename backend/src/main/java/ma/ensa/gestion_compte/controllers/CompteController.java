package ma.ensa.gestion_compte.controllers;

import ma.ensa.gestion_compte.entities.Compte;
import ma.ensa.gestion_compte.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banque")
public class CompteController { // gerer les requetes HTTP

    @Autowired
    private CompteRepository compteRepository;

    //READ: recupere tous les comptes (JSON et XML)
    @GetMapping(value="/comptes", produces = {"application/json",
            "application/xml"})
    public List<Compte> getComptes(){
        return compteRepository.findAll();
    }

    //READ: recuperer un compte par son id
    @GetMapping(value="/comptes/{id}", produces = {"application/json",
            "application/xml"})
    public ResponseEntity<Compte> getCompte(@PathVariable int id){
        return compteRepository.findById(id)
                .map(compte -> ResponseEntity.ok().body(compte))
                .orElse(ResponseEntity.notFound().build());
    }

    //CREATE: ajouter un nouveau compte
    @PostMapping(value="/comptes", produces = {"application/json",
            "application/xml"})
    public Compte createCompte(@RequestBody Compte compte){
        return compteRepository.save(compte);
    }

    //UPDATE: mettre a jour un compte existant
    @PutMapping(value="/comptes/{id}", produces = {"application/json",
            "application/xml"})
    public ResponseEntity<Compte> updateCompte(@PathVariable Long id,
                                               @RequestBody Compte compteDetails){
        return compteRepository.findById(Math.toIntExact(id))
                .map(compte -> {
                    compte.setSolde(compteDetails.getSolde());
                    compte.setDateCreation(compteDetails.getDateCreation());
                    compte.setType(compteDetails.getType());
                    Compte updatedCompte = compteRepository.save(compte);
                    return ResponseEntity.ok().body(updatedCompte);
                }).orElse(ResponseEntity.notFound().build());
    }

    //DELETE: supprimer compte
    @DeleteMapping("/comptes/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id){
        return compteRepository.findById(Math.toIntExact(id))
                .map(compte -> {
                    compteRepository.delete(compte);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
