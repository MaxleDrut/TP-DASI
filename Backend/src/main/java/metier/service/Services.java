/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ClientDao;
import dao.ConsultationDao;
import dao.EmployeDao;
import dao.JpaUtil;
import dao.MediumDao;

import dao.UtilisateurDao;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.AuthenticationException;
import javax.persistence.NoResultException;
import metier.modele.Client;
import metier.modele.Consultation;
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
             List<String> astroProfil = astroService.getProfil(client.getPrenom(), client.getDateNaissance());
            ProfilAstral profilAstral = new ProfilAstral(
                   // zodiaque, chinois, couleur, animal
                   astroProfil.get(0), astroProfil.get(1), astroProfil.get(2), astroProfil.get(3)
            );
            client.setProfilAstral(profilAstral);
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
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
            client = clientDao.chercherParId(id);
        } catch(Exception e) {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la récupération par id du client !\n Message : {0}", e.getLocalizedMessage());
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
            listeClients = clientDao.chercherTous();
        } catch(Exception e) {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la récupération de la liste des clients.\nMessage : {0}", e.getLocalizedMessage());
            listeClients = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
       return listeClients;
    }
    
    public List<Employe> obtenirListeEmployes()
    {
        EmployeDao employeDao = new EmployeDao();
        List<Employe> listeEmployes;
        
        try {
            JpaUtil.creerContextePersistance();
            listeEmployes = employeDao.chercherTous();
        } catch(Exception e) {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la récupération de la liste des employés.\nMessage : {0}", e.getLocalizedMessage());
            listeEmployes = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return listeEmployes;
    }
	
    public Consultation demanderConsultation(Client client, Medium medium)
    {
        ConsultationDao consultationDao = new ConsultationDao();
        EmployeDao employeDao = new EmployeDao();
        Consultation consultation = null;
        
        try
        {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            // sélectionner employé
            Employe employe = employeDao.employeAssignableAvecGenre(medium.getGenre());
            if(employe == null)
            {
                throw new Exception("Aucun employé disponible");
            }
            
            employe.setDisponibilite(Boolean.FALSE);
            employeDao.modifier(employe);
            
            // créer consultation
            consultation = new Consultation(employe, medium, client, new Date());
            consultationDao.creer(consultation);
            
            // contacter employé
            Message.envoyerMail("noreply@predictif.fr", employe.getMail(), "Assignation de client", "Bonjour, nous vous avons assigné un nouveau client. Rendez-vous sur votre espace personnel au plus vite pour le prendre en charge !");
            
            JpaUtil.validerTransaction();
        }
        catch(Exception e)
        {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la création d'une consultation !\n Message : " + e.getLocalizedMessage());
            JpaUtil.annulerTransaction();
            consultation = null;
        }
        finally
        {
            JpaUtil.fermerContextePersistance();
        }
        
        return consultation;
    }
    
    public Utilisateur authentification(String mail, String motDePasse) {
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        Utilisateur utilisateur = null;
        
        try {
            JpaUtil.creerContextePersistance();
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
            
        } catch(AuthenticationException e) {
            Logger.getLogger("ServicesClient").log(Level.INFO, "Erreur à l''authentification : {0}", e.getExplanation());
            utilisateur = null;
        } catch(Exception e) {
            Logger.getLogger("ServicesClient").log(Level.SEVERE, "Erreur non gérée lors de l''authentification : {0}", e.getLocalizedMessage());
            utilisateur = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return utilisateur;
    }
  
    public Medium obtenirMedium(Long mediumId) {
        MediumDao mediumDao = new MediumDao();
        Medium medium;        
        try {
            JpaUtil.creerContextePersistance();
            medium = mediumDao.chercherParId(mediumId);
        } catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur au niveau du Dao", e);
            medium = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return medium;
    }
    
    public List<Medium> obtenirListeMediums() {
        MediumDao mediumDao = new MediumDao();
        List<Medium> listeMediums;
        
        try {
            JpaUtil.creerContextePersistance();
            listeMediums = mediumDao.fournirListMediums();
        } catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur au niveau du Dao", e);
            listeMediums = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
       return listeMediums;
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
