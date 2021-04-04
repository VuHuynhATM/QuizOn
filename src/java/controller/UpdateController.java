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
public class UpdateController extends HttpServlet {

    private static final String SUCCESS = "SearchController";
    private static final String ERROR = "updatequestion.jsp";

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
            String questionID = request.getParameter("txtq_id");
            String question_context = request.getParameter("txtq_context");
            String an1_id = request.getParameter("txta_id1");
            String an1_context = request.getParameter("txta_context1");
            String an2_id = request.getParameter("txta_id2");
            String an2_context = request.getParameter("txta_context2");
            String an3_id = request.getParameter("txta_id3");
            String an3_context = request.getParameter("txta_context3");
            String an4_id = request.getParameter("txta_id4");
            String an4_context = request.getParameter("txta_context4");
            String an_correct=request.getParameter("txta");
            String status=request.getParameter("cbstatus");
            String subject=request.getParameter("cbsubject");
            boolean check = true;
            if (questionID == null || an1_id == null || an2_id == null || an3_id == null || an4_id == null) {
                check = false;
            }
            if (question_context == null && an1_context == null && an2_context == null && an3_context == null && an4_context == null) {
                check = false;
            } else {
                if (question_context.trim().isEmpty() || an1_context.trim().isEmpty() || an2_context.trim().isEmpty() || an3_context.trim().isEmpty() || an4_context.trim().isEmpty()) {
                    check = false;
                    request.setAttribute("MESS", "Question And Answer not empty!!!!!");
                } else {
                    if (question_context.length() > 1000 || an1_context.length() > 1000 || an2_context.length() > 1000 || an3_context.length() > 1000 || an4_context.length() > 1000) {
                        check = false;
                        request.setAttribute("MESS", "Question And Answer less than 1000 character!!!!!");
                    }
                }
            }
            if(an_correct==null){
                check=false;
                request.setAttribute("MESS", "Choice correct answer!!!!!");
            }
            if(status==null||subject==null){
                check=false;
            }
            if(check){
                ArrayList<AnswerDTO>list=new ArrayList<>();
                if(an_correct.equals(an1_id)){
                    AnswerDTO an1=new AnswerDTO(an1_id, an1_context, true, true);
                    list.add(an1);
                }else{
                    AnswerDTO an1=new AnswerDTO(an1_id, an1_context, false, true);
                    list.add(an1);
                }
                if(an_correct.equals(an2_id)){
                    AnswerDTO an2=new AnswerDTO(an2_id, an2_context, true, true);
                    list.add(an2);
                }else{
                    AnswerDTO an2=new AnswerDTO(an2_id, an2_context, false, true);
                    list.add(an2);
                }
                if(an_correct.equals(an3_id)){
                    AnswerDTO an3=new AnswerDTO(an1_id, an3_context, true, true);
                    list.add(an3);
                }else{
                    AnswerDTO an3=new AnswerDTO(an3_id, an3_context, false, true);
                    list.add(an3);
                }
                if(an_correct.equals(an4_id)){
                    AnswerDTO an4=new AnswerDTO(an4_id, an4_context, true, true);
                    list.add(an4);
                }else{
                    AnswerDTO an4=new AnswerDTO(an4_id, an4_context, false, true);
                    list.add(an4);
                }
                QuestionDTO ques=new QuestionDTO(questionID, question_context, "", subject, list, Boolean.parseBoolean(status));
                QuestionDAO quesdao=new QuestionDAO();
                quesdao.updateQuestion(ques);
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
