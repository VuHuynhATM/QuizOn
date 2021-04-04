/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.AnswerDTO;
import dto.QuestionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import utils.DBUtil;

/**
 *
 * @author tuanv
 */
public class QuestionDAO {

    public String getquestionID() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String questionID = "";
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT MAX(questionID) as num\n"
                    + "FROM question";
            pst = conn.prepareStatement(url);
            rs = pst.executeQuery();
            if (rs.next()) {
                questionID = rs.getString("num");
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
        return questionID;
    }

    public QuestionDTO getQuestion(String questionID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        QuestionDTO question = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT questionID,question_content,createDate,status,subID\n"
                    + "FROM question WHERE questionID=?\n";
            pst = conn.prepareStatement(url);
            pst.setString(1, questionID);
            rs = pst.executeQuery();
            if (rs.next()) {
                question = new QuestionDTO(rs.getString("questionID"), rs.getString("question_content"), rs.getString("createDate"), rs.getString("subID"), getAnswer(rs.getString("questionID")), rs.getBoolean("status"));
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
        return question;
    }

    public void createQuestion(QuestionDTO ques) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "INSERT INTO question(question_content,createDate,subID,status)\n"
                    + "VALUES(?,?,?,?);";
            pst = conn.prepareStatement(url);
            pst.setString(1, ques.getQuestio_context());
            pst.setString(2, ques.getCreateDate());
            pst.setString(3, ques.getSubID());
            pst.setString(4, "1");
            pst.executeUpdate();
            ArrayList<AnswerDTO> list = ques.getList();
            for (int i = 0; i < list.size(); i++) {
                createAnswer(list.get(i), getquestionID());
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

    public void createAnswer(AnswerDTO ans, String questionID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "INSERT INTO answer(answer_content,answer_correct,questionID,status)\n"
                    + "VALUES(?,?,?,?);";
            pst = conn.prepareStatement(url);
            pst.setString(1, ans.getAnswer_context());
            if (ans.isAnswer_correct()) {
                pst.setString(2, "1");
            } else {
                pst.setString(2, "0");
            }
            pst.setString(3, questionID);
            pst.setString(4, "1");
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

    public HashMap<String, ArrayList<QuestionDTO>> getQuestionBySub(int index, String subID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        HashMap<String, ArrayList<QuestionDTO>> hash = null;
        try {
            conn = DBUtil.getConnection();
            String url = "with x as(SELECT ROW_NUMBER() over (order by subID asc,[question_content] asc) as row,questionID,question_content,createDate,status,subID\n"
                    + "FROM question WHERE subID=?)\n"
                    + "SELECT row,questionID,question_content,createDate,status,subID FROM x WHERE row BETWEEN ? AND ?\n";
            pst = conn.prepareStatement(url);
            pst.setString(1, subID);
            pst.setString(2, index * 5 - 4 + "");
            pst.setString(3, index * 5 + "");
            rs = pst.executeQuery();
            while (rs.next()) {
                if (hash == null) {
                    hash = new HashMap<>();
                }
                QuestionDTO quesdto = new QuestionDTO(rs.getString("questionID"), rs.getString("question_content"), rs.getString("createDate"), rs.getString("subID"), getAnswer(rs.getString("questionID")), rs.getBoolean("status"));
                ArrayList<QuestionDTO> list = new ArrayList<>();
                if (hash.isEmpty()) {
                    list.add(quesdto);
                    hash.put(rs.getString("subID"), list);
                } else {
                    if (hash.containsKey(rs.getString("subID"))) {
                        list = hash.get(rs.getString("subID"));
                        list.add(quesdto);
                        hash.remove(rs.getString("subID"));
                        hash.put(rs.getString("subID"), list);
                    } else {
                        list.add(quesdto);
                        hash.put(rs.getString("subID"), list);
                    }
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
        return hash;
    }

    public int getNumPageofSub(String subID) throws SQLException, ClassNotFoundException {
        int numpage = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT count(questionID) as num\n"
                    + "FROM question WHERE subID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, subID);
            rs = pst.executeQuery();
            if (rs.next()) {
                numpage = (int) Math.ceil((double) rs.getInt("num") / 5);
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
        return numpage;
    }

    public HashMap<String, ArrayList<QuestionDTO>> getQuestionByStatus(int index, boolean status) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        HashMap<String, ArrayList<QuestionDTO>> hash = null;
        try {
            conn = DBUtil.getConnection();
            String url = "with x as(SELECT ROW_NUMBER() over (order by subID asc,[question_content] asc) as row,questionID,question_content,createDate,status,subID\n"
                    + "FROM question WHERE status=?)\n"
                    + "SELECT row,questionID,question_content,createDate,status,subID FROM x WHERE row BETWEEN ? AND ?\n";
            pst = conn.prepareStatement(url);
            if (status) {
                pst.setString(1, "1");
            } else {
                pst.setString(1, "0");
            }
            pst.setString(2, index * 5 - 4 + "");
            pst.setString(3, index * 5 + "");
            rs = pst.executeQuery();
            while (rs.next()) {
                if (hash == null) {
                    hash = new HashMap<>();
                }
                QuestionDTO quesdto = new QuestionDTO(rs.getString("questionID"), rs.getString("question_content"), rs.getString("createDate"), rs.getString("subID"), getAnswer(rs.getString("questionID")), rs.getBoolean("status"));
                ArrayList<QuestionDTO> list = new ArrayList<>();
                if (hash.isEmpty()) {
                    list.add(quesdto);
                    hash.put(rs.getString("subID"), list);
                } else {
                    if (hash.containsKey(rs.getString("subID"))) {
                        list = hash.get(rs.getString("subID"));
                        list.add(quesdto);
                        hash.remove(rs.getString("subID"));
                        hash.put(rs.getString("subID"), list);
                    } else {
                        list.add(quesdto);
                        hash.put(rs.getString("subID"), list);
                    }
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
        return hash;
    }

    public int getNumPageofStatus(boolean status) throws SQLException, ClassNotFoundException {
        int numpage = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT count(questionID) as num\n"
                    + "FROM question WHERE status=?";
            pst = conn.prepareStatement(url);
            if (status) {
                pst.setString(1, "1");
            } else {
                pst.setString(1, "0");
            }
            rs = pst.executeQuery();
            if (rs.next()) {
                numpage = (int) Math.ceil((double) rs.getInt("num") / 5);
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
        return numpage;
    }

    public HashMap<String, ArrayList<QuestionDTO>> getQuestionByName(int index, String question_content) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        HashMap<String, ArrayList<QuestionDTO>> hash = null;
        try {
            conn = DBUtil.getConnection();
            String url = "with x as(SELECT ROW_NUMBER() over (order by subID asc,[question_content] asc) as row,questionID,question_content,createDate,status,subID\n"
                    + "FROM question WHERE question_content LIKE ?)\n"
                    + "SELECT row,questionID,question_content,createDate,status,subID FROM x WHERE row BETWEEN ? AND ?\n";
            pst = conn.prepareStatement(url);
            pst.setString(1, "%" + question_content + "%");
            pst.setString(2, index * 5 - 4 + "");
            pst.setString(3, index * 5 + "");
            rs = pst.executeQuery();
            while (rs.next()) {
                if (hash == null) {
                    hash = new HashMap<>();
                }
                QuestionDTO quesdto = new QuestionDTO(rs.getString("questionID"), rs.getString("question_content"), rs.getString("createDate"), rs.getString("subID"), getAnswer(rs.getString("questionID")), rs.getBoolean("status"));
                ArrayList<QuestionDTO> list = new ArrayList<>();
                if (hash.isEmpty()) {
                    list.add(quesdto);
                    hash.put(rs.getString("subID"), list);
                } else {
                    if (hash.containsKey(rs.getString("subID"))) {
                        list = hash.get(rs.getString("subID"));
                        list.add(quesdto);
                        hash.remove(rs.getString("subID"));
                        hash.put(rs.getString("subID"), list);
                    } else {
                        list.add(quesdto);
                        hash.put(rs.getString("subID"), list);
                    }
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
        return hash;
    }

    public int getNumPageofName(String question_content) throws SQLException, ClassNotFoundException {
        int numpage = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT count(questionID) as num\n"
                    + "FROM question WHERE question_content LIKE ?";
            pst = conn.prepareStatement(url);
            pst.setString(1, "%" + question_content + "%");
            rs = pst.executeQuery();
            if (rs.next()) {
                numpage = (int) Math.ceil((double) rs.getInt("num") / 5);
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
        return numpage;
    }

    public void updateQuestion(QuestionDTO quesdto) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "UPDATE question\n"
                    + "SET question_content=?,status=?,subID=?\n"
                    + "WHERE questionID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, quesdto.getQuestio_context());
            if (quesdto.isStatus()) {
                pst.setString(2, "1");
            } else {
                pst.setString(2, "0");

            }
            pst.setString(3, quesdto.getSubID());
            pst.setString(4, quesdto.getQuestionID());
            pst.executeUpdate();
            ArrayList<AnswerDTO> list = quesdto.getList();
            for (int i = 0; i < list.size(); i++) {
                updateAnswer(list.get(i));
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

    public void updateAnswer(AnswerDTO ans) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "UPDATE answer\n"
                    + "SET answer_content=?,answer_correct=?\n"
                    + "WHERE answerID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, ans.getAnswer_context());
            if (ans.isAnswer_correct()) {
                pst.setString(2, "1");
            } else {
                pst.setString(2, "0");
            }
            pst.setString(3, ans.getAnswerID());
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

    public void deleteQuestion(String questionID, boolean status) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "";
            if (status) {
                url = "UPDATE question\n"
                        + "SET status=0\n"
                        + "WHERE questionID=?";
            } else {
                url = "UPDATE question\n"
                        + "SET status=1\n"
                        + "WHERE questionID=?";
            }
            pst = conn.prepareStatement(url);
            pst.setString(1, questionID);
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
}
