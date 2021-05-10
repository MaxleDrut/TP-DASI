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
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Utilisateur;

/**
 *
 * @author Marie Guillevic
 */
public class SerialisationConnexion extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response)  throws IOException {
        JsonObject reponse = new JsonObject();
        JsonObject utilisateurProperty = new JsonObject();
        
        
        Utilisateur utilisateur = (Utilisateur)request.getAttribute("utilisateur");
        
        if(utilisateur!=null){
            
            if(utilisateur instanceof Client){
                Client client = (Client) utilisateur;
                utilisateurProperty.addProperty("id", client.getId());
                utilisateurProperty.addProperty("prenom", client.getPrenom());
            }else{
                Employe employe = (Employe) utilisateur;
                utilisateurProperty.addProperty("id", employe.getId());
                utilisateurProperty.addProperty("prenom", employe.getPrenom());
            }
            reponse.addProperty("connexion", true);
            reponse.add("client", utilisateurProperty);
            
        }else{
            reponse.addProperty("connexion", false);
        }
        
                                               
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        PrintWriter out = this.getWriter(response);
        gson.toJson(reponse,out);
        out.close();
    }
    
}
