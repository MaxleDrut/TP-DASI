/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import javax.servlet.http.HttpServletRequest;
import metier.modele.Consultation;
import metier.service.Services;

public class ActionTerminerConsultation extends Action
{
    @Override
    public void executer(HttpServletRequest request) 
    {
        String idText = request.getParameter("id");
        String commentaire = request.getParameter("commentaire");
        
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
        }
        
        cons = services.terminerConsultation(cons, commentaire);
        if(cons == null)
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Une erreur est survenue lors de la terminaison de la consultation.");
        }
        
        request.setAttribute("success", true);
        request.setAttribute("message", "Consultation bien terminée !");
        request.setAttribute("consultation", cons);
    }
}
