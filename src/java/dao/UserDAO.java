/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.UserDTO;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtil;

/**
 *
 * @author tuanv
 */
public class UserDAO {

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    public UserDTO checkLogin(String email, String password) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT name,status,roleName\n"
                    + "FROM users a JOIN roles b ON a.roleID=b.roleID\n"
                    + "WHERE a.email=? AND a.password=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, email);
            pst.setString(2, toHexString(getSHA(password)));
            rs = pst.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String role = rs.getString("roleName");
                String status = rs.getString("status");
                user = new UserDTO(email, name, password, role, status);
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
        return user;
    }

    public void createAccount(UserDTO user) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "INSERT INTO [dbo].[users](email, name, password, status, roleID)\n"
                    + "VALUES(?,?,?,?,?);";
            pst = conn.prepareStatement(url);
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getName());
            pst.setString(3, toHexString(getSHA(user.getPassword())));
            pst.setString(4, "NEW");
            pst.setString(5, "2");
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
