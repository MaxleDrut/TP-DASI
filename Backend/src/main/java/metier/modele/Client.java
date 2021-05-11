/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author onyr
 */
@Entity
public class Client extends Utilisateur implements Serializable 
{    
    private String nom;
    private String prenom;
    
    private Date dateNaissance;
    private String adresse;
    private String numTelephone;
    
    @Embedded
    private ProfilAstral profilAstral;
    
    @ManyToMany 
    @JoinTable(name="FAVORIS_CLIENT")
    private List<Medium> mediumsFavoris;
    
    protected Client() {}
    
    public Client(String nom, String prenom, String adresse, String numTelephone, Date dateNaissance, String mail, String motDePasse) {
        super(mail, motDePasse);
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.numTelephone = numTelephone;
        this.dateNaissance = dateNaissance;
        this.mediumsFavoris =new ArrayList<>();;

    }
    
    public int ajouterMediumAuxFavoris(Medium medium){
        for(Medium mediums : mediumsFavoris){
                if(mediums.getId()==medium.getId()){
                    return 0;
                }
        }
        mediumsFavoris.add(medium);
        return 1;
    }
    
    public int enleverMediumDesFavoris(Medium medium){
        if(mediumsFavoris!=null){
            for(Medium mediums : mediumsFavoris){
                if(mediums.getId()==medium.getId()){
                    mediumsFavoris.remove(medium);
                    return 1;
                }
            }
        }
        return 0;
    }

    public List<Medium> getMediumsFavoris() {
        return mediumsFavoris;
    }
   

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public ProfilAstral getProfilAstral() {
        return profilAstral;
    }

    public void setProfilAstral(ProfilAstral profilAstral) {
        this.profilAstral = profilAstral;
    }

    public String getNumTelephone() {
        return numTelephone;
    }

    public void setNumTelephone(String numTelephone) {
        this.numTelephone = numTelephone;
    }
    @Override
    public String toString() {
        return "Client{" + "nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance + ", adresse=" + adresse + ", profilAstral=" + profilAstral + '}';
    }
}
