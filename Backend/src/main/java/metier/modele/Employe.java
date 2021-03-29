/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;


/**
 *
 * @author Marie Guillevic
 */
@Entity
public class Employe extends Utilisateur implements Serializable {

    
    private String nom;
    private String prenom;
    private String numTelephone;
    private Character genre;
    private String disponibilite;

    public Employe() {
    }

    public Employe(String nom, String prenom, String numTelephone, String mail, Character genre, String disponibilite,String mdp) {
        super(mail,mdp);
        this.nom = nom;
        this.prenom = prenom;
        this.numTelephone = numTelephone;
        this.genre = genre;
        this.disponibilite = disponibilite;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumTelephone() {
        return numTelephone;
    }

    public void setNumTelephone(String numTelephone) {
        this.numTelephone = numTelephone;
    }

    public Character getGenre() {
        return genre;
    }

    public void setGenre(Character genre) {
        this.genre = genre;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }
    
}
