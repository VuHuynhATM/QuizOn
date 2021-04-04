/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.ArrayList;

/**
 *
 * @author tuanv
 */
public class QuizDTO {
    private String quizID;
    private String email;
    private String subID;
    private String h_start;
    private String h_end;
    private String h_open;
    private int total;
    private boolean status;
    ArrayList<QuizDetailDTO> list;

    public QuizDTO() {
    }

    public QuizDTO(String quizID, String email, String subID, String h_start, String h_end, String h_open, int total, boolean status, ArrayList<QuizDetailDTO> list) {
        this.quizID = quizID;
        this.email = email;
        this.subID = subID;
        this.h_start = h_start;
        this.h_end = h_end;
        this.h_open = h_open;
        this.total = total;
        this.status = status;
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubID() {
        return subID;
    }

    public void setSubID(String subID) {
        this.subID = subID;
    }

    public String getH_start() {
        return h_start;
    }

    public void setH_start(String h_start) {
        this.h_start = h_start;
    }

    public String getH_end() {
        return h_end;
    }

    public void setH_end(String h_end) {
        this.h_end = h_end;
    }

    public String getH_open() {
        return h_open;
    }

    public void setH_open(String h_open) {
        this.h_open = h_open;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<QuizDetailDTO> getList() {
        return list;
    }

    public void setList(ArrayList<QuizDetailDTO> list) {
        this.list = list;
    }
    
}
