<%-- 
    Document   : createquestion
    Created on : Jan 28, 2021, 3:12:56 PM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Question Page</title>
    </head>
    <body>
        <h1>QUIZ NOT LET
            <a href="LogoutController" style="float: right">Logout</a>
        </h1>
        <h1>Create Question</h1>
        <form action="CreateQuestionController" method="POST">
            Question context:<input type="text" name="txtquestion" value="${param.txtquestion}" style="width: 900px"></br>
            <font style="color: red">${requestScope.QUES_ERROR}</font></br>
            Answer correct:<input type="text" name="txtq1" value="${param.txtq1}" style="width: 900px"></br>
            <font style="color: red">${requestScope.ANS1_ERROR}</font></br>
            Answer context:<input type="text" name="txtq2" value="${param.txtq2}" style="width: 900px"></br>
            <font style="color: red">${requestScope.ANS2_ERROR}</font></br>
            Answer context:<input type="text" name="txtq3" value="${param.txtq3}" style="width: 900px"></br>
            <font style="color: red">${requestScope.ANS3_ERROR}</font></br>
            Answer context:<input type="text" name="txtq4" value="${param.txtq4}" style="width: 900px"></br>
            <font style="color: red">${requestScope.ANS4_ERROR}</font></br>
            <select name="cbsubject">
                <option value="1">Math</option>
                <option value="2">PRJ</option>
                <option value="3">CEA</option>
            </select></br>
            <input type="submit" value="Create Question">
            <input type="reset" value="Reset">
        </form>
    </body>
</html>
