package controller;

import exception.AbstractException;
import exception.AccessDeniedException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.FlashBag;
import service.ServiceContainer;


@WebServlet(name = "AdminController", urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {
    
    public final static String USERNAME = "root";
    public final static String PASSWORD = "toor";
    
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
        
        HttpSession session = request.getSession();
        
        // Service container
        ServiceContainer serviceContainer = (ServiceContainer)session.getAttribute("serviceContainer");
        
        // FlashBag
        FlashBag flashBag = serviceContainer.getFlashBag();
        
        // Admin
        Boolean isAdmin = (null != session.getAttribute("isAdmin")) ? (Boolean)session.getAttribute("isAdmin") : false;
        
        try {
            if (true != isAdmin)
                throw new AccessDeniedException("AdminController: You must be logged as Admin to access to this page.");
            
            request.getRequestDispatcher("/WEB-INF/template/admin/home.jsp").forward(request, response);
            
        } catch (AbstractException e) {
            
            session.setAttribute("error", e.getMessage());
            
            request.getRequestDispatcher("/WEB-INF/template/error.jsp").forward(request, response);
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
