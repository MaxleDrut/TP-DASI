/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm.console;

import dao.JpaUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
import metier.modele.Medium;
import metier.modele.Utilisateur;
import metier.service.Services;
import metier.service.util.Message;
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
        
        testerInscriptionClient();  
        testerObtenirMedium();
        testerObtenirListMedium();        
         
        testerAidePrediction(4,2,3);
        authentificationIntervative();  
        
    }
    
    public static void testerAidePrediction(int amour, int sante, int travail) {
        Services serv = new Services();
        List<Client> lcl = serv.obtenirListeClients(); //Récupère le 1er client de la liste
        
        if(lcl!=null) {
             List<String> out = serv.demanderAideConsultation(lcl.get(0), amour,sante,travail);
             for(String s : out) {
                 System.out.println(s);
             }
        } else {
            System.out.println("C'est null");
        }
    }
    
    public static void testerInscriptionClient(){
        inscrire("Scuturici","Vasile-Marian","7 Avenue Jean Capelle, Villeurbanne","0628146942","03-02-1978","vasile-marian.scuturici@insa-lyon.fr","algo");
        inscrire("Guillevic","Marie","3 rue de la paix, Saint-Perreux","0614218795","05-04-2000","marieguillevic@outlook.com","noisette");
        inscrire("Micron","Manuel","54 rue du Faubourg Saint-Honoré, Paris","0899112233","21-12-1977","manuel@caramail.com","brigitte");
        inscrire("Maurincomme","Eric","168 cours Emile Zola, Villeurbanne","0472169589","14-07-1969","emaurincomme@gmail.com","kfet");
       
        //Mauvais format de date
        inscrire("Dupont","Jean","28 rue de la République, Lyon","0472218587","3104-1980","jean.dupont@free.fr","guignol");
        
    }
    
    //Nécessaire aux try catches pour les dates
    public static void inscrire(String nom, String prenom, String adresse,String numTelephone,String dateNaissance, String mail, String mdp) {
        Services serviceInscription = new Services();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
           Client cli = new Client(nom,prenom,adresse,numTelephone,sdf.parse(dateNaissance),mail,mdp);
           serviceInscription.inscrireClient(cli);
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur de format de la date, client non inscrit");
            Message.envoyerMail("noreply@predictif.fr", mail, "Echec d'inscription", "Une erreur d'inscription est survenue. Veuillez rééssayer");
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
                String dateNaissance = Saisie.lireChaine("Veuillez entrer votre dateNaissance (yyyy-MM-dd) : ");
                
                inscrire(nom,prenom,adresse,numTelephone,dateNaissance,mail,mdp);
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
        List<Medium> medium1 = serviceObtenirListMedium.obtenirListMedium();
        if(medium1!=null){
            for(Medium medium : medium1 ){
                System.out.println(medium.toString());
            }
        }else{
            System.out.println("Aucun medium n'est repertorié");
        }    
        
    }
}