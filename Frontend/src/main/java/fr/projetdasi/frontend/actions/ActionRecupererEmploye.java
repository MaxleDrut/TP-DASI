/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Employe;
import metier.service.Services;

public class ActionRecupererEmploye extends Action
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
        
        String idText = (String) request.getParameter("id");
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
        Employe employe = services.rechercherEmploye(id);
        
        if(employe == null)
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Cet employé n'existe pas.");
            return;
        }
        
        request.setAttribute("success", true);
        request.setAttribute("message", "Employé récupéré !");
        request.setAttribute("employe", employe);
    }
}
