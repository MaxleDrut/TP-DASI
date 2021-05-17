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

public class SerialisationDemanderConsultation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
        boolean success = (boolean) request.getAttribute("success");
        JsonObject result = new JsonObject();
        
        if(success)
        {
            Consultation consultation = (Consultation) request.getAttribute("consultation");
            
            result.addProperty("success", true);
            result.addProperty("consultation",consultation.getId());
        }
        else
        {
            result.addProperty("success", false);
            String msgErreur = (String) request.getAttribute("msgErreur");
            result.addProperty("msgErreur",msgErreur);
        }
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        this.getWriter(response).println(gson.toJson(result));
    }
}
