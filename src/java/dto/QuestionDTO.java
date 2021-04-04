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
public class QuestionDTO {
    private String questionID;
    private String questio_context;
    private String createDate;
    private String subID;
    private ArrayList<AnswerDTO> list;
    private boolean status;

    public QuestionDTO() {
    }

    public QuestionDTO(String questionID, String questio_context, String createDate, String subID, ArrayList<AnswerDTO> list, boolean status) {
        this.questionID = questionID;
        this.questio_context = questio_context;
        this.createDate = createDate;
        this.subID = subID;
        this.list = list;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestio_context() {
        return questio_context;
    }

    public void setQuestio_context(String questio_context) {
        this.questio_context = questio_context;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSubID() {
        return subID;
    }

    public void setSubID(String subID) {
        this.subID = subID;
    }

    public ArrayList<AnswerDTO> getList() {
        return list;
    }

    public void setList(ArrayList<AnswerDTO> list) {
        this.list = list;
    }
    
    
}
