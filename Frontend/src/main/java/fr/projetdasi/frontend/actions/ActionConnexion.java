/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Utilisateur;
import metier.service.Services;

/**
 *
 * @author Marie Guillevic
 */
public class ActionConnexion implements Action{

    @Override
    public void executer(HttpServletRequest request, HttpServletResponse response) {
        
        //Recupération des paramètres de la Requête
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        
        System.out.println(" login :" + login );
        System.out.println(" password :" + password );
        
        //Instanciation de la classe service
        Services serviceConnexion = new Services();
        
        Utilisateur utilisateur = serviceConnexion.authentification(login, password);
                
        
    }
    
}
