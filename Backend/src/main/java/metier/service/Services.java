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

    /**
     * Service permettant l'inscription d'un client sur la plateforme
     * @param client : un objet client à inscrire, pas encore persisté
     * @return l'instance du client nouvellement créé, null en cas d'exception
     */
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

    /**
     * Service permettant l'enregistrement d'un employé sur la plateforme
     * @param employe : un objet employé à inscrire, pas encore persisté
     * @return l'instance de l'employé passé en paramètre, ou null en cas d'exception
     */
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

    /**
     * Service permettant l'enregistrement d'un medium sur la plateforme
     * @param medium : un objet medium à inscrire, pas encore persisté
     * @return l'instancee du medium nouvellement persisté, ou null en cas d'exception
     */
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

    /**
     * Service permettant la récupération d'un client par son id
     * @param id : l'identifiant du client recherché
     * @return une instance du client si trouvé, null sinon
     */
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

    /**
     * Service permettant la récupération de la liste des clients
     * @return une liste de clients, ou null en cas d'exception
     */
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

    /**
     * Service permettant la récupération d'un employé par son id
     * @param employeId : l'identifiant de l'employé recherché
     * @return une instance de l'employé recherché, ou null en cas d'exception
     */
    public Employe rechercherEmploye(Long employeId) {
        EmployeDao eDao = new EmployeDao();
        Employe emp;

        try {
            JpaUtil.creerContextePersistance();
            emp = eDao.chercherParId(employeId);
        } catch(Exception e) {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la récupération par id de l'employé !\n Message : {0}", e.getLocalizedMessage());
            emp = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return emp;
    }

    /**
     * Service permettant la récupération de la liste des employés
     * @return une liste d'employé, ou null en cas d'exception
     */
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

    /**
     * Service permettant la demande d'une consultation par un client pour un médium
     * Cas d'exception :
     *  - le client a déjà une consultation en cours
     *  - aucun employé n'est assignable
     * @param client : le client réalisant la demande
     * @param medium : le medium demandé par le client
     * @return la consultation nouvellement créée, null en cas d'exception
     */
    public Consultation demanderConsultation(Client client, Medium medium)
    {
        ConsultationDao consultationDao = new ConsultationDao();
        EmployeDao employeDao = new EmployeDao();
        Consultation consultation = null;

        if(obtenirConsultationEnCoursClient(client) != null)
        {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la création d'une consultation !\n Message : Le client a déjà une consultation en cours !");
            return null;
        }
        
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
            Message.envoyerNotification(employe.getNumTelephone(), "Bonjour, nous vous avons assigné un nouveau client. Rendez-vous sur votre espace personnel au plus vite pour le prendre en charge !");
            
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

    /**
     * Service permettant l'authentification et la récupération d'un utilisateur
     * Cas d'exception :
     *  - Adresse email inconnue
     *  - Mot de passe incorrect
     * @param mail : l'adresse email de l'utilisateur
     * @param motDePasse : le mot de passe de l'utilisateur
     * @return une instance de l'utilisateur correspondant au couple (email, mot de passe) si tout s'est bien passé,
     *        ou null en cas d'exception
     */
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

    /**
     * Service permettant de récupérer un medium par son id
     * @param mediumId : l'identifiant du medium à récupérer
     * @return une instance du medium recherché, ou null en cas d'exception
     */
    public Medium obtenirMedium(Long mediumId) {
        MediumDao mediumDao = new MediumDao();
        Medium medium;
        try {
            JpaUtil.creerContextePersistance();
            medium = mediumDao.chercherParId(mediumId);
        } catch(Exception e) {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la récupération d'un medium !\n Message : " + e.getLocalizedMessage());
            medium = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return medium;
    }

    /**
     * Service permettant de récupérer la liste des mediums
     * @return la liste des médiums, ou null en cas d'exception
     */
    public List<Medium> obtenirListeMediums() {
        MediumDao mediumDao = new MediumDao();
        List<Medium> listeMediums;

        try {
            JpaUtil.creerContextePersistance();
            listeMediums = mediumDao.fournirListMediums();
        } catch(Exception e) {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la récupération de la liste des médiums !\n Message : " + e.getLocalizedMessage());
            listeMediums = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

       return listeMediums;
    }


    /**
     * Service permettant l'ajout manuel d'une consultation
     * @param cons : la consultation à enregistrer, pas encore persistée
     * @return une instance de la consultation nouvellement persistée
     */
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

    /**
     * Service permettant la récupération d'une consultation par son id
     * @param consultationId : l'identifiant de la consultation recherchée
     * @return une instance de la consultation recherchée, ou null en cas d'exception
     */
    public Consultation obtenirConsultation(Long consultationId) {
        ConsultationDao dao = new ConsultationDao();
        Consultation cons;
        try {
            JpaUtil.creerContextePersistance();
            cons = dao.chercherParId(consultationId);
        } catch(Exception e) {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la récupération d'une consultation !\n Message : " + e.getLocalizedMessage());
            cons = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return cons;
    }
  
    /**
     * Services permettant la récupération de la consultation assignée actuellement à l'employé spécifié
     * @param employeId : une instance de l'employé cible
     * @return une instance de la consultation recherchée, ou null si elle n'existe pas ou en cas d'exception
     */
    public Consultation obtenirConsultationAssignee(Employe employe) {
        ConsultationDao cDao = new ConsultationDao();

        Consultation cons = null;
        try {
            JpaUtil.creerContextePersistance();
            List<Consultation> lCons = cDao.chercherParEmploye(employe);

            for(Consultation c : lCons) {
                if(c.getDateFin() == null) { //Consultation en cours : c'est ce qu'on cherche
                    cons = c;
                    break; //Il n'y a en principe qu'une cons. en cours par employé, on peut donc sortir du for each
                    //(je sais que c'est censé être une structure while, mais puisqu'il n'y a pas de "while each" en java...)
                }
            }

        } catch(Exception e) {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la récupération d'une consultation assignée !\n Message : " + e.getLocalizedMessage());
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return cons;
    }

    /**
     * Service permettant la récupération de la consultation en cours pour un client
     * @param client : le client cible
     * @return une instance de la consultation recherchée, ou null si non trouvée ou en cas d'exception
     */
    public Consultation obtenirConsultationEnCoursClient(Client client) {
        ConsultationDao consDao = new ConsultationDao();
        ClientDao clientDao = new ClientDao();

        Consultation cons = null;
        try {
            JpaUtil.creerContextePersistance();
            try
            {
                cons = consDao.recupererConsultationEncoursClient(client);
            }
            catch(NoResultException e)
            {
                cons = null;
            }
        } catch(Exception e) {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur lors de la récupération d'une consultation client en cours !! \nMessage : " + e.getLocalizedMessage());
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return cons;
    }
    
    /**
     * Service permettant le démarrage d'une consultation. Envoie un SMS au client.
     * Cas d'exception :
     *  - la consultation n'a pas été assignée (pas de date d'assignation)
     * @param consultation : une instance de la consultation à démarrer
     * @return une instance de la consultation démarrée
     */
    public Consultation demarrerConsultation(Consultation consultation) {
        ConsultationDao cDao = new ConsultationDao();

        try {
            JpaUtil.creerContextePersistance();

            Date dateAss = consultation.getDateAssignation();
            if(dateAss == null) {
                throw new Exception("La consultation n'a pas de date d'assignation !");
            }

            Client cli = consultation.getClient();
            Employe emp = consultation.getEmploye();
            Medium med = consultation.getMedium();
            JpaUtil.ouvrirTransaction();
            //Par défaut, les dates ont pour valeur d'initialisation le temps actuel.
            consultation.setDateDebut(new Date());
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
        } catch(Exception e) {
            JpaUtil.annulerTransaction();
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur de demarrerConsultation() :", e);
            consultation = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return consultation;
    }

    /**
     * Service permettant de terminer une consultation. Met à jour la date de fin
     * de la consultation et son commentaire.
     * Cas d'exception :
     *  - La consultation n'a pas commencé
     * @param consultation : une instance de la consultation à terminer
     * @param commentaire : le commentaire de fin de consultation
     * @return une instance de la consultation terminée, null en cas d'exception
     */
    public Consultation terminerConsultation(Consultation consultation, String commentaire) {
        ConsultationDao cDao = new ConsultationDao();

        try {
            JpaUtil.creerContextePersistance();

            Date dateDeb = consultation.getDateDebut();
            if(dateDeb == null) {
                throw new Exception("La consultation n'a pas commencé !");
            }

            JpaUtil.ouvrirTransaction();
            consultation.setDateFin(new Date());
            consultation.setCommentaire(commentaire);
            consultation.getEmploye().setDisponibilite(Boolean.TRUE);
            cDao.modifier(consultation);
            
            JpaUtil.validerTransaction();

            Logger.getLogger("Services").log(Level.INFO,"Consultation terminée avec succès");

        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur de demarrerConsultation() :", e);
            consultation = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return consultation;
    }

    /**
     * Service permettant la demande d'une aide pendant une consultation
     * @param client : le client concerné par la demande
     * @param amour : le score d'amour
     * @param sante : le score de santé
     * @param travail : le score de travail
     * @return une liste de chaînes de caractères représentant une aide
     */
    public List<String> demanderAideConsultation(Client client, int amour, int sante, int travail) {
        ProfilAstral pa = client.getProfilAstral();
        AstroTest astro = new AstroTest();
        List<String> output = null;
        try {
            output = astro.getPredictions(pa.getCouleur(), pa.getAnimalTotem(),amour, sante,travail);
        } catch(Exception e) {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur de calcul de profil astral", e);
        }
        return output;
    }

    
    /**
     * Service permettant l'ajout d'un medium aux favoris d'un client
     * @param medium : une instance du medium à ajouter
     * @param client : le client mettant le medium en favoris
     * @return une instance du medium ajouté en favoris
     */
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
    
    

    /**
     * Service permettant de récupérer les statistiques de consultation par employé
     * (nombre de consultations par employé)
     * @return une map (employé => nombre de consultations) représentant les statistiques
     */

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

    /**
     * Service permettant la récupération des statistiques de consultation par médium
     * (nombre de consultations par medium)
     * @return une map (medium => nombre de consultations) représentant les statistiques
     */
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
                throw new Exception("No mediums in database");
            }
            
        } catch(Exception e) {
            Logger.getLogger("Services").log(Level.SEVERE, "Erreur au niveau de recupererNbConsultationsMediums()", e);
            
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return allMediumsNbConsultations;
    }
    
    /**
     * Service permettant de récupérer le top 5 des médiums par nombre de consultations
     * @return une map(medium => nombre de consultatins) contenant le top 5 des médiums. Ignore les médiums avec 0 consultations.
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

    /**
     * Service permettant de récupérer la liste des consultations pour un client
     * @param client : une instance du client dont on veut récupérer les consultations
     * @return la liste des consultations du client, ou null en cas d'exception
     */
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
    
    /**
     * Service permettant la récupération des consultations d'un employé
     * @param employe : une instance de l'employé dont on veut récupérer les consultationss
     * @return 
     */
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
