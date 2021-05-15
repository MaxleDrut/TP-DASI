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
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Consultation;

public class SerialisationListeConsultations extends Serialisation
{
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
        boolean success = (boolean) request.getAttribute("success");
        String message = (String) request.getAttribute("message");
        
        JsonObject result = new JsonObject();
        result.addProperty("success", success);
        result.addProperty("message", message);
        
        if(success)
        {
            List<Consultation> consultations = (List<Consultation>) request.getAttribute("consultations");

            
            JsonArray consultationsJson = new JsonArray();

            for(Consultation cons : consultations)
            {
                JsonObject consJson = new JsonObject();

                consJson.addProperty("id", cons.getId());
                consJson.addProperty("commentaire", cons.getCommentaire());

                consJson.addProperty("assignation", cons.getDateAssignation().getTime());
                consJson.addProperty("debut", cons.getDateDebut() != null ? cons.getDateDebut().getTime() : null);
                consJson.addProperty("fin", cons.getDateFin() != null ? cons.getDateFin().getTime() : null);

                JsonObject medium = new JsonObject();
                medium.addProperty("id", cons.getMedium().getId());
                medium.addProperty("nom", cons.getMedium().getDenomination());
                consJson.add("medium", medium);

                JsonObject client = new JsonObject();
                client.addProperty("id", cons.getClient().getId());
                client.addProperty("nom", cons.getClient().getNom());
                client.addProperty("prenom", cons.getClient().getPrenom());
                consJson.add("client", client);

                consultationsJson.add(consJson);
            }

            result.add("consultations", consultationsJson);
        }
        else
        {
            result.add("consultations", null);
        }
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(result, this.getWriter(response));
    }
}
