/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Client;
import metier.modele.Medium;

/**
 *
 * @author onyr
 */
public class ClientDao {
    
    public void creer(Client client) {
        JpaUtil.obtenirContextePersistance().persist(client);
    }
    
    public void supprimer(Client client) {
        JpaUtil.obtenirContextePersistance().remove(client);
    }
    
    public Client modifier(Client client) {
        return JpaUtil.obtenirContextePersistance().merge(client);
    }
    
    public Client chercherParId(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }
    
    
    
    public List<Client> chercherTous() {
        String s = "select e from Client e order by e.nom desc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Client.class);
        return query.getResultList();
    }
    
    public Client chercherClientPourAuthentification(String mail, String motDePasse) {
        String s = "select e from Client e where e.mail = :mail and e.motDePasse = :motDePasse";
        TypedQuery<Client> query = JpaUtil.obtenirContextePersistance().createQuery(s, Client.class);
        query.setParameter("mail", mail);
        query.setParameter("motDePasse", motDePasse);
        return query.getSingleResult();
    }
    
    
}
