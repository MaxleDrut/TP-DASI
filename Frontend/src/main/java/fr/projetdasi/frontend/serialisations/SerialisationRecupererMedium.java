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
import metier.modele.Medium;

public class SerialisationRecupererMedium extends Serialisation {
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) {
        JsonObject container = new JsonObject();
        
        if((boolean)request.getAttribute("success") == true) {
            Medium medium = (Medium)request.getAttribute("medium");

            container.addProperty("denomination",medium.getDenomination());
            container.addProperty("genre",medium.getGenre());
        }
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
