/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Employe;
import metier.service.Services;

/**
 *
 * @author Marie Guillevic
 */
public class ActionRecupererConsultationsParEmploye extends Action{

    @Override
    public void executer(HttpServletRequest request) {
        Services serviceRecupererConsultationsEmploye= new Services();
        Map<Employe,Long> consultationsEmploye = serviceRecupererConsultationsEmploye.recupererNombreConsultationsEmploye();
        request.setAttribute("consultations_par_employe",consultationsEmploye);    
    }
    
}
