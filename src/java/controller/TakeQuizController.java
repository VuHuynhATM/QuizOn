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
public class TakeQuizController extends HttpServlet {

    private static final String ERROR = "studenthome.jsp";
    private static final String SUCCESS = "DoQuizController";

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
            String subject = request.getParameter("subject");
            String subjectID = "";
            if (subject != null) {
                if (subject.equals("Math")) {
                    subjectID = "1";
                }
                if (subject.equals("PRJ")) {
                    subjectID = "2";
                }
                if (subject.equals("CEA")) {
                    subjectID = "3";
                }
                HttpSession session = request.getSession();
                UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
                QuizDAO quizdao = new QuizDAO();
                QuizDTO quiz = null;
                quiz = quizdao.getQuizDoing(user.getEmail());
                if (subjectID.trim().isEmpty()) {
                    subjectID = quiz.getSubID();
                }
                if (quiz != null) {
                    String action = request.getParameter("btnCotinue");
                    if (action != null) {
                        SubjectDAO subdao = new SubjectDAO();
                        SubjectDTO subdto = subdao.getSubject(subjectID);
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
                        url = SUCCESS;
                    }
                } else {
                    SubjectDAO subdao = new SubjectDAO();
                    SubjectDTO subdto = subdao.getSubject(subjectID);
                    Timestamp h_start = new Timestamp(System.currentTimeMillis());
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(h_start.getTime());
                    cal.add(Calendar.MINUTE, subdto.getTime());
                    Timestamp h_end = new Timestamp(cal.getTime().getTime());
                    ArrayList<QuizDetailDTO> list = new ArrayList<>();

                    quiz = new QuizDTO("", user.getEmail(), subjectID, h_start.toString(), h_end.toString(), h_start.toString(), 0, true, list);
                    quizdao.createQuizTake(quiz);
                    quiz = quizdao.getQuizDoing(user.getEmail());

                    session.setAttribute("NameQuiz", subdto.getSubjectName());
                    session.setAttribute("QUIZ", quiz);
                    session.setAttribute("NUM_QUES", quiz.getList().size());
                    url = SUCCESS;
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
