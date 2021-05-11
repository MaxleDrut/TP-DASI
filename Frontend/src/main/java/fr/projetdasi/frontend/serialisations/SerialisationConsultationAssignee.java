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
import metier.modele.Consultation;

public class SerialisationConsultationAssignee extends Serialisation 
{
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Consultation cons = (Consultation) request.getAttribute("consultation");
        
        JsonObject result = new JsonObject();
        if(cons != null)
        {
            JsonObject consultationJson = new JsonObject();
            
            JsonObject medium = new JsonObject();
            JsonObject client = new JsonObject();
            
            medium.addProperty("id", cons.getMedium().getId());
            medium.addProperty("nom", cons.getMedium().getDenomination());
            
            client.addProperty("id", cons.getClient().getId());
            client.addProperty("prenom", cons.getClient().getPrenom());
            client.addProperty("nom", cons.getClient().getNom());
            
            consultationJson.addProperty("assignation", cons.getDateAssignation().getTime());
            consultationJson.add("medium", medium);
            consultationJson.add("client", client);
            
            result.add("consultation", consultationJson);
        }
        else
        {
            result.add("consultation", null);
        }
        
        result.addProperty("success", (boolean) request.getAttribute("success"));
        result.addProperty("message", (String) request.getAttribute("message"));
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(result, this.getWriter(response));
    }
}
