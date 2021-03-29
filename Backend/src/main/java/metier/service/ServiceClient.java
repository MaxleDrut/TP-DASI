/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ClientDao;
import dao.JpaUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;

/**
 *
 * @author onyr
 */
public class ServiceClient {
    
    public Client InscrireClient(Client client) {
        ClientDao clientDao = new ClientDao();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            clientDao.creer(client);
            JpaUtil.validerTransaction();
        } catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur au niveau du Dao", e);
            JpaUtil.annulerTransaction();
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return client;
        
    }
    
    public Client RechercherClient(Long id) {
        ClientDao clientDao = new ClientDao();
        Client client;
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client = clientDao.chercherParId(id);
            JpaUtil.validerTransaction();
        } catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur au niveau du Dao", e);
            JpaUtil.annulerTransaction();
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        return client;
    }
    
    public List<Client> ObtenirListeClients() {
        ClientDao clientDao = new ClientDao();
        List<Client> listeClients;
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            listeClients = clientDao.chercherTous();
            JpaUtil.validerTransaction();
        } catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur au niveau du Dao", e);
            JpaUtil.annulerTransaction();
            listeClients = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
       return listeClients;
    }
    
    public Client AuthentifierClient(String mail, String motDePasse) {
        ClientDao clientDao = new ClientDao();
        Client client = null;
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client = clientDao.chercherClientPourAuthentification(mail, motDePasse);
            JpaUtil.validerTransaction();
        } catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur au niveau du Dao", e);
            JpaUtil.annulerTransaction();
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        return client;
        
    }
    
}
