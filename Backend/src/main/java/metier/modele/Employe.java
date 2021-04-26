/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


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
    private Boolean disponibilite;
    
    @OneToMany(mappedBy = "employe")
    private List<Consultation> consultations;

    public Employe() {
    }

    public Employe(String nom, String prenom, String numTelephone, String mail, Character genre, Boolean disponibilite,String mdp) {
        super(mail,mdp);
        this.nom = nom;
        this.prenom = prenom;
        this.numTelephone = numTelephone;
        this.genre = genre;
        this.disponibilite = disponibilite;
        this.consultations = new ArrayList<>();
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

    public Boolean getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(Boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    @Override
    public String toString() {
        return "Employe{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", numTelephone=" + numTelephone + ", genre=" + genre + ", disponibilite=" + disponibilite + '}';
    }
}
