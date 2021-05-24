/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.service.Services;

public class ActionRecupererClient extends Action{

    
    @Override
    public void executer(HttpServletRequest request) {
        //Recupération des paramètres de la Requête
        try {
            HttpSession session = request.getSession();
            boolean connecte = (boolean) session.getAttribute("connecte");

            if(!connecte) 
            {
                request.setAttribute("success", false);
                request.setAttribute("message", "Vous n'êtes pas connecté.e");
                return;
            }

            String idText = (String) request.getParameter("id");
            long id;

            if(idText == null)
            {
                id = (long) session.getAttribute("id");
            }
            else
            {    
                try
                {
                    id = Long.parseLong(idText);
                }
                catch(NumberFormatException e)
                {
                    request.setAttribute("success", false);
                    request.setAttribute("message", "Identifiant invalide.");
                    return;
                }
            }
            
            Services service = new Services();
            
            Client client = service.rechercherClient(id);
            if(client == null) {
                throw new Exception("Le client est introuvable");
            }
            request.setAttribute("client",client);
            request.setAttribute("success",true);
            request.setAttribute("message", "Client bien récupéré");
        } catch (Exception e) {
            System.out.println(e);
            request.setAttribute("success",false);
            request.setAttribute("message", e.getLocalizedMessage());
        }
        
        //Instanciation de la classe service
        
    }
    
}
