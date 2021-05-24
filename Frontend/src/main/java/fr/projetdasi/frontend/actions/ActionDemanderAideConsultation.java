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
import metier.service.Services;

public class ActionDemanderAideConsultation extends Action 
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
        
        String idClientText = request.getParameter("client");
        String amourText = request.getParameter("amour");
        String santeText = request.getParameter("sante");
        String travailText = request.getParameter("travail");
        
        int amour, sante, travail;
        long idClient;
        
        try
        {
            amour = Integer.parseInt(amourText);
            sante = Integer.parseInt(santeText);
            travail = Integer.parseInt(travailText);
            
            idClient = Long.parseLong(idClientText);
        }
        catch(NumberFormatException e)
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Impossible de récupérer l'un des arguments : amour, sante, travail, client.");
            return;
        }
        
        Services services = new Services();
        Client client = services.rechercherClient(idClient);
        if(client == null)
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Le client spécifié n'existe pas.");
            return;
        }
        
        List<String> predictions = services.demanderAideConsultation(client, amour, sante, travail);
        
        if(predictions == null)
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Impossible de récupérer les prédictions auprès du service.");
            return;
        }
        
        request.setAttribute("amour", predictions.get(0));
        request.setAttribute("sante", predictions.get(1));
        request.setAttribute("travail", predictions.get(2));
        request.setAttribute("success", true);
        request.setAttribute("message", "Prédictions récupérées !");
    }
}
