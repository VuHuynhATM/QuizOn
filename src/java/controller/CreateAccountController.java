/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tuanv
 */
public class CreateAccountController extends HttpServlet {

    private static final String ERROR = "create.jsp";
    private static final String SUCCESS = "login.jsp";

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
        String url = ERROR;
        try {
            String email = request.getParameter("txtemail");
            String name = request.getParameter("txtname");
            String password = request.getParameter("txtpassword");
            String comfirm = request.getParameter("txtconfirm");
            boolean check = true;
            if (email != null) {
                if (email.trim().isEmpty() || email.trim().length() > 50) {
                    if (email.trim().isEmpty()) {
                        request.setAttribute("EMAILERROR", "email not empty !!!!!");
                    } else {
                        request.setAttribute("EMAILERROR", "email <50 character !!!!!");
                    }
                    check = false;
                }
            } else {
                check = false;
            }
            if (name != null) {
                if (name.trim().isEmpty() || name.trim().length() > 50) {
                    if (name.trim().isEmpty()) {
                        request.setAttribute("NAMEERROR", "name not empty !!!!!");
                    } else {
                        request.setAttribute("NAMEERROR", "name <50 character !!!!!");
                    }
                    check = false;
                }
            } else {
                check = false;
            }
            if (password != null) {
                if (password.trim().isEmpty()) {
                    request.setAttribute("PASSWORDERROR", "password <50 character !!!!!");
                    check = false;
                }
            }
            if (comfirm != null) {
                if (comfirm.trim().isEmpty() && comfirm.equals(password)) {
                    request.setAttribute("COMFIRMERROR", "not same password !!!!!");
                    check = false;
                }
            } else {
                check = false;
            }
            if (check) {
                UserDTO user = new UserDTO(email, name, password, "", "");
                UserDAO dao = new UserDAO();
                dao.createAccount(user);
                request.setAttribute("MESS", "Create account successfull");
                url = SUCCESS;
            }
        } catch (Exception e) {
            log(e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
