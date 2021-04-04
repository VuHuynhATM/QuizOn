<%-- 
    Document   : login
    Created on : Jan 25, 2021, 3:59:59 PM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <form action="LoginController" method="POST">
            Email: <input type="text" name="txtemail" value="${param.txtemail}">${requestScope.EMAILERROR}</br>
            Password: <input type="password" name="txtpassword">${requestScope.PASSWORDERROR}</br>
            ${requestScope.LOGINERROR}
            <input type="submit" name="btnAction" value="Login"></br>
            <a href="create.jsp" >Create New Account</a>
        </form>
    </body>
</html>
