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
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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


    public Employe rechercherEmploye(Long employeId) {
        EmployeDao eDao = new EmployeDao();
        Employe emp;

        try {
            JpaUtil.creerContextePersistance();
            emp = eDao.chercherParId(employeId);
        } catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur au niveau du Dao", e);
            emp = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return emp;
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

    public Consultation ajouterConsultationManuellement(Consultation cons) {
        ConsultationDao cDao = new ConsultationDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            cDao.creer(cons);
            JpaUtil.validerTransaction();
            Logger.getLogger("Services").log(Level.INFO, "Ajout de la consultation réussi !");
        }
        catch(Exception e)
        {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de l'ajout manuel de la consultation !! \nMessage : {0}", e.getLocalizedMessage());
            JpaUtil.annulerTransaction();
            cons=null;
        }
        finally
        {
            JpaUtil.fermerContextePersistance();
        }
        return cons;
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

    /*Permet de démarrer la consultation :
    - Récupère la consultation à partir de l'ID
    - Set la date de début à maintenant
    - Envoie un SMS au client de la consultation selon le modèle
    - Renvoie le numéro de téléphone du client
    */

    public String demarrerConsultation(Long consultationId) {
        ConsultationDao cDao = new ConsultationDao();
        Consultation cons;

        try {
            JpaUtil.creerContextePersistance();
            cons = cDao.chercherParId(consultationId);

            if(cons == null) {
                throw new Exception("Consultation introuvable !");
            }

            Date dateAss = cons.getDateAssignation();
            if(dateAss == null) {
                throw new Exception("La consultation n'a pas de date d'assignation !");
            }

            Client cli = cons.getClient();
            Employe emp = cons.getEmploye();
            Medium med = cons.getMedium();
            JpaUtil.ouvrirTransaction();
            //Par défaut, les dates ont pour valeur d'initialisation le temps actuel.
            cons.setDateDebut(new Date());
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO,"Consultation démarrée avec succès");

            SimpleDateFormat jour = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat heure = new SimpleDateFormat("hh:mm");

            String message = "Bonjour "
                             + cli.getPrenom()
                             + ". J'ai bien reçu votre demande de consultation du "
                             + jour.format(dateAss)
                             + " à "
                             + heure.format(dateAss)
                             + ". Vous pouvez dès à présent me contacter au "
                             + emp.getNumTelephone()
                             + ". A tout de suite ! Médiumiquement vôtre, "
                             + med.getDenomination();

            Message.envoyerNotification(cli.getNumTelephone(), message);
            return cli.getNumTelephone();

        } catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur de demarrerConsultation() :", e);
            return null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    /*Permet de terminer la consultation :
    - Récupère la consultation à partir de l'ID
    - Set la date de fin à maintenant et ajoute un commentaire
    - Renvoie la consultation terminée.
    */

    public Consultation terminerConsultation(Long consultationId, String commentaire) {
        ConsultationDao cDao = new ConsultationDao();
        Consultation cons = null;

        try {
            JpaUtil.creerContextePersistance();
            cons = cDao.chercherParId(consultationId);

            if(cons == null) {
                throw new Exception("Consultation introuvable !");
            }

            Date dateDeb = cons.getDateDebut();
            if(dateDeb == null) {
                throw new Exception("La consultation n'a pas commencé !");
            }

            JpaUtil.ouvrirTransaction();
            cons.setDateFin(new Date());
            cons.setCommentaire(commentaire);
            JpaUtil.validerTransaction();

            Logger.getAnonymousLogger().log(Level.INFO,"Consultation terminée avec succès");

        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur de demarrerConsultation() :", e);
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

    public Medium ajouterMediumAuxFavoris(Medium medium, Client client){
        ClientDao clientDao = new ClientDao();
        MediumDao mediumDao = new MediumDao();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            if(medium==null){
                throw new Exception ("Le medium n'existe pas");
            }
            int success = client.ajouterMediumAuxFavoris(medium);
            if(success==1){
                clientDao.modifier(client);
                JpaUtil.validerTransaction();
                Logger.getLogger("Services").log(Level.INFO, "Ajout du medium aux favoris réussi !");
            }else {
                Logger.getLogger("Services").log(Level.SEVERE, "Le medium fait déja parti de la liste des favoris !");
                medium = null;
            }
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

    public Medium enleverMediumDesFavoris(Medium medium, Client client){
        ClientDao clientDao = new ClientDao();
        MediumDao mediumDao = new MediumDao();

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            if(medium==null){
                throw new Exception ("Le medium n'existe pas");
            }
            int success = client.enleverMediumDesFavoris(medium);
            if(success==1){
                clientDao.modifier(client);
                JpaUtil.validerTransaction();
                Logger.getLogger("Services").log(Level.INFO, "Suppression du medium des favoris réussi !");
            }else{
                Logger.getLogger("Services").log(Level.SEVERE, "Le medium ne fait pas parti de la liste des favoris !");
                medium = null;
            }
        }
        catch(Exception e)
        {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la suppression du medium des favoris !! \nMessage : {0}", e.getLocalizedMessage());
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


    public Map<Medium, Long> recupererNbConsultationsMediums() 
    {
        ConsultationDao cDao = new ConsultationDao();
        MediumDao mDao = new MediumDao();
        
        // data
        List<Medium> mediums;
        Map<Medium, Long> mediumsNbConsultations;
        
        // result
        Map<Medium, Long> allMediumsNbConsultations = new HashMap<Medium, Long>();
        
        try {
            JpaUtil.creerContextePersistance();
            
            // get all mediums and mediums used with their number of consultations
            mediums = mDao.fournirListMediums();
            mediumsNbConsultations = cDao.recupererNbConsultationsMediumsUtilises();
            
            
            int nbMediums = mediums.size();
            if(nbMediums > 0) {
                Medium currentMedium;
                Long nbConsultations;
                
                // for each medium, check if it has a number of consultations
                for(int i = 0; i < nbMediums; i++)
                {
                    currentMedium = mediums.get(i);
                    // check that this medium is inside mediumsNbConsultations
                    nbConsultations = mediumsNbConsultations.get(currentMedium);
                    if(nbConsultations == null)
                    {
                        // if nothing found, this medium has 0 consultations
                        nbConsultations = 0L;
                    }
                    
                    // add a new row inside the result HashMap
                    allMediumsNbConsultations.put(currentMedium, nbConsultations);
                }  
            } else {
                throw new Exception("no mediums in database");
            }
            
        } catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur au niveau de recupererNbConsultationsMediums()", e);
            
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return allMediumsNbConsultations;
    }
    
    /** A function that gets (a maximum of the) 5 top mediums and their number of consultations.
     * @return A map of the 5 first mediums and their number of consultations. WARN : 
     * Only returns mediums with non-zero consultations, hence can return 5 Entry
     * or less.
     */
    public Map<Medium, Long> recupererTop5Mediums()
    {   
        Long[] topNbConsultations = {0L, 0L, 0L, 0L, 0L};
        Map<Medium, Long> mediumsNbConsultations;
        
        // result
        Map<Medium, Long> mediumsNbConsultationsTop5 = new HashMap<Medium, Long>();
        
        try {
            
            // get all mediums and mediums used with their number of consultations
            mediumsNbConsultations = recupererNbConsultationsMediums();
            
            int size = mediumsNbConsultations.size();
            if(size > 0) {
                // sort the map as a List in descending order
                List<Entry<Medium, Long>> list = new ArrayList<>(mediumsNbConsultations.entrySet());
                list.sort(Entry.comparingByValue(Comparator.reverseOrder()));
                
                // get back at maximum the first 5 elements into a Map if not 0
                for(int i = 0; i < 5 && i < size; i++) {
                    if(list.get(i).getValue() != 0L) {
                        mediumsNbConsultationsTop5.put(
                            list.get(i).getKey(), list.get(i).getValue()
                        );
                    }   
                }
                
                
            } else {
                throw new Exception("Error : [recupererTop5Mediums()] absence of data in database");
            }
            
        } catch(Exception e) {
            Logger.getAnonymousLogger()
                .log(Level.SEVERE, "Erreur au niveau de recupererTop5Mediums()", e);
            
        }
        
        return mediumsNbConsultationsTop5;
        
    }

    public List<Consultation> recupererConsultationsClient(Client client)
    {
        List<Consultation> result = null;
        ConsultationDao consultationDao = new ConsultationDao();
        
        try
        {
            JpaUtil.creerContextePersistance();
            result = consultationDao.chercherParClient(client);
        }
        catch(Exception e)
        {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la récupération des consultations !! \nMessage : {0}", e.getLocalizedMessage());
            result = null;
        }
        finally
        {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    public List<Consultation> recupererConsultationsEmploye(Employe employe)
    {
        List<Consultation> result = null;
        ConsultationDao consultationDao = new ConsultationDao();
        
        try
        {
            JpaUtil.creerContextePersistance();
            result = consultationDao.chercherParEmploye(employe);
        }
        catch(Exception e)
        {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la récupération des consultations !! \nMessage : {0}", e.getLocalizedMessage());
            result = null;
        }
        finally
        {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
}
