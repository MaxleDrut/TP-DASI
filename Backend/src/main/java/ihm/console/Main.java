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
        }
    }
    
}