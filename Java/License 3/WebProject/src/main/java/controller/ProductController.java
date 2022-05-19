package controller;

import entity.Customer;
import entity.Manufacturer;
import entity.Product;
import entity.ProductCode;
import exception.AbstractException;
import exception.AccessDeniedException;
import exception.RepositoryException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import repository.ManufacturerRepository;
import repository.ProductCodeRepository;
import repository.ProductRepository;
import repository.QueryParameter;
import repository.RepositoryFactory;
import service.FlashBag;
import service.ServiceContainer;


@WebServlet(name = "ProductController", urlPatterns = {"/product"})
public class ProductController extends HttpServlet {

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
        
        // Admin
        Boolean isAdmin = (null != session.getAttribute("isAdmin")) ? (Boolean)session.getAttribute("isAdmin") : false;
        
        // Action
        String action = request.getParameter("_action");
        
        try {
            ProductRepository productRepository = RepositoryFactory.getProductRepository();
            ManufacturerRepository manufacturerRepository = RepositoryFactory.getManufacturerRepository();
            ProductCodeRepository productCodeRepository = RepositoryFactory.getProductCodeRepository();

            if (null != action) {
                if (null == customer && false == isAdmin)
                    throw new AccessDeniedException("PurchaseOrderController: You must be logged to access to this page.");
                
                // Create
                if ("create".equals(action)) {
                    try {
                        Product product = new Product();
                        
                        if (null != request.getParameter("submit")) {
                            Manufacturer manufacturer = manufacturerRepository.findOneWith(Arrays.asList(
                                new QueryParameter("m.manufacturer_id", Integer.parseInt(request.getParameter("manufacturer_id")))
                            ));

                            ProductCode productCode = productCodeRepository.findOneWith(Arrays.asList(
                                new QueryParameter("pc.prod_code", request.getParameter("product_code"))
                            ));

                            product
                                .setManufacturer(manufacturer)
                                .setCode(productCode)
                                .setPurchaseCost(Float.parseFloat(request.getParameter("purchase_cost")))
                                .setQuantity(Integer.parseInt(request.getParameter("quantity")))
                                .setMarkup(Float.parseFloat(request.getParameter("markup")))
                                .setAvailable(true)
                                .setDescription(request.getParameter("description"))
                            ;
                            
                            if (null == manufacturer)
                                flashBag.add("danger", "Unknown manufacturer.");
                            else if (null == productCode)
                                flashBag.add("danger", "Unknown product code.");
                            else {
                                productRepository.save(product);
                                
                                flashBag.add("success", "You product has been successfully created.");
                                
                                response.sendRedirect(request.getContextPath() + "/product");
                                return;
                            }
                        }
                        
                        request.setAttribute("manufacturers", manufacturerRepository.findAll());
                        request.setAttribute("productCodes", productCodeRepository.findAll());
                        request.setAttribute("product", product);
                        request.getRequestDispatcher("/WEB-INF/template/product/create.jsp").forward(request, response);
                        return;
                        
                    } catch (NumberFormatException e) {
                        flashBag.add("danger", "Manufacturer id must be an integer.");
                        flashBag.add("danger", "Purchase cost must be a float.");
                        flashBag.add("danger", "Quantity must be an integer.");
                        flashBag.add("danger", "Markup must be a float.");
                    } catch (RepositoryException e) {
                        flashBag.add("danger", e.getMessage());
                    }
                }
                
                // Edit
                else if ("edit".equals(action)) {
                    try {
                        // Form has been submitted
                        if (null != request.getParameter("submit")) {
                            
                            Product product = productRepository.findOneWith(Arrays.asList(
                                new QueryParameter("p.product_id", Integer.parseInt(request.getParameter("product_id")))
                            ));
                            
                            Manufacturer manufacturer = manufacturerRepository.findOneWith(Arrays.asList(
                                new QueryParameter("m.manufacturer_id", Integer.parseInt(request.getParameter("manufacturer_id")))
                            ));
                            
                            ProductCode productCode = productCodeRepository.findOneWith(Arrays.asList(
                                new QueryParameter("pc.prod_code", request.getParameter("code"))
                            ));
                            
                            product
                                .setManufacturer(manufacturer)
                                .setCode(productCode)
                                .setQuantity(Integer.parseInt(request.getParameter("quantity")))
                                .setPurchaseCost(Float.parseFloat(request.getParameter("purchase_cost")))
                                .setMarkup(Float.parseFloat(request.getParameter("markup")))
                            ;
                            
                            productRepository.save(product);
                            flashBag.add("success", "The product has been successfully updated.");
                        }

                        request.setAttribute("manufacturers", manufacturerRepository.findAll());
                        request.setAttribute("productCodes", productCodeRepository.findAll());
                        request.setAttribute("products", productRepository.findAllWith(Arrays.asList(
                            new QueryParameter("available", "TRUE")
                        )));
                        request.getRequestDispatcher("/WEB-INF/template/product/edit.jsp").forward(request, response);
                        return;
                    
                    } catch (NumberFormatException e) {
                        flashBag.add("danger", "Product id must be an integer.");
                    } catch (RepositoryException e) {
                        flashBag.add("danger", e.getMessage());
                    }
                }
                
                // Edit
                else if ("delete".equals(action)) {
                    try {
                        Product product = productRepository.findOneWith(Arrays.asList(
                            new QueryParameter("product_id", Integer.parseInt(request.getParameter("product_id")))
                        ));

                        if (null == product)
                            flashBag.add("danger", "Unknown product, can't delete it.");
                        else {
                            productRepository.delete(product);

                            flashBag.add("success", "The product has been successfully deleted.");
                        }
                        
                        request.setAttribute("manufacturers", manufacturerRepository.findAll());
                        request.setAttribute("productCodes", productCodeRepository.findAll());
                        request.setAttribute("products", productRepository.findAllWith(Arrays.asList(
                            new QueryParameter("available", "TRUE")
                        )));
                        request.getRequestDispatcher("/WEB-INF/template/product/edit.jsp").forward(request, response);
                        return;
                        
                    } catch (NumberFormatException e) {
                        flashBag.add("danger", "Product id must be an integer.");
                    } catch (RepositoryException e) {
                        flashBag.add("danger", e.getMessage());
                    }
                }
            }
            
            // Default Home
            request.setAttribute("products", productRepository.findAllWith(Arrays.asList(
                new QueryParameter("available", "TRUE")
            )));
            
            request.getRequestDispatcher("/WEB-INF/template/product/home.jsp").forward(request, response);
            
        } catch (SQLException | AbstractException e) {  
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
