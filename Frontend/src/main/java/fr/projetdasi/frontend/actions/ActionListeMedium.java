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
import metier.modele.Medium;
import metier.service.Services;

public class ActionListeMedium extends Action 
{
    @Override
    public void executer(HttpServletRequest request)
    {
        
        Services services = new Services();
        List<Medium> mediums = services.obtenirListeMediums();
        
        request.setAttribute("mediums",mediums);
        
        HttpSession session = request.getSession();

        
        //Si le client est renseigné, recherche aussi les mediums favoris du client
        Object connecte = session.getAttribute("connecte");
        
        if(connecte != null) {
            String type = (String) (session.getAttribute("type"));
            if(type.equals("client")) {
                long id = (long)session.getAttribute("id");
               Client client = services.rechercherClient(id);
               List<Medium> favoris = client.getMediumsFavoris();
               request.setAttribute("favoris",favoris);
               request.setAttribute("idClient",id);
           } else {
               request.setAttribute("favoris",null);
            }   
        }
    }
}
