/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Medium;
import metier.modele.Client;
import metier.service.Services;

/**
 *
 * @author Marie Guillevic
 */
public class ActionRecupererMediumsParClient extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        Services serviceRecupererMediumsParClients= new Services();
        Map<Client, Map<Medium, Long>> mediumsParClients = serviceRecupererMediumsParClients.recupererMediumsLesPlusConsultesParClient();
       
        request.setAttribute("mediums_par_clients",mediumsParClients); 
    }
    
}
