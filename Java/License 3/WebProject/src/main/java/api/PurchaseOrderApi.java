package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Calendar;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository.PurchaseOrderRepository;
import exception.RepositoryException;
import java.sql.SQLException;
import repository.RepositoryFactory;


@WebServlet(name = "PurchaseOrderApi", urlPatterns = {"/api/purchaseOrder"})
public class PurchaseOrderApi extends HttpServlet {

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

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Properties resultat = new Properties();
        
        String dataType = request.getParameter("data_type");
        String dateStart = request.getParameter("date_start");
        String dateEnd = request.getParameter("date_end");
        
        Date dateStartSql, dateEndSql;
        try {
            dateStartSql = Date.valueOf(dateStart);
        }
        catch (IllegalArgumentException e) {
            dateStartSql = Date.valueOf("1970-01-01");
        }
        try {
            dateEndSql = Date.valueOf(dateEnd);
        }
        catch (IllegalArgumentException e) {
            dateEndSql = new Date(Calendar.getInstance().getTimeInMillis());
        }
        
        try {
            PurchaseOrderRepository purchaseOrderRepository = RepositoryFactory.getPurchaseOrderRepository();
            
            if (null == dataType || "categories".equals(dataType))
                resultat.put("records", purchaseOrderRepository.findAllGroupByProductCode(dateStartSql, dateEndSql));
            else if ("customers".equals(dataType)) 
                resultat.put("records", purchaseOrderRepository.findAllGroupByCustomer(dateStartSql, dateEndSql));
            else if ("locations".equals(dataType)) 
                resultat.put("records", purchaseOrderRepository.findAllGroupByLocation(dateStartSql, dateEndSql));
        }
        catch (SQLException|RepositoryException e) {
            response.setStatus(401);
            resultat.put("message", e.getMessage());
        }
        
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            out.println(gson.toJson(resultat));
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
