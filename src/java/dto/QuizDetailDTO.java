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
public class QuizDetailDTO {
    private String quizDeatlID;
    private String email;
    private QuestionDTO ques;
    private int yourChoice;

    public QuizDetailDTO() {
    }

    public QuizDetailDTO(String quizDeatlID, String email, QuestionDTO ques, int yourChoice) {
        this.quizDeatlID = quizDeatlID;
        this.email = email;
        this.ques = ques;
        this.yourChoice = yourChoice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuizDeatlID() {
        return quizDeatlID;
    }

    public void setQuizDeatlID(String quizDeatlID) {
        this.quizDeatlID = quizDeatlID;
    }

    public QuestionDTO getQues() {
        return ques;
    }

    public void setQues(QuestionDTO ques) {
        this.ques = ques;
    }

    public int getYourChoice() {
        return yourChoice;
    }

    public void setYourChoice(int yourChoice) {
        this.yourChoice = yourChoice;
    }
    
}
