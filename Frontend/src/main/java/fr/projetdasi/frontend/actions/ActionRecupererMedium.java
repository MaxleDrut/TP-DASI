/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import javax.servlet.http.HttpServletRequest;
import metier.modele.Medium;
import metier.service.Services;

public class ActionRecupererMedium extends Action{

    @Override
    public void executer(HttpServletRequest request) {
        //Recupération des paramètres de la Requête
        String idString = request.getParameter("id");
        
        long id;
        try {
            id = Long.valueOf(idString);
            Services service = new Services();
            
            Medium medium = service.obtenirMedium(id);
            if(medium == null) {
                throw new Exception("Le medium est introuvable");
            }
            request.setAttribute("medium",medium);
            request.setAttribute("success",true);
        } catch (Exception e) {
            System.out.println(e);
            request.setAttribute("success",false);
            request.setAttribute("message", e.getLocalizedMessage());
        }
        
    }
    
}
