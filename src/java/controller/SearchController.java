/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.QuestionDAO;
import dto.QuestionDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tuanv
 */
public class SearchController extends HttpServlet {

    private static final String SUCCESS = "adminhome.jsp";

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
        String url = SUCCESS;
        try {
            String strindex = request.getParameter("index");
            int index = 0;
            if (strindex != null) {
                index = Integer.parseInt(strindex);
            } else {
                index = 1;
            }
            HttpSession session = request.getSession();
            session.setAttribute("index", index);
            QuestionDAO quesdao = new QuestionDAO();
            HashMap<String,ArrayList<QuestionDTO>> hash=new HashMap<>();
            index = (int) session.getAttribute("index");
            int numpage = 0;
            String action = request.getParameter("rdsearch");
            if (action == null || action.isEmpty()) {
                action = (String) session.getAttribute("METHOD_SEARCH");
            }
            if (action != null) {
                if ("SearchBySubject".equals(action)) {
                    String subID = request.getParameter("cbsubject");
                    if (subID == null) {
                        subID = (String) session.getAttribute("SUB_SEARCH");
                    }
                    if (subID != null) {
                        hash = quesdao.getQuestionBySub(index, subID);
                        numpage = quesdao.getNumPageofSub(subID);
                        session.setAttribute("SUB_SEARCH", subID);
                    }
                    session.setAttribute("NAME_SEARCH", "");
                    session.setAttribute("STATUS_SEARCH", "true");
                }
                if ("SearchByStatus".equals(action)) {
                    String txtstatus = request.getParameter("cbstatus");
                    if (txtstatus == null) {
                        txtstatus = (String) session.getAttribute("STATUS_SEARCH");
                    }
                    if (txtstatus != null) {
                        boolean status = Boolean.parseBoolean(txtstatus);
                        hash=quesdao.getQuestionByStatus(index, status);
                        numpage =quesdao.getNumPageofStatus(status);
                        session.setAttribute("STATUS_SEARCH", txtstatus);
                    }
                    session.setAttribute("NAME_SEARCH", "");
                    session.setAttribute("SUB_SEARCH", "1");
                }
                if ("SearchByQuestion".equals(action)) {
                    String question_content=request.getParameter("txtsearch");
                    if(question_content==null){
                        question_content=(String) session.getAttribute("NAME_SEARCH");
                    }
                    if(question_content!=null){
                        hash=quesdao.getQuestionByName(index, question_content);
                        numpage=quesdao.getNumPageofName(question_content);
                        session.setAttribute("NAME_SEARCH", question_content);
                    }
                    session.setAttribute("STATUS_SEARCH", "true");
                    session.setAttribute("SUB_SEARCH", "1");
                }
            }
            session.setAttribute("HASH_QUESTION", hash);
            session.setAttribute("METHOD_SEARCH", action);
            session.setAttribute("NUM_PAGE", numpage);
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
