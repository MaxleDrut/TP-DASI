/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Utilisateur;
import metier.service.Services;

/**
 *
 * @author Marie Guillevic
 */
public class ActionDeconnexion extends Action{

    
    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        session.removeAttribute("connecte");
        session.removeAttribute("id");
        session.removeAttribute("type");
        
        //System.out.println(utilisateur);
        request.setAttribute("success",true);
    }
    
}
