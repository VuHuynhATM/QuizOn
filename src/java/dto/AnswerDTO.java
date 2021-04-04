/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;


/**
 *
 * @author tuanv
 */
public class AnswerDTO {
    private String answerID;
    private String answer_context;
    private boolean answer_correct;
    private boolean status;

    public AnswerDTO() {
    }

    public AnswerDTO(String answerID, String answer_context, boolean answer_correct, boolean status) {
        this.answerID = answerID;
        this.answer_context = answer_context;
        this.answer_correct = answer_correct;
        this.status = status;
    }

    public String getAnswerID() {
        return answerID;
    }

    public void setAnswerID(String answerID) {
        this.answerID = answerID;
    }

    public String getAnswer_context() {
        return answer_context;
    }

    public void setAnswer_context(String answer_context) {
        this.answer_context = answer_context;
    }

    public boolean isAnswer_correct() {
        return answer_correct;
    }

    public void setAnswer_correct(boolean answer_correct) {
        this.answer_correct = answer_correct;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
