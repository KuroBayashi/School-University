package simplejdbcservlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DAOException;
import simplejdbc.DataSourceFactory;

/**
 *
 * 
 * @author pedago
 */
public class ShowClientInState extends HttpServlet {

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
        try (
            PrintWriter out = response.getWriter();
        ) {
            // Load DAO
            DAOExtends daoE = new DAOExtends(DataSourceFactory.getDataSource());

            // Init vars
            String state = request.getParameter("state");
            List<CustomerEntity> customerList = null;
            List<String> states =  daoE.getStates();
            String selected;
            
            // Load customers
            if (state != null) {
                try {
                    customerList = daoE.customersInState(state);
                } catch (DAOException e) {
                    Logger.getLogger("DAO").log(Level.SEVERE, null, e);
                }
            }
            
            // Generate HTML
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
                out.println("<title>Servlet ShowClientInState</title>");
                out.println("<style>");
                    out.println("body { font-family: Monospace; font-size: 14px; }");
                    out.println("form { margin: 20px 0; }");
                    out.println("table { border-collapse: collapse; }");
                    out.println("thead { font-size: 16px; font-weight: 600; background: #ef1564; }");
                    out.println("tr { border: 1px solid #000; }");
                    out.println("td { padding: 4px 10px 2px 10px;}");
                    out.println(".select-box { width: 100px; height: 20px; border: none; padding-left: 15px; background: #ef1564;}");
                    out.println(".warning-box { padding: 10px; background: #efb65b; }");
                    out.println(".warning-box::before { content: 'Warning: '; }");
                out.println("</style>");
            out.println("</head>");
            out.println("<body>");
                out.println("<h1>Servlet ShowClientInState at " + request.getContextPath() + "</h1>");

                out.println("<form method='get' action=''>");
                    out.println("<label for='state'> State select: </label>");
                    out.println("<select name='state' class='select-box js-select-state'> id='state'");
                        for (String stateName : states) {
                            selected = stateName.equals(state) ? "selected" : "";
                            out.println("<option value='"+ stateName +"' "+ selected +">"+ stateName +"</option>");
                        }
                    out.println("</select>");
                    out.println("<input type='submit' value='Valider' class='js-hide'>");
                out.println("</form>");
                
                if (customerList != null) {
                    if (!customerList.isEmpty()) {
                        out.println("<table>");
                            out.println("<thead>");
                                out.println("<tr>");
                                    out.println("<td>Id</td>");
                                    out.println("<td>Name</td>");
                                    out.println("<td>Address</td>");
                                out.println("</tr>");
                            out.println("</thead>");
                            out.println("<tbody>");
                                for (CustomerEntity customer : customerList) {
                                    out.println("<tr>");
                                        out.println("<td>"+ customer.getCustomerId() +"</td>");
                                        out.println("<td>"+ customer.getName() + "</td>");
                                        out.println("<td>"+ customer.getAddressLine1() +"</td>");
                                    out.println("</tr>");
                                }
                            out.println("</tbody>");
                        out.println("</table>");
                    }
                    else {
                        out.println("<p class='warning-box'>Selected state does not exists.</p>");
                    }
                }

                out.println("<script>");
                out.println("let select = document.querySelector('.js-select-state');");
                out.println("let url = window.location.href.split('?')[0];");
                out.println("select.onchange = function() { window.location.replace(url + '?state=' + select.value) };");
                out.println("</script>");

                out.println("<script>");
                out.println("document.querySelector('.js-hide').style.display = 'none';");
                out.println("</script>");
            out.println("</body>");
            out.println("</html>"); 
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
