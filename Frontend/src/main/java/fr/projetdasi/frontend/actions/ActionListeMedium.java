/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
        
        String idString = request.getParameter("idClient");
        
        //Si le client est renseigné, recherche aussi les mediums favoris du client
        if(idString != null) {
            long id;
            try {
                id = Long.valueOf(idString);
            } catch (Exception e) {
                System.out.println(e);
                id = 0;
            }
            
            Client client = services.rechercherClient(id);
            List<Medium> favoris = client.getMediumsFavoris();
            request.setAttribute("favoris",favoris);
        } else {
            request.setAttribute("favoris",null);
        }
        
    }
}
