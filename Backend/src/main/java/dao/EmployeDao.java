/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Employe;

/**
 *
 * @author Marie Guillevic
 */
public class EmployeDao {
    
    public void creer(Employe employe) {
        JpaUtil.obtenirContextePersistance().persist(employe);
    }
    
    public void supprimer(Employe employe) {
        JpaUtil.obtenirContextePersistance().remove(employe);
    }
    
    public Employe modifier(Employe employe) {
        return JpaUtil.obtenirContextePersistance().merge(employe);
    }
    
    public Employe chercherParId(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Employe.class, id);
    }
    
    public List<Employe> chercherTous() {
        String s = "select e from Employe e order by e.nom desc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        return query.getResultList();
    }
}
