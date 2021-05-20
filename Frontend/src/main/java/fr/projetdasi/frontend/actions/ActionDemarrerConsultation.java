/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Consultation;
import metier.service.Services;

public class ActionDemarrerConsultation extends Action
{
    @Override
    public void executer(HttpServletRequest request) 
    {
        HttpSession session = request.getSession();
        boolean connecte = (boolean) session.getAttribute("connecte");
        
        if(!connecte) 
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Vous n'êtes pas connecté.e");
            return;
        }
        
        String idText = request.getParameter("id");
        
        long id;
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
        
        Services services = new Services();
        Consultation cons = services.obtenirConsultation(id);
        if(cons == null)
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Cette consultation n'existe pas.");
            return;
        }
        else if(cons.getEmploye().getId() != (long) session.getAttribute("id"))
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Vous n'avez pas le droit de démarrer cette consultation.");
            return;
        }
        
        cons = services.demarrerConsultation(cons);
        if(cons == null)
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Une erreur est survenue lors du démarrage de la consultation.");
            return;
        }
        
        request.setAttribute("success", true);
        request.setAttribute("message", "La consultation a bien été lancée.");
    }
}
