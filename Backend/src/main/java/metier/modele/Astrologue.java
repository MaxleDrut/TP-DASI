/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author Marie Guillevic
 */
@Entity
public class Astrologue extends Medium {
    
    private String formation;
    private Integer promotion;

    public Astrologue() {
    }
    

    public Astrologue(String formation, Integer promotion, Character genre, String denomination, String presentation) {
        super(genre, denomination, presentation);
        this.formation = formation;
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return super.toString() + "Astrologue{" + "formation=" + formation + ", promotion=" + promotion + '}';
    }
    
    

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }
    
    
    
}
