/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pedago
 */
@WebServlet(name = "DevineUnNombre", urlPatterns = {"/DevineUnNombre"})
public class DevineUnNombre extends HttpServlet {
    
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
        
        ServletContext app = this.getServletContext();
        HttpSession session = request.getSession();
        
        if (null == app.getAttribute("app_count_players"))
            app.setAttribute("app_count_players", 0);
        
        if (null == app.getAttribute("app_best_score")) {
            app.setAttribute("app_best_score", Integer.MAX_VALUE);
            app.setAttribute("app_best_player", "");
        }
        
        String action = request.getParameter("_action");
        
        if (action != null) {
            switch (action) {
                case "connexion":
                    String pseudo = request.getParameter("pseudo");
                    
                    if (null != pseudo && !pseudo.equals("") && null == session.getAttribute("player_name")){
                        app.setAttribute("app_count_players", (Integer)app.getAttribute("app_count_players") +1);
                        
                        session.setAttribute("player_name", pseudo);
                        session.setAttribute("player_try_last", 0);
                        session.setAttribute("player_try_count", 0);
                        session.setAttribute("player_try_message", "");
                        session.setAttribute("player_win", false);
                        
                        session.setAttribute("number", (new Random()).nextInt((100) + 1));
                    }
                    
                    break;
                case "deconnexion":
                    if (null != session.getAttribute("player_name")) {
                        session.invalidate();
                        app.setAttribute("app_count_players", (Integer)app.getAttribute("app_count_players") -1);
                    }
                    
                    break;
                case "jouer":
                    Integer number = (Integer)session.getAttribute("number");
                    
                    int proposition = 0;
                    if (Pattern.matches("[0-9]+", request.getParameter("proposition")))
                        proposition = Integer.parseInt(request.getParameter("proposition"));
                    
                    if (proposition == number) {
                        session.setAttribute("player_win", true);
                        
                        if ((Integer)session.getAttribute("player_try_count") < (Integer)app.getAttribute("app_best_score")) {
                            app.setAttribute("app_best_score", (Integer)session.getAttribute("player_try_count"));
                            app.setAttribute("app_best_player", session.getAttribute("player_name"));
                        }
                    }
                    else if (proposition < number) {
                        session.setAttribute("player_try_message", "Trop bas");
                    }
                    else {
                        session.setAttribute("player_try_message", "Trop haut");
                    }
                    
                    session.setAttribute("player_try_last", proposition);
                    session.setAttribute("player_try_count", (Integer)session.getAttribute("player_try_count") +1);
                    
                    break;
                case "rejouer":
                    session.setAttribute("player_try_last", 0);
                    session.setAttribute("player_try_count", 0);
                    session.setAttribute("player_try_message", "");
                    session.setAttribute("player_win", false);
                    
                    session.setAttribute("number", (new Random()).nextInt((100) + 1));
                    
                    break;
                default:
                    break;
            }
        }
        
        request.getRequestDispatcher("view/index.jsp").forward(request, response);
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
