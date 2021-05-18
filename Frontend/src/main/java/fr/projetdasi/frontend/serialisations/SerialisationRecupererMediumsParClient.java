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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Medium;
import metier.modele.Client;

/**
 *
 * @author Marie Guillevic
 */
public class SerialisationRecupererMediumsParClient extends Serialisation{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject reponse = new JsonObject();
        
        
        Map<Client, Map<Medium, Long>> mediumsParClients = (Map<Client, Map<Medium, Long>>)request.getAttribute("mediums_par_clients");
        if(!mediumsParClients.isEmpty()){
            
            reponse.addProperty("success",true);    
            
            JsonArray jsonClients = new JsonArray();
            
            for(Iterator i = mediumsParClients.keySet().iterator(); i.hasNext();){
                JsonObject clientStat = new JsonObject();
                JsonObject clientProperty = new JsonObject();
                Client client = (Client) i.next();
                clientProperty.addProperty("prenom",client.getPrenom());  
                
                
                JsonArray jsonMediumsParClient = new JsonArray();
                Map<Medium,Long> consult_medium = (Map<Medium,Long>) mediumsParClients.get(client);
                if(!consult_medium.isEmpty()){
                    
                    for(Iterator j = consult_medium.keySet().iterator(); j.hasNext();){
                        JsonObject mediumStat = new JsonObject();
                        JsonObject mediumProperty = new JsonObject();
                        Medium medium = (Medium) j.next();
                        mediumProperty.addProperty("nom",medium.getDenomination()); 
                        Long nbConsult = (Long) consult_medium.get(medium);
                        mediumStat.add("medium", mediumProperty);
                        mediumStat.addProperty("nb_consultations",nbConsult); 
                        jsonMediumsParClient.add(mediumStat);
                    }
                    clientStat.add("client",clientProperty);
                    clientStat.add("listeMediums",jsonMediumsParClient);

                    jsonClients.add(clientStat);
                }
                
                
                
            }
            reponse.add("mediums_par_clients",jsonClients);
            
            
        }else{
            reponse.addProperty("success", false);
        }
        
                                               
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        PrintWriter out = this.getWriter(response);
        gson.toJson(reponse, out);
    }
    
}
