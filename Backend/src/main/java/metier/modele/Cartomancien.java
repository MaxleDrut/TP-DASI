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
public class Cartomancien extends Medium{

    public Cartomancien() {
    }

    public Cartomancien(Character genre, String denomination, String presentation) {
        super(genre, denomination, presentation);
    }
    
    
}
