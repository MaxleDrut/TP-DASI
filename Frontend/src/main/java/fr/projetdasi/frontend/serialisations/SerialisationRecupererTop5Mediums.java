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
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.modele.Utilisateur;

/**
 *
 * @author Marie Guillevic
 */
public class SerialisationRecupererTop5Mediums extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
       
        JsonObject reponse = new JsonObject();
        
        
        Map<Medium,Long> top5 = (Map<Medium,Long>)request.getAttribute("top5Mediums");
        
        if(top5!=null){
            
            reponse.addProperty("success",true);    
            
            JsonArray jsonTop5 = new JsonArray();
            
            for(Iterator i = top5.keySet().iterator(); i.hasNext();){
                JsonObject mediumStat = new JsonObject();
                
                JsonObject mediumProperty = new JsonObject();
                Medium medium = (Medium) i.next();
                mediumProperty.addProperty("nom",medium.getDenomination());
                
                
                Long nbConsult = (Long) top5.get(medium);
                
                mediumStat.add("Medium",mediumProperty);
                mediumStat.addProperty("nb_consultations",nbConsult);  
                
                jsonTop5.add(mediumStat);
                
            }
            reponse.add("top5",jsonTop5);
            
            
        }else{
            reponse.addProperty("success", false);
        }
        
                                               
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        PrintWriter out = this.getWriter(response);
        gson.toJson(reponse, out);
    }
    
}
