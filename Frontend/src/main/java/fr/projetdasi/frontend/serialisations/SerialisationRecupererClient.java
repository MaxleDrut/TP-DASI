/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.serialisations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Client;
import metier.modele.ProfilAstral;

public class SerialisationRecupererClient extends Serialisation {
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) {
        JsonObject container = new JsonObject();
        
        Client client = (Client)request.getAttribute("client");
        
        container.addProperty("mail",client.getMail());
        container.addProperty("nom",client.getNom());
        container.addProperty("prenom",client.getPrenom());
        container.addProperty("adresse",client.getAdresse());
        container.addProperty("numtelephone",client.getNumTelephone());
        
        ProfilAstral profilAstral = client.getProfilAstral();
        
        JsonObject jsonAstral = new JsonObject();
        
        jsonAstral.addProperty("zodiac",profilAstral.getSigneZodiac());
        jsonAstral.addProperty("chinois",profilAstral.getSigneChinois());
        jsonAstral.addProperty("couleur",profilAstral.getCouleur());
        jsonAstral.addProperty("animal",profilAstral.getAnimalTotem());
        
        container.add("astral", jsonAstral);
       
        try (PrintWriter out = this.getWriter(response)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(container,out);
            out.close();
        } catch (Exception e) {
            System.out.println("Erreur de Serialization de la Liste Medium");
            System.out.println(e);
        }
    }
}
