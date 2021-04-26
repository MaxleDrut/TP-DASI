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
import java.util.List;
import java.util.Map;
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
            Message.envoyerMail("noreply@predictif.fr", client.getMail(), "Echec d'inscription", "Une erreur d'inscription est survenue. Veuillez rééssayer"); 
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
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur au niveau du Dao", e);
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
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur au niveau du Dao", e);
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
    
    public List<Medium> obtenirListMedium() {
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
  
    public Consultation obtenirConsultation(Long consultationId) {
        ConsultationDao dao = new ConsultationDao();
        Consultation cons;        
        try {
            JpaUtil.creerContextePersistance();
            cons = dao.chercherParId(consultationId);
        } catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur au niveau du Dao", e);
            cons = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return cons;
    }
    
    public Consultation obtenirConsultationAssignee(Long employeId) {
        ConsultationDao cDao = new ConsultationDao();
        EmployeDao eDao = new EmployeDao();
           
        Consultation cons = null;
        try {
            JpaUtil.creerContextePersistance();
            Employe emp = eDao.chercherParId(employeId);
            List<Consultation> lCons = cDao.chercherParEmploye(emp);
            
            for(Consultation c : lCons) {
                if(c.getDateFin() == null) { //Consultation en cours : c'est ce qu'on cherche
                    cons = c;
                    break; //Il n'y a en principe qu'une cons. en cours par employé, on peut donc sortir du for each
                    //(je sais que c'est censé être une structure while, mais puisqu'il n'y a pas de "while each" en java...)
                }
            }
            
        } catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur au niveau du Dao", e);
            
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return cons;
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
    
    public Medium ajouterMediumAuxFavoris(Long mediumId, Long clientId){
        ClientDao clientDao = new ClientDao();
        MediumDao mediumDao = new MediumDao();
        Medium medium;
        Client client;
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            medium = mediumDao.chercherParId(mediumId);
            client = clientDao.chercherParId(clientId);
            clientDao.ajouterFavoris(medium, client);
            JpaUtil.validerTransaction();
            Logger.getLogger("Services").log(Level.INFO, "Ajout du medium aux favoris réussi !");
        }
        catch(Exception e)
        {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de l'ajout du medium au favoris !! \nMessage : {0}", e.getLocalizedMessage());
            JpaUtil.annulerTransaction();
            medium = null;
        } 
        finally 
        {
            JpaUtil.fermerContextePersistance();
        }
        return medium;
    }
    
    public Map<Employe,Long> recupererNombreConsultationsEmploye(){
        ConsultationDao consultationDao = new ConsultationDao();
        Map<Employe,Long> consultationsEmployes;
        
        try {
            JpaUtil.creerContextePersistance();
            consultationsEmployes = consultationDao.recupererNbConsultationsEmploye();
             
        }
        catch(Exception e)
        {
           Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de l'accès aux consultations de l'Employe !! \nMessage : {0}", e.getLocalizedMessage());
           consultationsEmployes=null;
        } 
        finally 
        {
            JpaUtil.fermerContextePersistance();
        }
        return consultationsEmployes;
    }
}
