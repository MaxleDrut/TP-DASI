/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Medium;
import metier.service.Services;

public class ActionListeMedium implements Action 
{
    @Override
    public void executer(HttpServletRequest request, HttpServletResponse response) 
    {
        Services services = new Services();
        List<Medium> mediums = services.obtenirListeMediums();
        request.setAttribute("mediums",mediums);
    }
}
