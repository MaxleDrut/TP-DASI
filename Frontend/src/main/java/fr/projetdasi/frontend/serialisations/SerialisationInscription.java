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

public class SerialisationInscription extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
        boolean success = (boolean) request.getAttribute("success");
        JsonObject result = new JsonObject();
        
        if(success)
        {
            Client client = (Client) request.getAttribute("client");
            
            result.addProperty("success", true);
            result.addProperty("name", client.getPrenom());
        }
        else
        {
            String message = (String) request.getAttribute("message");
            
            result.addProperty("success", false);
            result.addProperty("message", message);
        }
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        this.getWriter(response).println(gson.toJson(result));
    }
}
