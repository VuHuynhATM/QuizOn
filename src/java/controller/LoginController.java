/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.QuizDAO;
import dao.SubjectDAO;
import dao.UserDAO;
import dto.QuizDTO;
import dto.SubjectDTO;
import dto.UserDTO;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tuanv
 */
public class LoginController extends HttpServlet {

    private static final String SUCCESS = "studenthome.jsp";
    private static final String SUCCESSADMIN = "SearchController";
    private static final String ERROR = "login.jsp";

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
            String password = request.getParameter("txtpassword");
            boolean check = true;
            boolean checkaccount = false;
            if (email != null) {
                if (email.trim().isEmpty()) {
                    request.setAttribute("EMAILERROR", "Email not empty");
                    check = false;
                    checkaccount = false;
                } else {
                    checkaccount = true;
                }
            } else {
                check = false;
            }
            if (password != null) {
                if (password.trim().isEmpty()) {
                    request.setAttribute("PASSWORDERROR", "password not empty");
                    check = false;
                    checkaccount = false;
                } else {
                    checkaccount = true;
                }
            } else {
                check = false;
            }
            if (check) {
                UserDAO dao = new UserDAO();
                UserDTO user = new UserDTO();
                user = dao.checkLogin(email, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("LOGIN_USER", user);
                    if (user.getRole().equals("ADMIN")) {
                        url = SUCCESSADMIN;
                    } else {
                        Timestamp h_start = new Timestamp(System.currentTimeMillis());
                        QuizDAO quizdao=new QuizDAO();
                        QuizDTO quiz=quizdao.getQuizDoing(email);
                        if(quiz!=null){
                            quizdao.submitQuizByTimeOut(email, quiz.getQuizID(), h_start.toString());
                            quiz=quizdao.getQuizDoing(email);
                            if(quiz!=null){
                                SubjectDAO subdao=new SubjectDAO();
                                SubjectDTO sub=subdao.getSubject(quiz.getSubID());
                                session.setAttribute("HAS_QUIZ", quiz);
                                session.setAttribute("HAS_SUB", sub);
                            }
                        }else
                        url = SUCCESS;
                    }
                } else {
                    if (checkaccount) {
                        request.setAttribute("LOGINERROR", "Wrong password Or email");
                    }
                }
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
