/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.service.Services;

public class ActionInscription extends Action 
{
    @Override
    public void executer(HttpServletRequest request) 
    {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String address = request.getParameter("address");
        String birthdate = request.getParameter("birthdate");
        String email = request.getParameter("email");
        String telephone = request.getParameter("phone");
        String password = request.getParameter("password");
        String cgu_check = request.getParameter("cgu-check");
        
        System.out.println(birthdate);
        
        if(cgu_check == null || !cgu_check.equalsIgnoreCase("on"))
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Vous devez accepter les conditions générales d'utilisation");
            return;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birth;
        try
        {
            birth = sdf.parse(birthdate);
        }
        catch(ParseException ex)
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Format de date invalide !");
            return;
        }
        
        Services services = new Services();
        Client client = new Client(lastname, firstname, address, telephone, birth, email, password);
        client = services.inscrireClient(client);
        
        if(client == null)
        {
            request.setAttribute("success", false);
            request.setAttribute("message", "Une erreur est survenue lors de l'inscription");
            return;
        }
        
        request.setAttribute("success", true);
        request.setAttribute("client", client);
    }
}
