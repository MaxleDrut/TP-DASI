/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm.console;

import dao.ConsultationDao;
import dao.EmployeDao;
import dao.JpaUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.modele.Utilisateur;
import metier.service.Services;
import metier.service.util.PeuplementBD;

/**
 *
 * @author onyr
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JpaUtil.init();
        PeuplementBD peuplementBD = new PeuplementBD();
        peuplementBD.peuplementEmploye();
        peuplementBD.peuplementMedium();
        testerObtenirMedium();
        testerObtenirListMedium();        
        testerAssignationConsultation();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try { //Nécessaire pour les dates
            testerInscriptionClient("Scuturici","Vasile-Marian","7 Avenue Jean Capelle, Villeurbanne","0628146942",sdf.parse("03-02-1978"),"vasile-marian.scuturici@insa-lyon.fr","algo");
            testerInscriptionClient("Guillevic","Marie","3 rue de la paix, Saint-Perreux","0614218795",sdf.parse("05-04-2000"),"marieguillevic@outlook.com","noisette");
            testerInscriptionClient("Micron","Manuel","54 rue du Faubourg Saint-Honoré, Paris","0899112233",sdf.parse("21-12-1977"),"manuel@caramail.com","brigitte");
            testerInscriptionClient("Maurincomme","Eric","168 cours Emile Zola, Villeurbanne","0472169589",sdf.parse("14-07-1969"),"emaurincomme@gmail","kfet");
        
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur d'inscription (checkez les dates)", e);
        }      
        testerAidePrediction(1,2,3);
        authentificationIntervative();  
        
    }
    
    public static void testerAidePrediction(int amour, int sante, int travail) {
        Services serv = new Services();
        List<Client> lcl = serv.obtenirListeClients();
        
        if(lcl!=null) {
             List<String> out = serv.demanderAideConsultation(lcl.get(0), amour,sante,travail);
             for(String s : out) {
                 System.out.println(s);
             }
        } else {
            System.out.println("C'est null");
        }
    }
    
    public static void testerInscriptionClient(String nom, String prenom, String adresse,String numTelephone,Date dateNaissance, String mail, String mdp){
        Services serviceInscription = new Services();
        Client client = new Client(nom,prenom,adresse,numTelephone,dateNaissance,mail,mdp);
        serviceInscription.inscrireClient(client);
        if(client==null){
            System.out.println("Une erreur est survenue sur le serveur");
        }else{
            System.out.println(client.toString());
        }
    }
    
    public static void testerInscriptionInterractive() {
        
        System.out.println("Bonjour !");
        
        // inscription

        String stop =Saisie.lireChaine("Voulez-vous vous inscrire (oui ou non) ?");
        while(!stop.equals("non")){
            if(stop.equals("oui")){
                String nom = Saisie.lireChaine("Veuillez entrer votre nom : ");
                String prenom = Saisie.lireChaine("Veuillez entrer votre prenom : ");
                String adresse = Saisie.lireChaine("Veuillez entrer votre adresse : ");
                String mail = Saisie.lireChaine("Veuillez entrer votre mail : ");
                String numTelephone = Saisie.lireChaine("Veuillez entrer votre numero de telephone : ");
                String mdp = Saisie.lireChaine("Veuillez entrer votre mot de passe : ");
                try{
                    String dateNaissance = Saisie.lireChaine("Veuillez entrer votre dateNaissance (yyyy-MM-dd) : ");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date birthday = sdf.parse(dateNaissance);
                    testerInscriptionClient(nom,prenom,adresse,numTelephone, birthday,mail,mdp);
                } catch(Exception e) {
                    System.out.println("Erreur lors de la date");
                }
               
            }
            stop=Saisie.lireChaine("Voulez-vous vous inscrire (oui ou non) ?");
           
        }
    }
    
    public static void authentificationIntervative() {
        String stop;
        
        boolean doAuth = true;
        while(doAuth) {
            stop = Saisie.lireChaine("Voulez-vous vous authentifier (oui ou non)?");
            if(stop.equals("oui")) {
               String mail = Saisie.lireChaine("Veuillez entrer son email : ");
               String mdp = Saisie.lireChaine("Veuillez entrer son mot de passe : ");

                // testerAuthentification(mail,mdp);
                Services services = new Services();
                Utilisateur utilisateur = services.authentification(mail, mdp);
                if(utilisateur == null) {
                    System.out.println("Erreur d'authentification, veuillez reessayer");
                } else {
                    System.out.println("Authentification réussie [" + utilisateur.getMail() + "]");
                    doAuth = false;
                }
            } else {
                doAuth = false;
            }
        }
    }
    
    public static void testerObtenirMedium(){
        Services serviceObtenirMedium = new Services();
        
        //Obtention réussie
        Medium medium1 = serviceObtenirMedium.obtenirMedium(11L);
        System.out.println(medium1.toString());
        
        //Obtention failed
        Medium medium2 = serviceObtenirMedium.obtenirMedium(1L);
        if(medium2==null){
            System.out.println("Aucun medium n'a été trouvé");
        }
    }
    
    public static void testerObtenirListMedium(){
        
        Services serviceObtenirListMedium = new Services();
        
        //Obtention réussie
        List<Medium> medium1 = serviceObtenirListMedium.obtenirListeMediums();
        if(medium1!=null){
            for(Medium medium : medium1 ){
                System.out.println(medium.toString());
            }
        }else{
            System.out.println("Aucun medium n'est repertorié");
        }
        
        
        
    }
    
    
    public static void testerAssignationConsultation()
    {
        Services services = new Services();
        
        // Client 17
        Client client = new Client("BLABLABLAA", "Placeholder", "Somewhere", "0213456789", new Date(), "pigeon@perchoir.com", "azerty1234");
        client = services.inscrireClient(client);
        
        // Medium
        Medium medium = services.obtenirListeMediums().get(0);
        
        // Employes
        List<Employe> employes = services.obtenirListeEmployes();
        
        // Création manuelle d'un historique de consultations
        ConsultationDao consultationDao = new ConsultationDao();
        try
        {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            for(int i = 0; i < 11; i++)
            {
                Employe e;
                if(i < 3) e = employes.get(0);      // 1er employé gagne 3 consultations
                else if(i < 6) e = employes.get(1); // 2e employé gagne 3 consultations
                else if(i < 8) e = employes.get(2); // 3e employé gagne 2 consultations
                else e = employes.get(3 + (i - 8)); // 4e, 5e et 6e employés gagnent 1 consultation chacun
                
                Consultation cons = new Consultation(e, medium, client, new Date());
                cons.setCommentaire("No comment");
                cons.setDateDebut(new Date());
                cons.setDateFin(new Date());
                consultationDao.creer(cons);
            }   
            
            JpaUtil.validerTransaction();
            JpaUtil.fermerContextePersistance();
        }
        catch(Exception e)
        {
            JpaUtil.annulerTransaction();
            JpaUtil.fermerContextePersistance();
            e.printStackTrace();
            System.exit(1);
        }
        
        Consultation cons1 = services.demanderConsultation(client, medium);
        Consultation cons2 = services.demanderConsultation(client, medium);
        Consultation cons3 = services.demanderConsultation(client, medium);
        
        System.out.println("-*-*-*--*---*--*-*-*-*-*-");
        System.out.println(cons1.getEmploye());
        System.out.println(cons2.getEmploye());
        System.out.println(cons3.getEmploye());
        System.out.println("-*-*-*--*---*--*-*-*-*-*-");
        
        System.out.println("Doit afficher une erreur :");
        services.demanderConsultation(client, medium);
        
        // tester d'assigner plusieurs consultations à la suite pour voir qui chope quoi
        EmployeDao employeDao = new EmployeDao();
        
        try
        {
            JpaUtil.creerContextePersistance();
            
            
            Employe employeLibre1 = employeDao.employeAssignableAvecGenre('F');
            
            Employe employeLibre2 = employeDao.employeAssignableAvecGenre('F');
        
            /* Doit afficher: 
             *  Giselle
                
             *
             */
            System.out.println("Employes assigné ===========================================");
            System.out.println(employeLibre1);
            System.out.println(employeLibre2);
            System.out.println("Fin employes assigné ===========================================");
            
            JpaUtil.fermerContextePersistance();
        }
        catch(Exception e)
        {
            JpaUtil.fermerContextePersistance();
            e.printStackTrace();
            System.exit(1);
        }
        
        
        
        
        
        
    }
}