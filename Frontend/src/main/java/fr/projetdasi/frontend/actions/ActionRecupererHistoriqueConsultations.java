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
import metier.modele.Employe;
import metier.modele.Medium;
import metier.service.Services;

public class ActionRecupererHistoriqueConsultations extends Action 
{
    @Override
    public void executer(HttpServletRequest request) 
    {
        String type = request.getParameter("type"); // client, employe
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
        
        switch(type)
        {
            case "client":
            {
                Client client = services.rechercherClient(id);
                if(client == null)
                {
                    request.setAttribute("success", false);
                    request.setAttribute("message", "Ce client n'existe pas.");
                    return;
                }
                
                List<Consultation> consultations = services.recupererConsultationsClient(client);
                request.setAttribute("consultations", consultations);
                break;
            }
            case "employe":
            {
                Employe employe = services.rechercherEmploye(id);
                if(employe == null)
                {
                    request.setAttribute("success", false);
                    request.setAttribute("message", "Cet employé n'existe pas.");
                    return;
                }
                
                List<Consultation> consultations = services.recupererConsultationsEmploye(employe);
                request.setAttribute("consultations", consultations);
                break;
            }
            default:
                request.setAttribute("success", false);
                request.setAttribute("message", "Action non supportée.");
                return;
        }
        
        if(request.getAttribute("consultations") == null)
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Impossible de récupérer les consultations.");
        }
        else
        {
            request.setAttribute("success", true);
            request.setAttribute("message", "Consultations récupérées !");
        }
    }
}
