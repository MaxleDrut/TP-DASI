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
public class Spirite extends Medium{
    
    private String support;

    public Spirite() {
    }

    public Spirite(String support, Character genre, String denomination, String presentation) {
        super(genre, denomination, presentation);
        this.support = support;
    }

    @Override
    public String toString() {
        return super.toString() + "Spirite{" + "support=" + support + '}';
    }
    
    

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    
    
    
}
