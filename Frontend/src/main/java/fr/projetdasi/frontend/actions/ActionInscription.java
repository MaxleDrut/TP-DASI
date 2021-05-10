/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionInscription implements Action 
{
    @Override
    public void executer(HttpServletRequest request) 
    {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String address = request.getParameter("address");
        String birthdate = request.getParameter("birthdate");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String promotions_check = request.getParameter("promotions-check");
        String cgu_check = request.getParameter("cgu-check");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
