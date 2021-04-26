/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Consultation;
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
    
    public Employe employeAssignableAvecGenre(char genre)
    {
        TypedQuery<Employe> q3 = JpaUtil.obtenirContextePersistance().createQuery(
                "select emp "
                + "from Employe emp "
                + "where emp.disponibilite = true and emp.genre = :genre "
                , Employe.class
        ).setParameter("genre", genre);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date unMoisAvant = cal.getTime();
        
        Employe empChoisi = null;
        long minCons = -1;
        
        for(Employe emp : q3.getResultList())
        {
            long cpt = 0;
            for(Consultation cons : emp.getConsultations())
            {
                if(cons.getDateAssignation().after(unMoisAvant))
                {
                    cpt++;
                }
            }
            
            if(empChoisi == null || cpt < minCons)
            {
                empChoisi = emp;
                minCons = cpt;
            }
        }

        return empChoisi;
    }
}
