<%-- 
    Document   : create
    Created on : Jan 26, 2021, 1:32:20 PM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Account Page</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <form action="CreateAccountController">
            Email:<input name="txtemail" type="email" value="${param.txtemail}">${requestScope.EMAILERROR}</br>
            password:<input type="password" name="txtpassword" >${requestScope.PASSWORDERROR}</br>
            Confirm:<input type="password" name="txtconfirm" >${requestScope.COMFIRMERROR}</br>
            Name:<input type="text" name="txtname" value="${param.txtname}">${requestScope.NAMEERROR}</br>
            <input type="submit" value="Create Account" name="btnAction">
        </form>
    </body>
</html>
