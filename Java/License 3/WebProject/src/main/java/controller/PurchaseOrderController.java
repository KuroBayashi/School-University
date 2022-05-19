package controller;

import entity.Customer;
import entity.Product;
import entity.PurchaseOrder;
import exception.AbstractException;
import exception.AccessDeniedException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import repository.PurchaseOrderRepository;
import repository.QueryParameter;
import exception.RepositoryException;
import java.sql.Date;
import java.util.Calendar;
import repository.ProductRepository;
import repository.RepositoryFactory;
import service.FlashBag;
import service.ServiceContainer;


@WebServlet(name = "PurchaseOrderController", urlPatterns = {"/purchaseOrder"})
public class PurchaseOrderController extends HttpServlet {

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
        
        // Customer
        Customer customer = (Customer)session.getAttribute("customer");
        
        // Action
        String action = request.getParameter("_action");
        
        try {
            if (null == customer || -1 == customer.getId())
                throw new AccessDeniedException("PurchaseOrderController: You must be logged to access to this page.");
            
            PurchaseOrderRepository purchaseOrderRepository = RepositoryFactory.getPurchaseOrderRepository();
            ProductRepository productRepository = RepositoryFactory.getProductRepository();

            if (null != action) {
                // Create
                if ("create".equals(action)) {
                    try {
                        Product product = productRepository.findOneWith(Arrays.asList(
                            new QueryParameter("product_id", Integer.parseInt(request.getParameter("product_id")))
                        ));
                        
                        if (null == product)
                            flashBag.add("danger", "Unknown product, can't purchase it.");
                        else {
                            Calendar calendar = Calendar.getInstance();

                            PurchaseOrder purchaseOrder = new PurchaseOrder()
                                .setCustomer(customer)
                                .setProduct(product)
                                .setQuantity(1)
                                .setSalesDate(new Date(calendar.getTimeInMillis()))
                                .setShippingCost(product.getPurchaseCost())
                                .setFreightCompany("UPS")
                            ;

                            calendar.add(Calendar.DAY_OF_YEAR, 7);
                            purchaseOrder.setShippingDate(new Date(calendar.getTimeInMillis()));
                            
                            purchaseOrderRepository.save(purchaseOrder);
                            
                            flashBag.add("success", "Your purchase order has been successfully created.");
                        }
                        
                    } catch (NumberFormatException e) {
                        flashBag.add("danger", "Your purchase order num must be an integer.");
                    } catch (RepositoryException e) {
                        flashBag.add("danger", e.getMessage());
                    }
                }
                
                // Edit
                else if ("edit".equals(action)) { 

                    String shippingDate = request.getParameter("shipping_date");
                    String quantity     = request.getParameter("quantity");

                    // TODO : Check shipping date > tomorrow
                    if (null == shippingDate || !shippingDate.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$"))
                        flashBag.add("danger", "Invalid shipping date (yyyy-mm-dd)");
                    else if (null == quantity || !quantity.matches("^[1-9]+[0-9]*$"))
                        flashBag.add("danger", "Quantity must be greater than 0.");
                    else {
                        try {
                            PurchaseOrder purchaseOrder = purchaseOrderRepository.findOneWith(Arrays.asList(
                                new QueryParameter("c.customer_id", customer.getId()),
                                new QueryParameter("order_num", Integer.parseInt(request.getParameter("order_num")))
                            ));

                            if (null == purchaseOrder)
                                flashBag.add("danger", "Unknown purchase order, can't edit it.");
                            else {
                                purchaseOrder
                                    .setQuantity(Integer.parseInt(quantity))
                                    .setShippingDate(Date.valueOf(shippingDate))
                                    .setFreightCompany(request.getParameter("freight_company"))
                                ;

                                purchaseOrderRepository.save(purchaseOrder);

                                flashBag.add("success", "You purchase order has been successfully updated.");
                            }
                        } catch (NumberFormatException e) {
                            flashBag.add("danger", "Your purchase order num must be an integer.");
                            flashBag.add("danger", "Your purchase order quantity must be an integer.");
                        } catch (RepositoryException e) {
                            flashBag.add("danger", e.getMessage());
                        }
                    }
                }

                // Delete 
                else if ("delete".equals(action)) {
                    try {
                        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOneWith(Arrays.asList(
                            new QueryParameter("c.customer_id", customer.getId()),
                            new QueryParameter("order_num", Integer.parseInt(request.getParameter("order_num")))
                        ));

                        if (null == purchaseOrder)
                            flashBag.add("danger", "Unknown purchase order, can't delete it.");
                        else {
                            purchaseOrderRepository.delete(purchaseOrder);

                            flashBag.add("success", "Your purchase order has been successfully deleted.");
                        }
                    } catch (NumberFormatException e) {
                        flashBag.add("danger", "Your purchase order num must be an integer.");
                    } catch (RepositoryException e) {
                        flashBag.add("danger", e.getMessage());
                    }
                }
            }
            
            // Default Home
            request.setAttribute("purchaseOrders", purchaseOrderRepository.findAllWith(Arrays.asList(
                new QueryParameter("c.customer_id", customer.getId())
            )));

            request.getRequestDispatcher("/WEB-INF/template/purchaseOrder/home.jsp").forward(request, response);
            
        } catch (SQLException|AbstractException e) {
            request.setAttribute("error", e.getMessage());
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
