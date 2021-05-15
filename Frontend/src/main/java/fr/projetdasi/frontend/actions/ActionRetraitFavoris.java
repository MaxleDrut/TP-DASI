/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Medium;
import metier.service.Services;

public class ActionRetraitFavoris extends Action{

    
    @Override
    public void executer(HttpServletRequest request) {
        //Recupération des paramètres de la Requête
        String idClString = request.getParameter("idClient");
        String idMedString = request.getParameter("idMedium");
        
        try {
            long idClient = Long.valueOf(idClString);
            long idMedium = Long.valueOf(idMedString);
            
            Services service = new Services();
            Client client = service.rechercherClient(idClient);
            Medium medium = service.obtenirMedium(idMedium);
            
            if(medium == null) {
                throw new Exception("Le medium est introuvable");
            }
            if(client == null) {
                throw new Exception("Le client est introuvable");
            }
            
            service.enleverMediumDesFavoris(medium, client);
            request.setAttribute("success",true);
            request.setAttribute("medium",medium);
            request.setAttribute("client",client);
        } catch (Exception e) {
            System.out.println(e);
            request.setAttribute("success",false);
        }
        
        //Instanciation de la classe service
        
        
    }
    
}