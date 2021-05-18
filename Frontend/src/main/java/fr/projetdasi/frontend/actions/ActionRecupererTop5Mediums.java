/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Medium;
import metier.service.Services;

/**
 *
 * @author Marie Guillevic
 */
public class ActionRecupererTop5Mediums extends Action{

    @Override
    public void executer(HttpServletRequest request) {
        Services serviceRecupererTop5 = new Services();
        Map<Medium,Long> top5 = serviceRecupererTop5.recupererTop5Mediums();
        request.setAttribute("top5Mediums",top5);        
    }
    
}
