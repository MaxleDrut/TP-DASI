/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.serialisations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Cartomancien;
import metier.modele.Consultation;
import metier.modele.Medium;
import metier.modele.Spirite;

public class SerialisationRecupererHistoriqueConsultationsClient extends Serialisation {
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) {
        JsonObject container = new JsonObject();
        
        List<Consultation> consultations = (List<Consultation>)request.getAttribute("consultations");
        
        JsonArray arrayConsultations = new JsonArray();
        
        DateFormat formatJMA = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat formatMinutes = new SimpleDateFormat("mm");
        DateFormat formatHeures = new SimpleDateFormat("hh");
                
        if((boolean)request.getAttribute("success") == true) {
            
            for(Consultation cons : consultations) {
                Date dateDebut = cons.getDateDebut();
                Date dateFin = cons.getDateFin();
                
                //Consultation terminee
                if(dateDebut != null && dateFin != null) {
                    JsonObject jConsultation = new JsonObject();

                    String debut = formatJMA.format(dateDebut);

                    //Durée de la consultation en minutes : écart des minutes + 60*écart des heures

                    int duree = Integer.valueOf(formatMinutes.format(dateFin)) - Integer.valueOf(formatMinutes.format(dateDebut));
                    duree+= 60*(Integer.valueOf(formatHeures.format(dateFin)) - Integer.valueOf(formatHeures.format(dateDebut)));

                    //On fait l'hypothèse qu'une consultation dure au + 24h. Il faut donc prendre en compte le cas
                    //Où une consultation commence à 23h et se termine à 1h du matin le lendemain :

                    if(formatJMA.format(dateFin).equals(formatJMA.format(dateDebut)) == false) {
                        duree+=360;
                    }


                    jConsultation.addProperty("debut",debut);
                    jConsultation.addProperty("duree",duree);

                    Medium medium = cons.getMedium();

                    String type;
                    if(medium instanceof Spirite) {
                        type = "Spirite";
                    } else if(medium instanceof Cartomancien) {
                        type = "Cartomancien";
                    } else {
                        type = "Astrologue";
                    }
                    jConsultation.addProperty("typeMedium",type);

                    jConsultation.addProperty("denominationMedium",medium.getDenomination());
                    jConsultation.addProperty("idMedium", medium.getId());
                    jConsultation.addProperty("genreMedium", medium.getGenre());

                    arrayConsultations.add(jConsultation);
                }
            }

            container.add("consultations",arrayConsultations);
        }
       
        try (PrintWriter out = this.getWriter(response)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(container,out);
            out.close();
        } catch (Exception e) {
            System.out.println("Erreur de Serialization de la Liste Medium");
            System.out.println(e);
        }
    }
}
