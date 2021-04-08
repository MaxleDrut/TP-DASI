/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service.util;

import metier.modele.Astrologue;
import metier.modele.Cartomancien;
import metier.modele.Employe;
import metier.modele.Spirite;
import metier.service.Services;

/**
 *
 * @author Marie Guillevic
 */
public class PeuplementBD {

    public PeuplementBD() {
    }
    
    public void peuplementEmploye(){
            Services serviceRecrutement = new Services();
            Employe giselle = new Employe("Duprés","Giselle","0450692130","giselle.dupres@predictif.com",'F',"free","giselleDupres");
            serviceRecrutement.recrutement(giselle);
            Employe renaud = new Employe("Salomon","Renaud","0450695689","renaud.salomon@predictif.com",'H',"busy","renaudSalomon");
            serviceRecrutement.recrutement(renaud);
            Employe gerard = new Employe("Breton","Gerard","0450698539","gereard.breton@predictif.com",'H',"free","gerardBreton");
            serviceRecrutement.recrutement(gerard);
            Employe christine = new Employe("Delafeuille","Christine","0450694123","christine.delafeuille@predictif.com",'F',"free","christineDelafeuille");
            serviceRecrutement.recrutement(christine);
            Employe francois = new Employe("Hugo","Francois","0450697469","francois.hugo@predictif.com",'H',"free","francoisHugo");
            serviceRecrutement.recrutement(francois);
            Employe romain = new Employe("Galle","Romain","0450697852","romain.galle@predictif.com",'H',"free","romainGalle");
            serviceRecrutement.recrutement(romain);
            Employe florient = new Employe("Rascoussier","Florient","0450694532","florient.rascoussier@predictif.com",'H',"free","florientRascoussier");
            serviceRecrutement.recrutement(florient);
            Employe maxence = new Employe("Drutel","Maxence","0450694741","maxence.drutel@predictif.com",'H',"free","maxenceDrutel");
            serviceRecrutement.recrutement(maxence);
            Employe marie = new Employe("Guillevic","Marie","0450698582","marie.guillevic@predictif.com",'F',"free","marieGuillevic");
            serviceRecrutement.recrutement(marie);
            Employe laura = new Employe("Kelche","Laura","0450692526","laura.kelche@predictif.com",'F',"busy","lauraKelche");
            serviceRecrutement.recrutement(laura);

    }
    
    public void peuplementMedium(){
            Services serviceCreationMedium = new Services();
            Spirite gwenaelle = new Spirite("Boule de cristal",'F',"Gwenaëlle","Spécialiste des grandes conversations au-delà de TOUTES les frontières");
            serviceCreationMedium.inventerMedium(gwenaelle);
            Spirite tran = new Spirite("Marc de café, boule de cristal, oreilles de lapin",'H',"Professeur Tran","Votre avenir est devant vous : regardons-le ensemble !");
            serviceCreationMedium.inventerMedium(tran);
            Cartomancien irma = new Cartomancien('F',"Mme Irma","Comprenez votre entourage grâce à mes cartes ! Résultats rapides");
            serviceCreationMedium.inventerMedium(irma);
            Cartomancien endora = new Cartomancien('F',"Endora","Mes cartes répondront à toutes vos questions personnelles");
            serviceCreationMedium.inventerMedium(endora);
            Astrologue serena = new Astrologue("École Normale Supérieure d’Astrologie (ENS-Astro)",2006,'F',"Serena","Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre passé");
            serviceCreationMedium.inventerMedium(serena);
            Astrologue m = new Astrologue("Institut des Nouveaux Savoirs Astrologiques",2010,'H',"Mr M","Avenir, avenir, que nous réserves-tu ? N'attendez plus, demandez à me consulter!");
            serviceCreationMedium.inventerMedium(m);

    }
}
