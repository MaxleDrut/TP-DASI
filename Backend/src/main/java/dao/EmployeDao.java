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
    
    public List<Employe> employesLibresParOrdreDeParticipationAvecGenre(char genre)
    {
        TypedQuery<Employe> query = JpaUtil.obtenirContextePersistance().createQuery(
            "select c.employe " +
            "from Consultation c " +
            "where c.employe.disponibilite = true "
                    + "and c.employe.genre = :genre "
                    + "and c.dateAssignation < current_date - 30 " +
            "group by c.employe " +
            "order by count(c.employe)", Employe.class
        ).setParameter("genre", genre);
        
        return query.getResultList();
    }
}
