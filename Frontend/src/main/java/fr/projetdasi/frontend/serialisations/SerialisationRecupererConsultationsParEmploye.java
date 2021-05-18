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
import metier.modele.Employe;

/**
 *
 * @author Marie Guillevic
 */
public class SerialisationRecupererConsultationsParEmploye extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject reponse = new JsonObject();
        
        
        Map<Employe,Long> consult_employes = (Map<Employe,Long>)request.getAttribute("consultations_par_employe");
        
        if(consult_employes!=null){
            
            reponse.addProperty("success",true);    
            
            JsonArray jsonConsultations = new JsonArray();
            
            for(Iterator i = consult_employes.keySet().iterator(); i.hasNext();){
                JsonObject employeStat = new JsonObject();
                
                JsonObject employeProperty = new JsonObject();
                Employe employe = (Employe) i.next();
                employeProperty.addProperty("prenom",employe.getPrenom());              
                Long nbConsult = (Long) consult_employes.get(employe);
                
                employeStat.add("employe",employeProperty);
                employeStat.addProperty("nb_consultations",nbConsult);  
                
                jsonConsultations.add(employeStat);
                
            }
            reponse.add("consultationsEmploye",jsonConsultations);
            
            
        }else{
            reponse.addProperty("success", false);
        }
        
                                               
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        PrintWriter out = this.getWriter(response);
        gson.toJson(reponse, out);
    }
    
}
