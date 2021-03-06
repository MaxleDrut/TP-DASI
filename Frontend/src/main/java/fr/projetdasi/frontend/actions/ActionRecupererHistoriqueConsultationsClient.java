/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.service.Services;

public class ActionRecupererHistoriqueConsultationsClient extends Action{

    
    @Override
    public void executer(HttpServletRequest request) {
        //Recupération des paramètres de la Requête
        
        try {
            HttpSession session = request.getSession();
            
            if(!((boolean) session.getAttribute("connecte"))) {
                throw new Exception("Pas d'utilisateur connecte !");
            }
            
            long id = (long) session.getAttribute("id");
            request.setAttribute("idClient",id);
            
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
