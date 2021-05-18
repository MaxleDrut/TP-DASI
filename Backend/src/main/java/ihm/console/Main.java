/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm.console;

import dao.ClientDao;
import dao.ConsultationDao;
import dao.EmployeDao;
import dao.JpaUtil;
import static ihm.console.Utils.assertEquals;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
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

        // tests en hard
        testerInscriptionClient();
        testerObtenirMedium();
        testerObtenirListeMedium();
        testerAssignationConsultation();
        testerAuthentification();
        testerAidePrediction(1,2,3);
        testerAidePrediction(4,2,3);
        testerAjouterMediumAuxFavoris();
        testerEnleverMediumDesFavoris();
        
        
        // interface interactive
        /*authentificationIntervative();*/  
        testerObtenirEmploye();
        testerObtenirListeEmployes();
        ajoutManuelCons();
        testerObtenirConsultation();       
        testerRecupererNombreConsultationsEmploye();

        testerRecupListeConsultations();
        testerObtConsultationAss();
        testerDemarrerConsultation();
        testerTerminerConsultation();

        testerRecupererNbConsultationsMediums();
        testerRecupererTop5Mediums();
        
        
        /*authentificationIntervative();*/
        testerAjouterMediumAuxFavoris();
        
        testerRecupererMediumsParClient();
    }

    public static void testerAidePrediction(int amour, int sante, int travail) {
        System.out.println("============= testerAidePrediction() ===========");
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
        System.out.println("============= testerInscriptionClient() ===========");
        inscrire("Scuturici","Vasile-Marian","7 Avenue Jean Capelle, Villeurbanne","0628146942","03-02-1978","vasile-marian.scuturici@insa-lyon.fr","algo");
        inscrire("Guillevic","Marie","3 rue de la paix, Saint-Perreux","0614218795","05-04-2000","marieguillevic@outlook.com","noisette");
        inscrire("Micron","Manuel","54 rue du Faubourg Saint-Honoré, Paris","0899112233","21-12-1977","manuel@caramail.com","brigitte");
        inscrire("Maurincomme","Eric","168 cours Emile Zola, Villeurbanne","0472169589","14-07-1969","emaurincomme@gmail.com","kfet");

        //Mauvais format de date
        inscrire("Dupont","Jean","28 rue de la République, Lyon","0472218587","3104-1980","jean.dupont@free.fr","guignol");
    }

    //Nécessaire aux try catches pour les dates
    public static void inscrire(String nom, String prenom, String adresse,String numTelephone,String dateNaissance, String mail, String mdp) {
        System.out.println("============= inscrire() ===========");
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

    public static void testerAuthentification() {
        System.out.println("============= testerAuthentification() ===========");
        // cas du succès
        Utilisateur guillevic = authentifierUtilisateur("marieguillevic@outlook.com", "noisette");
        if(guillevic == null) {
            throw new AssertionError("Problème sur authentifierUtilisateur(\"marieguillevic@outlook.com\", \"noisette\")");
        }

        // cas de l'erreur de mail
        Utilisateur guillevicFalse = authentifierUtilisateur("marieguillevic@error.com", "noisette");
        assertEquals(guillevicFalse, null);

        // cas de l'erreur de mdp
        Utilisateur guillevicFalse2 = authentifierUtilisateur("marieguillevic@error.com", "false");
        assertEquals(guillevicFalse2, null);
    }

    public static void testerInscriptionInterractive() {
        System.out.println("============= testerInscriptionInterractive() ===========");
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
        System.out.println("============= authentificationInteractive() ===========");
        String stop;

        boolean doAuth = true;
        while(doAuth) {
            stop = Saisie.lireChaine("Voulez-vous vous authentifier (oui ou non)?");
            if(stop.equals("oui")) {
               String mail = Saisie.lireChaine("Veuillez entrer son email : ");
               String mdp = Saisie.lireChaine("Veuillez entrer son mot de passe : ");

                Utilisateur utilisateur = authentifierUtilisateur(mail, mdp);
                if(utilisateur != null) {
                    doAuth = false;
                }
            } else {
                doAuth = false;
            }
        }
    }

    public static Utilisateur authentifierUtilisateur(String mail, String mdp) {
        System.out.println("============= authentifierUtilisateur() ===========");
        Services services = new Services();
        Utilisateur utilisateur = services.authentification(mail, mdp);
        if(utilisateur == null) {
            System.out.println("Erreur d'authentification, veuillez reessayer");
        } else {
            System.out.println("Authentification réussie [" + utilisateur.getMail() + "]");
        }
        return utilisateur;
    }

    public static void testerObtenirMedium(){
        System.out.println("============= testerObtenirMedium() ===========");
        Services serviceObtenirMedium = new Services();

        //Obtention réussie
        Medium medium1 = serviceObtenirMedium.obtenirMedium(11L);
        if(medium1!=null){
            System.out.println(medium1.toString());
        }else{
            System.out.println("Aucun medium n'a été trouvé");
        }


        //Obtention failed
        Medium medium2 = serviceObtenirMedium.obtenirMedium(1L);
        if(medium2!=null){
            System.out.println(medium2.toString());
        }else{
            System.out.println("Aucun medium n'a été trouvé");
        }
    }

    public static void testerObtenirListeMedium(){
        System.out.println("============= testerObtenirListeMedium() ===========");
        Services serviceObtenirListeMedium = new Services();

        //Obtention réussie
        List<Medium> medium1 = serviceObtenirListeMedium.obtenirListeMediums();

        if(medium1!=null){
            for(Medium medium : medium1 ){
                System.out.println(medium.toString());
            }
        } else {
            System.out.println("Aucun medium n'est repertorié");

        }
    }

    public static void testerObtenirEmploye(){
        System.out.println("============= testerObtenirEmploye() ===========");
        Services serv = new Services();

        //Obtention réussie
        Employe emp = serv.rechercherEmploye(11L);
        if(emp!=null){
           System.out.println(emp.toString());
        }

        //Obtention failed
        emp = serv.rechercherEmploye(1L);
        if(emp==null){
            System.out.println("Aucun employé n'a été trouvé");
        } else {
            System.out.println(emp.toString());
        }
    }

    public static void testerObtenirListeEmployes(){
        System.out.println("============= testerObtenirListeEmployes() ===========");
        Services serv= new Services();

        //Obtention réussie
        List<Employe> lEmp = serv.obtenirListeEmployes();
        if(lEmp!=null){
            for(Employe e : lEmp ){
                System.out.println(e.toString());
            }
        } else {
            System.out.println("Aucun employé n'est repertorié");

        }
    }

    public static void testerAjouterMediumAuxFavoris(){
        System.out.println("============= testerAjouterMediumAuxFavoris() ===========");
        Services serviceAjoutMediumAuxFavoris = new Services();
        
        //Ajout du medium 11 à la liste des favoris du client 17
        System.out.println("============================ Ajout du medium n°11 à la liste des favoris du client n°17 :");
        Medium medium = serviceAjoutMediumAuxFavoris.obtenirMedium(11L);
        Client client = serviceAjoutMediumAuxFavoris.rechercherClient(17L);
        medium = serviceAjoutMediumAuxFavoris.ajouterMediumAuxFavoris(medium, client);
        if(medium!=null){
            System.out.println(" Medium " + medium.getDenomination()+ " ajouté aux favoris");
        }
        
        //Ajout du medium 11 à la liste des favoris du client 17 (déja dans sa liste : ne sera pas ajouté 2 fois)
       System.out.println("============================ Ajout du medium n°11 à la liste des favoris du client n°17 :");
        Medium medium2 = serviceAjoutMediumAuxFavoris.obtenirMedium(11L);
        medium2 = serviceAjoutMediumAuxFavoris.ajouterMediumAuxFavoris(medium2, client);
        if(medium2!=null){
            System.out.println(" Medium " + medium2.getDenomination()+ " ajouté aux favoris");
        }
        
        //Ajout du medium 1 à la liste des favoris du client 17 (il n'y a pas de medium 1)
        System.out.println("============================ Ajout du medium n°1 à la liste des favoris du client n°17 :");
        Medium medium3 = serviceAjoutMediumAuxFavoris.obtenirMedium(1L);
        medium3 = serviceAjoutMediumAuxFavoris.ajouterMediumAuxFavoris(medium3, client);
        if(medium3!=null){
            System.out.println(" Medium " + medium3.getDenomination()+ " ajouté aux favoris");
        }

    }

    public static void testerEnleverMediumDesFavoris(){
        
        System.out.println("============= testerEnleverMediumDesFavoris() ===========");
        
        Services serviceEnleverMediumDesFavoris = new Services();
        
        //Suppression du medium 11 de la liste des favoris du client 17
        System.out.println("============================ Suppression du medium n°11 de la liste des favoris du client n°17 :");
        Medium medium = serviceEnleverMediumDesFavoris.obtenirMedium(11L);
        Client client = serviceEnleverMediumDesFavoris.rechercherClient(17L);
        medium = serviceEnleverMediumDesFavoris.enleverMediumDesFavoris(medium, client);
        if(medium!=null){
            System.out.println(" Medium " + medium.getDenomination()+ " supprimé des favoris");
        }
        
        //Suppression du medium 11 de la liste des favoris du client 17 (déja supprimé)
        System.out.println("============================ Suppression du medium n°11 de la liste des favoris du client n°17 :");
        Medium medium2 = serviceEnleverMediumDesFavoris.obtenirMedium(11L);
        medium2 = serviceEnleverMediumDesFavoris.enleverMediumDesFavoris(medium2, client);
        if(medium2!=null){
            System.out.println(" Medium " + medium2.getDenomination()+ " supprimé des favoris");
        }
        //Suppression du medium 1 de la liste des favoris du client 17 (il n'y a pas de medium 1)
        System.out.println("============================ Suppression du medium n°1 de la liste des favoris du client n°17 :");
        Medium medium3 = serviceEnleverMediumDesFavoris.obtenirMedium(1L);
        medium3 = serviceEnleverMediumDesFavoris.enleverMediumDesFavoris(medium3, client);
        if(medium3!=null){
            System.out.println(" Medium " + medium3.getDenomination()+ " supprimé des favoris");
        }

    }
    
    
    //Cherche la consultation numéro 25 et 27
    public static void testerObtenirConsultation() {
        System.out.println("============= testerObtenirConsultation() ===========");
        Services serv = new Services();


        Consultation cons = serv.obtenirConsultation(25L);
        if(cons != null) {
            System.out.println(cons);
        } else {
            System.out.println("La consultation 25 n'a pas été trouvée");
        }

        cons = serv.obtenirConsultation(27L);
        if(cons != null) {
            System.out.println(cons);
        } else {
            System.out.println("La consultation 27 n'a pas été trouvée");
        }
    }

    public static void testerObtConsultationAss() {
        System.out.println("============= testerObtenirConsultationAssignée() ===========");
        Services serv = new Services();
        
        Employe emp = serv.obtenirListeEmployes().get(0);
        Consultation cons = serv.obtenirConsultationAssignee(emp);
        
        if(cons == null) {
            System.out.println("Le premier employé n'a pas de consultation assignée");
        } else {
            System.out.println(cons);
        }
        
    }
    
    public static void ajoutManuelCons() {
        System.out.println("============= ajoutManuelConsultation() ===========");
        Services serv = new Services();

        Client cli = serv.obtenirListeClients().get(0);
        Employe emp = serv.obtenirListeEmployes().get(0);
        Medium med = serv.obtenirListeMediums().get(0);

        Consultation cons = new Consultation(emp,med,cli,new Date());

        serv.ajouterConsultationManuellement(cons);
    }

    //Demarre la consultation numéro 1 (et envoie le sms)
    public static void testerDemarrerConsultation() {
        System.out.println("============= testerDemarrerConsultation() ===========");
        Services serv = new Services();
        
        Employe emp = serv.obtenirListeEmployes().get(0);
        Consultation cons = serv.obtenirConsultationAssignee(emp);
        
        serv.demarrerConsultation(cons);
        
    }

    public static void testerTerminerConsultation() {
        System.out.println("============= testerTerminerConsultation() ===========");
        Services serv = new Services();
        Employe emp = serv.obtenirListeEmployes().get(0);
        Consultation cons = serv.obtenirConsultationAssignee(emp);
        
        Consultation c = serv.terminerConsultation(cons, "L'interrogé semblait paniqué, mais les astres ont su le rassurer");
        System.out.println(c);

    }

    public static void testerAssignationConsultation()
    {
        System.out.println("============= testerAssignationConsultation() ===========");
        Services services = new Services();

        // Client 17
        Client client = new Client("BLABLABLAA", "Placeholder", "Somewhere", "0213456789", new Date(), "pigeon@perchoir.com", "azerty1234");
        client = services.inscrireClient(client);
        Client client2 = services.rechercherClient(17L);
        Client client3 = services.rechercherClient(18L);

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
        Consultation cons2 = services.demanderConsultation(client2, medium);
        Consultation cons3 = services.demanderConsultation(client3, medium);

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
             *  null
             *  null
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

    public static void testerRecupererNombreConsultationsEmploye(){
        System.out.println("============= testerRecupererNombreConsultationsEmploye() ===========");
        Services serviceConsultation = new Services();
        Map<Employe,Long> consultationsEmploye= serviceConsultation.recupererNombreConsultationsEmploye();
        for(Map.Entry<Employe,Long> mapEntry : consultationsEmploye.entrySet()){
             System.out.println(mapEntry.getKey()
                              + " | nombre de consultations: " + mapEntry.getValue());
        }
    }

    public static void testerRecupListeConsultations()
    {
        System.out.println("============= testerRecupListeConsultations() ===========");
        Services services = new Services();
        
        Client client = services.rechercherClient(21L);
        
        System.out.println("============================ Consultations du client n°21 :");
        services.recupererConsultationsClient(client).forEach(cons -> {
            System.out.println(cons);
        });
        
        
        Employe employe = services.rechercherEmploye(9L);
        
        System.out.println("============================ Consultations de l'employé n°9 :");
        services.recupererConsultationsEmploye(employe).forEach(cons -> {
            System.out.println(cons);
        });
    }
    
    public static void testerRecupererNbConsultationsMediums()
    {   
        Services services = new Services();
        Map<Medium, Long> mediumsNbConsultations = services.recupererNbConsultationsMediums();
        
        System.out.println("============= testerRecupererNbConsultationsMediums() ===========");
        if(mediumsNbConsultations.isEmpty())
        {
            System.out.println(" Erreur : pas de résultats dans mediumsNbConsultations.");
        } else {
            for(Map.Entry<Medium, Long> entry : mediumsNbConsultations.entrySet())
            {
                Medium medium = entry.getKey();
                Long nbConsultations = entry.getValue();
                System.out.println(
                    "medium = " + medium.getDenomination() +
                    ", nbConsultations = " + nbConsultations
                );
            }
        }
    }
    
    public static void testerRecupererTop5Mediums()
    {
        Services services = new Services();
        Map<Medium, Long> mediumsNbConsultationsTop5 = services.recupererTop5Mediums();
        
        System.out.println("============= testerRecupererTop5Mediums() ===========");
        if(mediumsNbConsultationsTop5.isEmpty())
        {
            System.out.println(" Erreur : pas de résultats dans mediumsNbConsultationsTop5.");
        } else {
            for(Map.Entry<Medium, Long> entry : mediumsNbConsultationsTop5.entrySet())
            {
                Medium medium = entry.getKey();
                Long nbConsultations = entry.getValue();
                System.out.println(
                    "medium = " + medium.getDenomination() +
                    ", nbConsultations = " + nbConsultations
                );
            }
        }
    }
    
    public static void testerRecupererMediumsParClient(){
        Services services = new Services();
        Map<Client, Map<Medium, Long>> mediumsParClient = services.recupererMediumsLesPlusConsultesParClient();
        
        System.out.println("============= testerRecupererMediumsParClient() ===========");
        if(mediumsParClient.isEmpty()){
            System.out.println("Il n'y a aucune consultation pour aucun client");
        }else{
            for(Iterator i = mediumsParClient.keySet().iterator(); i.hasNext();){
                Client client = (Client) i.next();
                System.out.println(client.getMail());
                
                Map<Medium,Long> consult_medium = (Map<Medium,Long>) mediumsParClient.get(client);
                if(consult_medium.isEmpty()){
                    System.out.println("Il n'y a aucune consultation pour ce client");
                }else{
                    for(Iterator j = consult_medium.keySet().iterator(); j.hasNext();){
                        Medium medium = (Medium) j.next();
                        Long nb = (Long) consult_medium.get(medium);
                        System.out.println(medium.getDenomination() + " nb : "+ nb);
                    }
                }
            }
        }
    }
    
}
