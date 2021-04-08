/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import metier.modele.Medium;

/**
 *
 * @author Marie Guillevic
 */
public class MediumDao {
    
    public void creer(Medium medium) {
        JpaUtil.obtenirContextePersistance().persist(medium);
    }
}
