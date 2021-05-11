/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.projetdasi.frontend.servlets;

import dao.JpaUtil;
import fr.projetdasi.frontend.actions.Action;
import fr.projetdasi.frontend.actions.ActionConnexion;
import fr.projetdasi.frontend.actions.ActionInscription;
import fr.projetdasi.frontend.actions.ActionListeMedium;
import fr.projetdasi.frontend.actions.ActionRecupererClient;
import fr.projetdasi.frontend.serialisations.Serialisation;
import fr.projetdasi.frontend.serialisations.SerialisationConnexion;
import fr.projetdasi.frontend.serialisations.SerialisationInscription;
import fr.projetdasi.frontend.serialisations.SerialisationListeMedium;
import fr.projetdasi.frontend.serialisations.SerialisationRecupererClient;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String todo = request.getParameter("todo");
        
        Action action = null;
        Serialisation serialisation = null;
        
        switch(todo)
        {
            case "inscription":
            {
                action = new ActionInscription();
                serialisation = new SerialisationInscription();
                break;
            }
            case "connexion":
            {   
                action = new ActionConnexion();
                serialisation = new SerialisationConnexion();
                break;
            }

            case "lister-mediums":
            {
                action = new ActionListeMedium();
                serialisation = new SerialisationListeMedium();

                break;
            }
            
            case "recuperer-client":
            {
                action = new ActionRecupererClient();
                serialisation = new SerialisationRecupererClient();
                break;
            }
                
            default:
                // retourner page d'erreur
                break;
        }
        
        if(action != null && serialisation != null)
        {
            action.executer(request);
            serialisation.serialiser(request, response);
        }
        else
        {
            response.sendError(400, "Bad request");
        }
    }

    @Override
    public void init() throws ServletException {
      super.init();
      JpaUtil.init();
    }

    @Override
    public void destroy() {
      JpaUtil.destroy();
      super.destroy();
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
