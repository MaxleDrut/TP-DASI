/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.service.Services;

public class ActionRecupererHistoriqueConsultationsClient extends Action{

    
    @Override
    public void executer(HttpServletRequest request) {
        //Recupération des paramètres de la Requête
        String idString = request.getParameter("idClient");
        
        long id;
        try {
            id = Long.valueOf(idString);
            Services service = new Services();
            
            Client client = service.rechercherClient(id);
            if(client == null) {
                throw new Exception("Le client est introuvable");
            }
            
            List<Consultation> consultations = service.recupererConsultationsClient(client);
            
            if(consultations == null) {
                throw new Exception("Le clientn n'a jamais consulté");
            }
            
            request.setAttribute("consultations",consultations);
            request.setAttribute("success",true);
        } catch (Exception e) {
            System.out.println(e);
            request.setAttribute("success",false);
        }
        
    }
    
}
