/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.TypedQuery;
import metier.modele.Client;
import metier.modele.Utilisateur;

/**
 *
 * @author romai
 */
public class UtilisateurDao 
{
    public Utilisateur chercherUtilisateurParEmail(String mail) {
        String s = "select ut from Utilisateur ut where ut.mail = :mail";
        TypedQuery<Client> query = JpaUtil.obtenirContextePersistance().createQuery(s, Client.class);
        query.setParameter("mail", mail);
        return query.getSingleResult();
    }
}
