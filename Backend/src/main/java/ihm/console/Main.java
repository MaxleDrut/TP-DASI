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
import metier.modele.Client;
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
       /* System.out.println("Bonjour !");
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
        stop = Saisie.lireChaine("Voulez-vous rechercher un client (oui ou non)?");
         while(!stop.equals("non")){
            if(stop.equals("oui")){
                String mail = Saisie.lireChaine("Veuillez entrer son email : ");
                String mdp = Saisie.lireChaine("Veuillez entrer son mot de passe : ");
                
               // testerAuthentification(mail,mdp);
            }
            stop = Saisie.lireChaine("Voulez-vous rechercher un client (oui ou non)?");
         }*/
            
        
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