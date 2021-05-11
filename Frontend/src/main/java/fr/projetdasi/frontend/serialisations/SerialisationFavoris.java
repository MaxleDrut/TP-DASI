/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.serialisations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Client;
import static metier.modele.Consultation_.medium;
import metier.modele.Medium;

public class SerialisationFavoris extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
        boolean success = (boolean) request.getAttribute("success");
        JsonObject result = new JsonObject();
        
        if(success)
        {
            Client client = (Client) request.getAttribute("client");
            Medium medium = (Medium) request.getAttribute("medium");
            result.addProperty("success", true);
            result.addProperty("medium",medium.getId());
            result.addProperty("client",client.getId());
        }
        else
        {
            result.addProperty("success", false);
        }
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        this.getWriter(response).println(gson.toJson(result));
        
        
    }
}
