/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;

/**
 *
 * @author Romain
 */
public class ConsultationDao 
{
    public void creer(Consultation consultation)
    {
        JpaUtil.obtenirContextePersistance().persist(consultation);
    }
    
    public void modifier(Consultation consultation)
    {
        JpaUtil.obtenirContextePersistance().merge(consultation);
    }
    
    public Consultation chercherParId(Long id)
    {
        return JpaUtil.obtenirContextePersistance().find(Consultation.class, id);
    }
    
    public List<Consultation> chercherParMedium(Medium medium)
    {
        TypedQuery<Consultation> query = JpaUtil.obtenirContextePersistance().createQuery(
               "select c from Consultation c where c.medium = :medium", Consultation.class
        ).setParameter("medium", medium);
        
        return query.getResultList();
    }
    
    public List<Consultation> chercherParClient(Client client)
    {
        TypedQuery<Consultation> query = JpaUtil.obtenirContextePersistance().createQuery(
               "select c from Consultation c where c.client = :client", Consultation.class
        ).setParameter("client", client);
        
        return query.getResultList();
    }
    
    public List<Consultation> chercherParEmploye(Employe employe)
    {
        TypedQuery<Consultation> query = JpaUtil.obtenirContextePersistance().createQuery(
               "select c from Consultation c where c.employe = :employe", Consultation.class
        ).setParameter("employe", employe);
        
        return query.getResultList();
    }
    
    public Map<Employe,Long> recupererNbConsultationsEmploye(){
        
        String s = "select c.employe as employe, count(c.id) as nbConsultations from Consultation c group by c.employe";
        Query query = JpaUtil.obtenirContextePersistance().createQuery(s);
       
        List<Object[]> results = query.getResultList();
        Map<Employe,Long> consultationsEmployes = new HashMap<Employe,Long>();
        for(Object[] obj : results){
            consultationsEmployes.put((Employe) obj[0],(Long) obj[1]);
        }    
        
        String s2 = "select e from Employe e where e NOT IN (select c.employe from Consultation c)";
        TypedQuery query2 = JpaUtil.obtenirContextePersistance().createQuery(s2, Employe.class);
        List<Employe> resultsEmp = query2.getResultList();
        for(Employe e : resultsEmp){
            consultationsEmployes.put(e, 0L);
        } 
         
        return consultationsEmployes;
        
    }

    /** This function returns mediums used in consultations and the number of consultations
     * they are involved in. WARN : This only returns mediums that are used in 
     * consultations, not all the possible mediums to use.
     * A similar function is defined in Service
     * @return 
     */
    public Map<Medium, Long> recupererNbConsultationsMediumsUtilises() 
    {
        List<Object[]> queryResults;
        Map<Medium, Long> mediumsNbConsultations = new HashMap<Medium, Long>();
        
        // do query; get back a List<Object[Medium, Long]>
        String s = "select c.medium as medium, count(c.id) as nbConsults from Consultation c group by c.medium";
        Query query = JpaUtil.obtenirContextePersistance()
            .createQuery(s);
        queryResults = query.getResultList();
        
        // convert List<Object[Medium, Long]> to Map<Medium, Long>
        for(Object[] o : queryResults)
        {
            mediumsNbConsultations.put(
                (Medium) o[0], (Long) o[1]
            );
        }
        
        return mediumsNbConsultations;   
    }
    
    public Consultation recupererConsultationEncoursClient(Client client)
    {
        TypedQuery<Consultation> query = JpaUtil.obtenirContextePersistance().createQuery(
                "select c "
                + "from Consultation c "
                + "where c.dateFin is null and c.client = :client ",
                Consultation.class
        ).setParameter("client", client);
        
        return query.getSingleResult();
    }
}
