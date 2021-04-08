/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ClientDao;
import dao.EmployeDao;
import dao.JpaUtil;
import dao.MediumDao;

import dao.UtilisateurDao;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.AuthenticationException;
import javax.persistence.NoResultException;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.modele.ProfilAstral;
import metier.modele.Utilisateur;
import metier.service.util.AstroTest;
import metier.service.util.Message;

/**
 *
 * @author onyr
 */
public class Services {
    
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
            Message.envoyerMail("noreply@predictif.fr", client.getMail(), "Confirmation d'inscription", "Bonjour, vous êtes bien inscrit !"); 
        }
        catch(Exception e)
        {
            Logger.getLogger("ServicesClient").log(Level.SEVERE, "Erreur lors de l''inscription d'un Client !! \nMessage : {0}", e.getLocalizedMessage());
            JpaUtil.annulerTransaction();
            client = null;
        } 
        finally 
        {
            JpaUtil.fermerContextePersistance();
        }
        
        return client;
    }
    
    public Employe recrutement(Employe employe) {
        EmployeDao employeDao = new EmployeDao();        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            employeDao.creer(employe);
            JpaUtil.validerTransaction();
            Logger.getLogger("Services").log(Level.INFO, "Recrutement de l'employé réussie !");
            
        }
        catch(Exception e)
        {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors du recrutement de l'Employe !! \nMessage : {0}", e.getLocalizedMessage());
            JpaUtil.annulerTransaction();
            employe=null;
        } 
        finally 
        {
            JpaUtil.fermerContextePersistance();
        }
        return employe;
    }
    
    public Medium inventerMedium(Medium medium){
         MediumDao mediumDao = new MediumDao();        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            mediumDao.creer(medium);
            JpaUtil.validerTransaction();
            Logger.getLogger("Services").log(Level.INFO, "Création du medium réussie !");
            
        }
        catch(Exception e)
        {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la création du medium !! \nMessage : {0}", e.getLocalizedMessage());
            JpaUtil.annulerTransaction();
            medium=null;
        } 
        finally 
        {
            JpaUtil.fermerContextePersistance();
        }
        return medium;
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
    
    public Utilisateur authentification(String mail, String motDePasse) {
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        Utilisateur utilisateur = null;
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            try {
                utilisateur = utilisateurDao.chercherUtilisateurParEmail(mail);
            }
            catch(NoResultException e)
            {
                throw new AuthenticationException("Impossible de trouver l'adresse mail !");
            }
            
            if(!utilisateur.getMotDePasse().equals(motDePasse))
            {
                throw new AuthenticationException("Mot de passe incorrect !");
            }
            
            JpaUtil.validerTransaction();
        } catch(AuthenticationException e) {
            Logger.getLogger("ServicesClient").log(Level.INFO, "Erreur à l''authentification : {0}", e.getExplanation());
            JpaUtil.annulerTransaction();
            utilisateur = null;
        } catch(Exception e) {
            Logger.getLogger("ServicesClient").log(Level.SEVERE, "Erreur non gérée lors de l''authentification : {0}", e.getLocalizedMessage());
            JpaUtil.annulerTransaction();
            utilisateur = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return utilisateur;
    }
    
    public List<String> demanderAideConsultation(Client cl, int amour, int sante, int travail) {
        ProfilAstral pa = cl.getProfilAstral();
        AstroTest astro = new AstroTest();
        List<String> output = null;
        try {
            output = astro.getPredictions(pa.getCouleur(), pa.getAnimalTotem(),amour, sante,travail);
        } catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur de calcul de profil astral", e);
        } finally {
            return output;
        }
    }
    
}
