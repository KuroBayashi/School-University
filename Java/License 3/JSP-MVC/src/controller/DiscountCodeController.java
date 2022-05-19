/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOException;
import dao.DataSourceFactory;
import dao.DiscountCodeDAO;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DiscountCode;

/**
 *
 * @author pedago
 */
@WebServlet(name = "DiscountCodeController", urlPatterns = {"/DiscountCodeController"})
public class DiscountCodeController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            List<DiscountCode> discountCodeList = new LinkedList<>();
            List<String> flashMessages = new LinkedList<>();
            
            DiscountCodeDAO dao = new DiscountCodeDAO(DataSourceFactory.getDataSource());
            
            String action = request.getParameter("_action");
            String code = request.getParameter("code");
            String taux = request.getParameter("taux");
            
            // ACTION
            try {
                if (action != null) {
                    if (action.equals("delete")) {
                        if (code == null)
                            throw new Exception("Invalid code.");
                                
                        dao.delete(code);
                        
                        flashMessages.add("Le code a été supprimé.");
                    }
                    else if (action.equals("add")) {
                        float t = Float.parseFloat(taux);

                        dao.save(new DiscountCode(code, t));
                        
                        flashMessages.add("Le code a été ajouté.");
                    }
                }
            }
            catch (DAOException e) {
                flashMessages.add("DAOException: " + e.getMessage());
            }
            catch(Exception e) {
                flashMessages.add("Exception: " + e.getMessage());
            }
            
            // SHOW
            try {
                discountCodeList = dao.findAll();
            }
            catch (DAOException e) {
                flashMessages.add("DAOException: " + e.getMessage());
            }
            
            request.setAttribute("flashMessages", flashMessages);
            request.setAttribute("discountCodes", discountCodeList);
            request.getRequestDispatcher("views/discountCode.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
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
