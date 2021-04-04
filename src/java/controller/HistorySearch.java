/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.QuizDAO;
import dao.SubjectDAO;
import dto.QuizDTO;
import dto.QuizDetailDTO;
import dto.SubjectDTO;
import dto.UserDTO;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tuanv
 */
public class HistorySearch extends HttpServlet {

    private static final String SUCCESS = "history.jsp";

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
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            int index = 0;
            String subID = request.getParameter("cbsubject_his");
            if (subID != null) {
                String txtindex = request.getParameter("index");
                if (txtindex == null) {
                    index = 1;
                } else {
                    index = Integer.parseInt(txtindex);
                }
            }
            //tu dong time out
            QuizDAO quizdao = new QuizDAO();
            QuizDTO quiz = null;
            quiz = quizdao.getQuizDoing(user.getEmail());
            SubjectDAO subdao = new SubjectDAO();
            if (quiz != null) {
                SubjectDTO subdto = subdao.getSubject(quiz.getSubID());
                Timestamp h_start = new Timestamp(System.currentTimeMillis());
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(h_start.getTime());
                cal.add(Calendar.MINUTE, subdto.getTime());
                Timestamp h_end = new Timestamp(cal.getTime().getTime());
                ArrayList<QuizDetailDTO> list = new ArrayList<>();

                quizdao.updateTimeOpen(quiz.getQuizID(), h_start.toString());

                session.setAttribute("NameQuiz", subdto.getSubjectName());
                session.setAttribute("QUIZ", quiz);
                session.setAttribute("NUM_QUES", quiz.getList().size());
            }
            quizdao = new QuizDAO();
            int num_his = quizdao.getNumQuiz(user.getEmail(), subID);
            ArrayList<QuizDTO> listh = quizdao.getHistory(user.getEmail(), subID, index);
            subdao = new SubjectDAO();
            SubjectDTO sub = subdao.getSubject(subID);
            request.setAttribute("LIST_HIS", listh);
            request.setAttribute("NUM_HIS", Math.ceil((double) num_his / 5));
            request.setAttribute("NUM_QUIZ", sub.getNumofQuestion());
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
