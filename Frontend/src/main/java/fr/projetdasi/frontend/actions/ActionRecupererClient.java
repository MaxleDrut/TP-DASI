/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.service.Services;

public class ActionRecupererClient extends Action{

    
    @Override
    public void executer(HttpServletRequest request) {
        //Recupération des paramètres de la Requête
        String idString = request.getParameter("id");
        
        long id;
        try {
            id = Long.valueOf(idString);
            Services service = new Services();
            
            Client client = service.rechercherClient(id);
            if(client == null) {
                throw new Exception("Le client est introuvable");
            }
            request.setAttribute("client",client);
            request.setAttribute("success",true);
        } catch (Exception e) {
            System.out.println(e);
            request.setAttribute("success",false);
            request.setAttribute("message", e.getLocalizedMessage());
        }
        
        //Instanciation de la classe service
        
    }
    
}
