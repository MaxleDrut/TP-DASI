/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author onyr
 */
@Entity
public class Consultation {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Date dateAssignation;
    private Date dateDebut;
    private Date dateFin;
    private String commentaire;
    
    @OneToOne
    private Employe employe;
    @OneToOne
    private Medium medium;
    @OneToOne
    private Client client;
    
    protected Consultation() {}
    
    public Consultation(Date dateAssignation) {
        this.dateAssignation = dateAssignation;
    }

    public Date getDateAssignation() {
        return dateAssignation;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Long getId() {
        return id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public Medium getMedium() {
        return medium;
    }

    public Client getClient() {
        return client;
    }

    public void setDateAssignation(Date dateAssignation) {
        this.dateAssignation = dateAssignation;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    

    @Override
    public String toString() {
        return "Consultation{" + "dateAssignation=" + dateAssignation + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", commentaire=" + commentaire + '}';
    }
    
    
    
}
