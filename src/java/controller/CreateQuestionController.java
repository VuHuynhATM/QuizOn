/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.QuestionDAO;
import dto.AnswerDTO;
import dto.QuestionDTO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tuanv
 */
public class CreateQuestionController extends HttpServlet {

    private static final String SUCCESS = "SearchController";
    private static final String ERROR = "createquestion.jsp";

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
            String question = request.getParameter("txtquestion");
            String ans1 = request.getParameter("txtq1");
            String ans2 = request.getParameter("txtq2");
            String ans3 = request.getParameter("txtq3");
            String ans4 = request.getParameter("txtq4");
            String subID =request.getParameter("cbsubject");
            boolean check = true;
            if (question != null) {
                if (question.trim().isEmpty()) {
                    request.setAttribute("QUES_ERROR", "Question not empty!!!");
                    check = false;
                } else {
                    if (question.length() > 1000) {
                        request.setAttribute("QUES_ERROR", "Question less than 1000 character!!!");
                        check = false;
                    }
                }
            }else{
                check=false;
            }
            if (ans1 != null) {
                if (ans1.trim().isEmpty()) {
                    request.setAttribute("ANS1_ERROR", "Answer not empty!!!");
                    check = false;
                } else {
                    if (ans1.length() > 1000) {
                        request.setAttribute("ANS1_ERROR", "Answer less than 1000 character!!!");
                        check = false;
                    }
                }
            }else{
                check=false;
            }
            if (ans2 != null) {
                if (ans2.trim().isEmpty()) {
                    request.setAttribute("ANS2_ERROR", "Answer not empty!!!");
                    check = false;
                } else {
                    if (ans2.length() > 1000) {
                        request.setAttribute("ANS2_ERROR", "Answer less than 1000 character!!!");
                        check = false;
                    }
                }
            }else{
                check=false;
            }
            if (ans3 != null) {
                if (ans3.trim().isEmpty()) {
                    request.setAttribute("ANS3_ERROR", "Answer not empty!!!");
                    check = false;
                } else {
                    if (ans3.length() > 1000) {
                        request.setAttribute("ANS3_ERROR", "Answer less than 1000 character!!!");
                        check = false;
                    }
                }
            }
            if (ans4 != null) {
                if (ans4.trim().isEmpty()) {
                    request.setAttribute("ANS4_ERROR", "Answer not empty!!!");
                    check = false;
                } else {
                    if (ans4.length() > 1000) {
                        request.setAttribute("ANS4_ERROR", "Answer less than 1000 character!!!");
                        check = false;
                    }
                }
            }else{
                check=false;
            }
            if(subID !=null){
                if(!subID.equals("1")&&!subID.equals("2")&&!subID.equals("3")){
                    check=false;
                }
            }else{
                check=false;
            }
            if (check) {
                ArrayList<AnswerDTO> list = new ArrayList<>();
                AnswerDTO ans1dto = new AnswerDTO("", ans1, true, true);
                list.add(ans1dto);
                AnswerDTO ans2dto = new AnswerDTO("", ans2, false, true);
                list.add(ans2dto);
                AnswerDTO ans3dto = new AnswerDTO("", ans3, false, true);
                list.add(ans3dto);
                AnswerDTO ans4dto = new AnswerDTO("", ans4, false, true);
                list.add(ans4dto);
                long millis = System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(millis);
                QuestionDTO quesdto = new QuestionDTO("", question, date.toString(), subID, list,true);
                QuestionDAO quesdao=new QuestionDAO();
                quesdao.createQuestion(quesdto);
                request.setAttribute("CREATE_SUCC", "Create new question successfull");
                url=SUCCESS;
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
