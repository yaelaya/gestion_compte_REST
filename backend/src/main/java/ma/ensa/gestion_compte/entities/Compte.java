package ma.ensa.gestion_compte.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity  // enregistrement des instances fbdd
@Data    // genere getters o setters
@NoArgsConstructor  // genere constructeur sans arguments
@AllArgsConstructor // genere constructor avec arguments
@JacksonXmlRootElement(localName="compte")

public class Compte {
    @Id  //cle primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) //spécifier la stratégie de génération de la clé primaire id == la valeur de l'identifiant sera générée automatiquement par la base de données
    private Long id;
    private double solde;

    @Temporal(TemporalType.DATE) //il faut ignorer l'heure et ne conserver que la date 7it la classe Date fiha o la date o l'heure bjoj
    private Date dateCreation;

    @Enumerated(EnumType.STRING) // l'énumération doit être stockée sous forme de chaîne de caractères
    private TypeCompte type;

}
