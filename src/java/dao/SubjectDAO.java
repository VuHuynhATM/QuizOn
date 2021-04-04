/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.SubjectDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtil;

/**
 *
 * @author tuanv
 */
public class SubjectDAO {

    public SubjectDTO getSubject(String subID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        SubjectDTO subject = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT subName,numofQuestion,time,status\n"
                    + "FROM subject\n"
                    + "WHERE subID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, subID);
            rs = pst.executeQuery();
            if (rs.next()) {
                subject=new SubjectDTO(subID, rs.getString("subName"), rs.getInt("numofQuestion"), rs.getInt("time"), rs.getBoolean("status"));
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
        return subject;
    }
}
