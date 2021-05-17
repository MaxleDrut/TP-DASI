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
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Utilisateur;

/**
 *
 * @author Marie Guillevic
 */
public class SerialisationDeconnexion extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response)  throws IOException {
        JsonObject reponse = new JsonObject();
        
        reponse.addProperty("success",(boolean) request.getAttribute("success"));
                                               
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        PrintWriter out = this.getWriter(response);
        gson.toJson(reponse, out);
    }
}
