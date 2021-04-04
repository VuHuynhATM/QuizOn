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
public class SubjectDTO {
    private String subjectID;
    private String subjectName;
    private int numofQuestion;
    private int time;
    private boolean status;

    public SubjectDTO() {
    }

    public SubjectDTO(String subjectID, String subjectName, int numofQuestion, int time, boolean status) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.numofQuestion = numofQuestion;
        this.time = time;
        this.status = status;
    }



    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getNumofQuestion() {
        return numofQuestion;
    }

    public void setNumofQuestion(int numofQuestion) {
        this.numofQuestion = numofQuestion;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
