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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tuanv
 */
public class DoQuizController extends HttpServlet {

    private static final String SUCCESS = "quiz.jsp";
    private static final String ERROR = "studenthome.jsp";
    private static final String SUBMIT = "studenthome.jsp";

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
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            QuizDTO quiz = (QuizDTO) session.getAttribute("QUIZ");
            ArrayList<QuizDetailDTO> list = quiz.getList();
            int index = 0;
            if (session.getAttribute("indexquiz") == null) {
                index = 1;
            } else {
                index = (int) session.getAttribute("indexquiz");
            }
            String action = request.getParameter("btnaction");
            Timestamp h_start = new Timestamp(System.currentTimeMillis());
            if ("Submit".equals(action)) {
                String yourchoice = request.getParameter("rdans");
                if (yourchoice == null) {
                    yourchoice = "0";
                }
                QuizDAO quizdao = new QuizDAO();
                QuizDetailDTO quizDetail = list.get(index - 1);
                quizdao.updateQuiz_Detail(quizDetail.getQuizDeatlID(), Integer.parseInt(yourchoice));
                int total = quizdao.getNumAnswerCorrect(quiz.getQuizID());
                quizdao.updateQuiz_Take(quiz.getQuizID(), total);
                quizdao.submitQuiz(user.getEmail(), quiz.getQuizID());
                quizdao.updateTimeOpen(quiz.getQuizID(), h_start.toString());
                session.setAttribute("indexquiz", null);
                session.setAttribute("QUIZ", null);
                session.setAttribute("HAS_QUIZ", null);
                session.setAttribute("HAS_SUB", null);
                session.setAttribute("NameQuiz", null);
                quiz.setTotal(total);
                SubjectDAO subdao = new SubjectDAO();
                SubjectDTO sub = subdao.getSubject(quiz.getSubID());
                request.setAttribute("RESULT_QUIZ", quiz);
                request.setAttribute("RESULT_SUB", sub);
                url = SUBMIT;
            } else {
                String yourchoice = request.getParameter("rdans");
                if (yourchoice != null) {
                    QuizDAO quizdao = new QuizDAO();
                    QuizDetailDTO quizDetail = list.get(index - 1);
                    quizdao.updateQuiz_Detail(quizDetail.getQuizDeatlID(), Integer.parseInt(yourchoice));
                    list.get(index - 1).setYourChoice(Integer.parseInt(yourchoice));
                    int total = quizdao.getNumAnswerCorrect(quiz.getQuizID());
                    quizdao.updateQuiz_Take(quiz.getQuizID(), total);
                    quizdao.updateTimeOpen(quiz.getQuizID(), h_start.toString());
                    quiz.setH_open(h_start.toString());
                    quiz.setTotal(total);
                    quiz.setList(list);
                    session.setAttribute("QUIZ", quiz);
                }
                Timestamp h_end = java.sql.Timestamp.valueOf(quiz.getH_end());
                long time = h_end.getTime() - h_start.getTime();
                int m = (int) time % (1000 * 60 * 60) / (1000 * 60);
                int s = (int) time % (1000 * 60) / (1000);
                request.setAttribute("M", m);
                request.setAttribute("S", s);
                String txtindex = request.getParameter("index");
                if (txtindex == null) {
                    index = 1;
                } else {
                    index = Integer.parseInt(txtindex);
                }
                String next_pre = request.getParameter("action");
                if (next_pre != null) {
                    if ("next".equals(next_pre)) {
                        index = Integer.parseInt(request.getParameter("index_next"));
                        if(index>(int) session.getAttribute("NUM_QUES")){
                            index=1;
                        }
                    }
                    if ("previous".equals(next_pre)) {
                        index = Integer.parseInt(request.getParameter("index_pre"));
                        if(index<1){
                            index=(int) session.getAttribute("NUM_QUES");
                        }
                    }
                }
                session.setAttribute("indexquiz", index);
                index = (int) session.getAttribute("indexquiz");
                QuizDetailDTO quizDetail = list.get(index - 1);
                request.setAttribute("QuizDetail", quizDetail);
                session.setAttribute("HAS_QUIZ", quiz);
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
