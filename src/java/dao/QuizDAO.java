/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.AnswerDTO;
import dto.QuestionDTO;
import dto.QuizDTO;
import dto.QuizDetailDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.DBUtil;

/**
 *
 * @author tuanv
 */
public class QuizDAO {

    public QuizDTO getQuizDoing(String email) throws SQLException, ClassNotFoundException {
        QuizDTO quiz = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT quiz_takeID, total, h_start, h_end, h_open,subID\n"
                    + "FROM quiz_take\n"
                    + "WHERE email=? AND status=0\n"
                    + "ORDER BY NEWID()";
            pst = conn.prepareStatement(url);
            pst.setString(1, email);
            rs = pst.executeQuery();
            if (rs.next()) {
                String quiz_takeID = rs.getString("quiz_takeID");
                int total = rs.getInt("total");
                String h_open = rs.getString("h_open");
                String h_start = rs.getString("h_start");
                String h_end = rs.getString("h_end");
                String subID = rs.getString("subID");
                ArrayList<QuizDetailDTO> list = getListQuestion(quiz_takeID);
                QuizDTO quizdto = new QuizDTO(quiz_takeID, email, subID, h_start, h_end, h_open, total, true, list);
                quiz = new QuizDTO(quiz_takeID, email, subID, h_start, h_end, h_open, total, true, list);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return quiz;
    }

    public ArrayList<QuizDetailDTO> getListQuestion(String quiz_takeID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<QuizDetailDTO> list = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT quizDetailID\n"
                    + "FROM quiz\n"
                    + "WHERE quiz_takeID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, quiz_takeID);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                String quizDetailID = rs.getString("quizDetailID");
                QuizDetailDTO quizDetail = getQuestionDoing(quizDetailID);
                list.add(quizDetail);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public QuizDetailDTO getQuestionDoing(String quizDetailID) throws SQLException, ClassNotFoundException {
        QuizDetailDTO quizDetail = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT questionID,yourChoice,email\n"
                    + "FROM quizDetail\n"
                    + "WHERE quizDetailID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, quizDetailID);
            rs = pst.executeQuery();
            if (rs.next()) {
                String questionID = rs.getString("questionID");
                String yourChoice = rs.getString("yourChoice");
                String email = rs.getString("email");
                QuestionDAO quesdao = new QuestionDAO();
                QuestionDTO ques = quesdao.getQuestion(questionID);
                quizDetail = new QuizDetailDTO(quizDetailID, email, ques, Integer.parseInt(yourChoice));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return quizDetail;
    }

    public ArrayList<QuestionDTO> getQuestionToQuiz(String subID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<QuestionDTO> list = null;
        try {
            SubjectDAO subdao = new SubjectDAO();
            conn = DBUtil.getConnection();
            String url = "SELECT TOP(?)questionID,question_content,createDate,status,subID\n"
                    + "FROM question\n"
                    + "WHERE status=1 AND subID=?\n"
                    + "ORDER BY NEWID()";
            pst = conn.prepareStatement(url);
            pst.setInt(1, subdao.getSubject(subID).getNumofQuestion());
            pst.setString(2, subID);
            rs = pst.executeQuery();
            while (rs.next()) {
                QuestionDTO quesdto = new QuestionDTO(rs.getString("questionID"), rs.getString("question_content"), rs.getString("createDate"), rs.getString("subID"), getAnswer(rs.getString("questionID")), rs.getBoolean("status"));
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(quesdto);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public ArrayList<AnswerDTO> getAnswer(String questionID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<AnswerDTO> list = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT answerID,answer_content,answer_correct,status\n"
                    + "FROM answer\n"
                    + "WHERE questionID=?\n"
                    + "ORDER BY NEWID()";
            pst = conn.prepareStatement(url);
            pst.setString(1, questionID);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                AnswerDTO ansdto = new AnswerDTO(rs.getString("answerID"), rs.getString("answer_content"), rs.getBoolean("answer_correct"), rs.getBoolean("status"));
                list.add(ansdto);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public void createQuizTake(QuizDTO quiz_take) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "INSERT INTO quiz_take(email,h_end,h_open,h_start,status,subID,total)\n"
                    + "VALUES(?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(url);
            pst.setString(1, quiz_take.getEmail());
            pst.setString(2, quiz_take.getH_end());
            pst.setString(3, quiz_take.getH_open());
            pst.setString(4, quiz_take.getH_start());
            pst.setString(5, "0");
            pst.setString(6, quiz_take.getSubID());
            pst.setString(7, quiz_take.getTotal() + "");
            pst.executeUpdate();
            ArrayList<QuestionDTO> list = getQuestionToQuiz(quiz_take.getSubID());
            for (int i = 0; i < list.size(); i++) {
                createQuizDetail(list.get(i), quiz_take.getEmail());
                createQuiz(quiz_take.getEmail());
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void createQuizDetail(QuestionDTO ques, String email) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "INSERT INTO quizDetail(questionID,yourChoice,email)\n"
                    + "VALUES(?,?,?)";
            pst = conn.prepareStatement(url);
            pst.setString(1, ques.getQuestionID());
            pst.setString(2, "");
            pst.setString(3, email);
            pst.executeUpdate();
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public String getQuiz_TakeID(String email) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String result = "";
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT MAX(quiz_takeID) as num\n"
                    + "FROM quiz_take\n"
                    + "WHERE email=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, email);
            rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getString("num") == null) {
                    result = "1";
                } else {
                    result = rs.getString("num");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public String getQuiz_DetailID(String email) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String result = "";
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT MAX(quizDetailID) as num\n"
                    + "FROM quizDetail\n"
                    + "WHERE email=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, email);
            rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getString("num") == null) {
                    result = "1";
                } else {
                    result = rs.getString("num");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public void createQuiz(String email) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "INSERT INTO quiz(quiz_takeID,quizDetailID)\n"
                    + "VALUES (?,?)";
            pst = conn.prepareStatement(url);
            pst.setString(1, getQuiz_TakeID(email));
            pst.setString(2, getQuiz_DetailID(email));
            pst.executeUpdate();

        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void submitQuizByTimeOut(String email, String quiz_takeID, String h_open) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "UPDATE quiz_take\n"
                    + "SET status=1,h_open=?\n"
                    + "WHERE quiz_takeID=? AND email=? AND ?>h_end";
            pst = conn.prepareStatement(url);
            pst.setString(1, h_open);
            pst.setString(2, quiz_takeID);
            pst.setString(3, email);
            pst.setString(4, h_open);
            pst.executeUpdate();
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void submitQuiz(String email, String quiz_takeID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "UPDATE quiz_take\n"
                    + "SET status=1\n"
                    + "WHERE quiz_takeID=? AND email=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, quiz_takeID);
            pst.setString(2, email);
            pst.executeUpdate();
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void updateQuiz_Take(String quizID, int total) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "UPDATE [dbo].[quiz_take]\n"
                    + "SET total=?\n"
                    + "WHERE quiz_takeID=?";
            pst = conn.prepareStatement(url);
            pst.setInt(1, total);
            pst.setString(2, quizID);
            pst.executeUpdate();

        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void updateQuiz_Detail(String quizdetailID, int yourchoice) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "UPDATE [dbo].[quizDetail]\n"
                    + "SET yourChoice=?\n"
                    + "WHERE quizDetailID=?";
            pst = conn.prepareStatement(url);
            pst.setInt(1, yourchoice);
            pst.setString(2, quizdetailID);
            pst.executeUpdate();

        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public int getNumAnswerCorrect(String quizID) throws ClassNotFoundException, SQLException {
        int num = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "Select count(b.quizDetailID) as num\n"
                    + "from [dbo].[quiz] a Join [dbo].[quizDetail] b on a.quizDetailID=b.quizDetailID\n"
                    + "		join [dbo].[question] c  on b.questionID=c.questionID\n"
                    + "		join [dbo].[answer] d on c.questionID=d.questionID\n"
                    + "where b.yourChoice=d.answerID and d.answer_correct=1 and a.quiz_takeID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, quizID);
            rs = pst.executeQuery();
            if (rs.next()) {
                num = rs.getInt("num");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return num;
    }

    public void updateTimeOpen(String quizID, String time) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "UPDATE [dbo].[quiz_take]\n"
                    + "SET h_open=?\n"
                    + "WHERE quiz_takeID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, time);
            pst.setString(2, quizID);
            pst.executeUpdate();
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public ArrayList<QuizDTO> getHistory(String email, String subID, int index) throws SQLException, ClassNotFoundException {
        ArrayList<QuizDTO> list = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "with x as(SELECT ROW_NUMBER() over (order by [h_start] DESC) as row,[total],[subID],[h_start],[h_end],[h_open],quiz_takeID\n"
                    + "          FROM [dbo].[quiz_take] WHERE [email]=? AND subID=? AND status=1) \n"
                    + "SELECT row,[total],[subID],[h_start],[h_end],[h_open],quiz_takeID FROM x WHERE row BETWEEN ? AND ?";
            pst = conn.prepareStatement(url);
            pst.setString(1, email);
            pst.setString(2, subID);
            pst.setInt(3, index * 5 - 4);
            pst.setInt(4, index * 5);
            rs = pst.executeQuery();
            while (rs.next()) {
                String quiz_takeID = rs.getString("quiz_takeID");
                int total = rs.getInt("total");
                String h_open = rs.getString("h_open");
                String h_start = rs.getString("h_start");
                String h_end = rs.getString("h_end");
                ArrayList<QuizDetailDTO> listdetail = getListQuestion(quiz_takeID);
                QuizDTO quizdto = new QuizDTO(quiz_takeID, email, subID, h_start, h_end, h_open, total, true, listdetail);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(quizdto);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public int getNumQuiz(String email, String subID) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT count(quiz_takeID) as num\n"
                    + "FROM [dbo].[quiz_take] WHERE [email]=? AND subID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, email);
            pst.setString(2, subID);
            rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getInt("num");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
}
