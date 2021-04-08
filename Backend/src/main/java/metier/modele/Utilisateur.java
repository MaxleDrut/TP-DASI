/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author romain
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur implements Serializable 
{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    @Column(unique=true)
    protected String mail;
    
    protected String motDePasse;
    
    protected Utilisateur() {}
    
    public Utilisateur(String mail, String motDePasse) 
    {
        this.mail = mail;
        this.motDePasse = motDePasse;
    }
    
    

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
