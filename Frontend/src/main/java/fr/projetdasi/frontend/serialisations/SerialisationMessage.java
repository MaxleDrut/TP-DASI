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

public class SerialisationMessage extends Serialisation 
{
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
        JsonObject result = new JsonObject();
        result.addProperty("success", (boolean) request.getAttribute("success"));
        result.addProperty("message", (String) request.getAttribute("message"));
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(result, this.getWriter(response));
    }
}
