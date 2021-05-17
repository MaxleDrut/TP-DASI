/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Utilisateur;
import metier.service.Services;

public class ActionConsultationAssignee extends Action
{
    @Override
    public void executer(HttpServletRequest request) 
    {
        String idText = request.getParameter("id");
        long userId;
        
        try
        {
            userId = Long.parseLong(idText);
        }
        catch(NumberFormatException e)
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Identifiant invalide.");
            return;
        }
        
        Services services = new Services();
        Utilisateur utilisateur = services.rechercherUtilisateur(userId);
        Consultation cons = null;
        
        if(utilisateur instanceof Client)
        {
            cons = services.obtenirConsultationEnCoursClient((Client) utilisateur);
        }
        else if(utilisateur instanceof Employe)
        {
            cons = services.obtenirConsultationAssignee((Employe) utilisateur);
        }
        
        request.setAttribute("consultation", cons);
        request.setAttribute("success", true);
        request.setAttribute("message", "Consultation récupérée");
    }
}
