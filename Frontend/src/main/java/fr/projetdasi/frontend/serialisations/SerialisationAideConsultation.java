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

public class SerialisationAideConsultation extends Serialisation
{
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
        boolean success = (boolean) request.getAttribute("success");
        String message = (String) request.getAttribute("message");
        
        JsonObject result = new JsonObject();
        result.addProperty("success", success);
        result.addProperty("message", message);
        
        JsonObject predJson = null;
        
        if(success)
        {
            predJson = new JsonObject();
            
            String amour = (String) request.getAttribute("amour");
            String sante = (String) request.getAttribute("sante");
            String travail = (String) request.getAttribute("travail");
            
            predJson.addProperty("amour", amour);
            predJson.addProperty("sante", sante);
            predJson.addProperty("travail", travail);
        }
        
        result.add("predictions", predJson);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(result, this.getWriter(response));
    }
}
