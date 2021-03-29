/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ClientDao;
import dao.JpaUtil;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
import metier.modele.ProfilAstral;
import metier.service.util.AstroTest;

/**
 *
 * @author onyr
 */
public class ServiceClient {
    
    public Client inscrireClient(Client client) {
        ClientDao clientDao = new ClientDao();
        AstroTest astroService = new AstroTest();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            List<String> astroProfil = astroService.getProfil(client.getPrenom(), client.getDateNaissance());
            ProfilAstral profilAstral = new ProfilAstral(
                // zodiaque, chinois, couleur, animal
                astroProfil.get(0), astroProfil.get(1), astroProfil.get(2), astroProfil.get(3)
            );
            client.setProfilAstral(profilAstral);
            clientDao.creer(client);
            
            JpaUtil.validerTransaction();
            Logger.getLogger("ServicesClient").log(Level.INFO, "Inscription client réussie !");
        }
        catch(Exception e)
        {
            Logger.getLogger("ServicesClient").log(Level.SEVERE, "Erreur lors de l'inscription d'un Client !! \nMessage : {0}", e.getLocalizedMessage());
            JpaUtil.annulerTransaction();
            client = null;
        } 
        finally 
        {
            JpaUtil.fermerContextePersistance();
        }
        
        return client;
    }
    
    public Client rechercherClient(Long id) {
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
    
    public List<Client> obtenirListeClients() {
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
    
    public Client authentifierClient(String mail, String motDePasse) {
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
