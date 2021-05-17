/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.serialisations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Astrologue;
import metier.modele.Cartomancien;
import metier.modele.Medium;
import metier.modele.Spirite;

public class SerialisationRecupererMedium extends Serialisation {
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) {
        JsonObject container = new JsonObject();
        JsonObject mediumContainer = new JsonObject();
        
        if((boolean)request.getAttribute("success")) {
            Medium medium = (Medium)request.getAttribute("medium");

            mediumContainer.addProperty("denomination",medium.getDenomination());
            mediumContainer.addProperty("genre",medium.getGenre());
            mediumContainer.addProperty("presentation", medium.getPresentation());
            
            if(medium instanceof Astrologue)
            {
                Astrologue astro = (Astrologue) medium;
                mediumContainer.addProperty("formation", astro.getFormation());
                mediumContainer.addProperty("promotion", astro.getPromotion());
            }
            else if(medium instanceof Spirite)
            {
                Spirite spirite = (Spirite) medium;
                mediumContainer.addProperty("support", spirite.getSupport());
            }
        }
        
        container.add("medium", mediumContainer);
        container.addProperty("success", (boolean)request.getAttribute("success"));
        container.addProperty("message", "");
        
        try (PrintWriter out = this.getWriter(response)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(container,out);
            out.close();
        } catch (Exception e) {
            System.out.println("Erreur de Serialization de la récuperation du Medium");
            System.out.println(e);
        }
    }
}
