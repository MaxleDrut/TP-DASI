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
import metier.modele.Employe;

public class SerialisationEmploye extends Serialisation
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
            Employe employe = (Employe) request.getAttribute("employe");
            JsonObject employeJson = new JsonObject();
            
            employeJson.addProperty("id", employe.getId());
            employeJson.addProperty("genre", employe.getGenre());
            employeJson.addProperty("nom", employe.getNom());
            employeJson.addProperty("prenom", employe.getPrenom());
            employeJson.addProperty("email", employe.getMail());
            employeJson.addProperty("telephone", employe.getNumTelephone());
            
            result.add("employe", employeJson);
        }
        else
        {
            result.add("employe", null);
        }
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(result, this.getWriter(response));
    }    
}
