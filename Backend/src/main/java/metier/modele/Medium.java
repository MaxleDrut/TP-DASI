/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author Marie Guillevic
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Medium {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected Character genre;
    protected String denomination;
    protected String presentation;

    public Medium() {
    }

    protected Medium(Character genre, String denomination, String presentation) {
        this.genre = genre;
        this.denomination = denomination;
        this.presentation = presentation;
    }

    @Override
    public String toString() {
        return "Medium{" + "id=" + id + ", genre=" + genre + ", denomination=" + denomination + ", presentation=" + presentation + '}';
    }    
    

    public Character getGenre() {
        return genre;
    }

    public void setGenre(Character genre) {
        this.genre = genre;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public Long getId() {
        return id;
    }
}
