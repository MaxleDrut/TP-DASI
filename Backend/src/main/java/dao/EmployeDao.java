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
    
    public Employe modifier(Employe employe) {
        return JpaUtil.obtenirContextePersistance().merge(employe);
    }
    
    public List<Employe> chercherTous()
    {
        String s = "select e from Employe e order by e.nom desc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        return query.getResultList();
    }
    
    public Employe employeAssignableAvecGenre(char genre)
    {
        /*
            Récupère le nombre de consultations par employé (pour toujours)
        SELECT emp.id, count(cons.employe_id) FROM LOGIN.EMPLOYE emp left JOIN LOGIN.CONSULTATION cons ON emp.id = cons.EMPLOYE_ID group by cons.employe_id, emp.id  FETCH FIRST 100 ROWS ONLY;

        
        select emp
        from Employe emp left join Consultation cons on emp.id = cons.employe_id
        where emp.disponibilite = true and emp.genre = :genre
             and cons.dateAssignation > :date_depart
        group by emp
        order by count(emp)
        
        
        */
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date unMoisAvant = cal.getTime();
        /*
        TypedQuery<Employe> query = JpaUtil.obtenirContextePersistance().createQuery(
                "select distinct emp "
                + "from Employe emp left join emp.consultations cons  " // on emp.id = cons.employe_id => automatiquement géré par l'ORM
                + "where emp.disponibilite = true and emp.genre = :genre "
                        + "and cons.dateAssignation > :date_depart "
                + "group by emp " // , cons.id
                // + "order by count(cons.id) asc "
                , Employe.class
        ).setParameter("genre", genre)
         .setParameter("date_depart", unMoisAvant, TemporalType.DATE);
        */
        TypedQuery<Employe> q3 = JpaUtil.obtenirContextePersistance().createQuery(
                "select emp "
                + "from Employe emp "
                + "where emp.disponibilite = true and emp.genre = :genre "
                , Employe.class
        ).setParameter("genre", genre);
        
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
        
        /*
        
        
        Query q2 = JpaUtil.obtenirContextePersistance().createQuery(
                "select emp "
                + "from Employe emp left join Consultation cons " // on emp.id = cons.employe_id => automatiquement géré par l'ORM
                    // + "where emp.disponibilite = true and emp.genre = :genre "
                    // + "and cons.dateAssignation > :date_depart "
                //+ "order by count(emp.consultations) asc "
        ); //.setParameter("genre", genre);
        System.out.println("=============================================================================================================================================");
        for(Object o : q2.getResultList())
        {
            /*Object[] tab = (Object[]) o;
            Employe e = (Employe)tab[0];
            Long cpt = (Long)tab[1]; 
            System.out.println(e + " " + cpt);
            System.out.println((Employe)o);
        }
         //.setParameter("date_depart", unMoisAvant, TemporalType.DATE);
        /*
        Query q1 = JpaUtil.obtenirContextePersistance().createQuery(
                "select emp "
                + "from Employe emp left join emp.consultations cons " // on emp.id = cons.employe_id => automatiquement géré par l'ORM
                + "where emp.disponibilite = true and emp.genre = :genre "
                        + "and cons.dateAssignation > :date_depart "
                + "group by emp, cons.id "
                + "order by count(cons.id) asc "
        ).setParameter("genre", genre)
         .setParameter("date_depart", unMoisAvant, TemporalType.DATE);
        System.out.println("Consultations :");
        for(Object o : q1.getResultList())
        {
            /*Object[] tab = (Object[]) o;
            Employe e = (Employe)tab[0];
            Consultation cons = (Consultation)tab[1]; 
            
            System.out.println(e.getId() + " " + e.getNom() + " => " + cons.getId());
            System.out.println((Employe)o);
        }
                */
        return empChoisi;
    }
}
