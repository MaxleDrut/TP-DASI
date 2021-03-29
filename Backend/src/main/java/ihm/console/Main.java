/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dasi_tp_1;

import dao.JpaUtil;
import ihm.console.Saisie;
import java.util.Arrays;
import java.util.List;
import metier.modele.Client;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import metier.service.ServiceClient;

/**
 *
 * @author onyr
 */
public class Main {
    
    public static Client rgalle;
    
    public static void testerInscriptionClient() {
        
        ServiceClient serviceClient = new ServiceClient();
        
        rgalle = new Client("romain", "galle", "romain.galle@insa-lyon.fr", "123456");
        
        Client onyr = new Client("onyr", "stellaeros", "onyr.official@gmail.com", "1111");
        
        Client client = serviceClient.InscrireClient(rgalle);
        if(client == null) {
            System.out.println(("Erreur inscription client rgalle"));
        } else {
            System.out.println(client.getPrenom() + " " + client.getNom() + " a bien été enregistré");
        }
        
        Client maxence = new Client("maxence", "drutel", "maxence.drutel@insa-lyon.fr", "aaaa");
        serviceClient.InscrireClient(maxence);
        
        Client jean = new Client("jean-eudes", "rascoussier", "jean.eudes@gmail.com", "ffffKKKK");
        serviceClient.InscrireClient(jean);
        
        Client marie = new Client("marie", "guillevic", "marie.guillevic@insa-lyon.fr", "1234567890");
        serviceClient.InscrireClient(marie);
    }
    
    public static void testerUniciteMailInscriptionClient() {
        Client rgalle2 = new Client("romain2", "galle", "romain.galle@insa-lyon.fr", "123456");
        ServiceClient serviceClient = new ServiceClient();
        
        Client client2 = serviceClient.InscrireClient(rgalle2);
        if(client2 == null) {
            System.out.println(("Erreur inscription client rgalle2"));
        } else {
            System.out.println(client2.getPrenom() + " " + client2.getNom() + " a bien été enregistré");
        }
    }
    
    public static void testerRechercheClient() {
        ServiceClient serviceClient = new ServiceClient();
        Client client = serviceClient.RechercherClient(rgalle.getId());
        if(client == null) {
            System.out.println(("Error rgalle not found"));
        } else {
            System.out.println(client.getPrenom() + " " + client.getNom() + " a bien été trouvé");
        }
    }
    
    public static void testerListeClients() {
        ServiceClient serviceClient = new ServiceClient();
        List<Client> listeClients = serviceClient.ObtenirListeClients();
        listeClients.forEach(client -> {
            System.out.println(client.getPrenom() + " " + client.getNom());
        });
    }
    
    public static void testerAuthentificationClient() {
        ServiceClient serviceClient = new ServiceClient();
        Client client = serviceClient.AuthentifierClient(rgalle.getMail(), rgalle.getMotDePasse());
        
        if(client == null) {
            System.out.println(("Error rgalle not authenticated"));
        } else {
            System.out.println(client.getPrenom() + " " + client.getNom() + " a bien été autentifié");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JpaUtil.init();
                      
        testerInscriptionClient();
        testerRechercheClient();
        testerListeClients();
        testerAuthentificationClient();
       
        // rentrer un nouvel utilisateur
        
        System.out.println("Bonjour ! Nouvelle Inscription");
        
        String nom = Saisie.lireChaine("Entrez votre nom: ");
        System.out.println("Valeur entrée " + nom);
        
        String prenom = Saisie.lireChaine("Entrez votre nom: ");
        System.out.println("Valeur entrée " + prenom);
        
        String mail = Saisie.lireChaine("Entrer votre mail: ");
        System.out.println("Valeur entrée " + mail);
        
        String motDePasse = Saisie.lireChaine("Entrer votre motDePasseail: ");
        System.out.println("Valeur entrée " + motDePasse);

        Saisie.pause();
        ServiceClient serviceClient = new ServiceClient();
        Client client = serviceClient.InscrireClient(new Client(nom, prenom, mail, motDePasse));
        if(client == null) {
            System.out.println(("Erreur inscription client rgalle"));
        } else {
            System.out.println(client.getPrenom() + " " + client.getNom() + " a bien été enregistré");
        }
        
        
        System.out.println("Au revoir !");
    }
    
}
