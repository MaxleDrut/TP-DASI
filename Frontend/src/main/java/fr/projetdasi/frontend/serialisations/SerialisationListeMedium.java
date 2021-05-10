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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Astrologue;
import metier.modele.Cartomancien;
import metier.modele.Medium;
import metier.modele.Spirite;

public class SerialisationListeMedium extends Serialisation {
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) {
        JsonObject container = new JsonObject();
        
        List<Medium> mediums = (List<Medium>)request.getAttribute("mediums");
        
        //System.out.println(mediums.size());
        JsonArray jsonMediums = new JsonArray();
        for(Medium med : mediums) {
            
            //System.out.println(med);
            JsonObject jMed = new JsonObject();
            
            jMed.addProperty("denomination",med.getDenomination());
            jMed.addProperty("presentation",med.getPresentation());
            
            String type = "";
            if(med instanceof Spirite) {
                type = "Spirite";
                Spirite spi = (Spirite)med;
                jMed.addProperty("support",spi.getSupport());
            } else if(med instanceof Cartomancien) {
                type = "Cartomancien";
            } else {
                type = "Astrologue";
                Astrologue astro = (Astrologue)med;
                jMed.addProperty("formation",astro.getFormation());
                jMed.addProperty("promotion",astro.getPromotion());
            }
            jMed.addProperty("type",type);
            
            jsonMediums.add(jMed);
        }
        
        container.add("mediums",jsonMediums);
       
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
