<%-- 
    Document   : studenthome
    Created on : Jan 26, 2021, 7:49:48 PM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>student home Page</title>
    </head>
    <body>
        <h1>QUIZ NOT LET
            <a href="LogoutController" style="float: right">Logout</a>&nbsp;
            <font style="float: right; color: blue">${sessionScope.LOGIN_USER.name}</font>
        </h1>
        </br>
        <c:if test="${empty sessionScope.HAS_QUIZ}">
            <h3>Choice subject to take a quiz</h3>
            <c:if test="${not empty requestScope.RESULT_SUB}">
                <h3>${requestScope.RESULT_SUB.subjectName}</h3>
                Correct: ${requestScope.RESULT_QUIZ.total}/${requestScope.RESULT_SUB.numofQuestion}          
                Score :${requestScope.RESULT_QUIZ.total/requestScope.RESULT_SUB.numofQuestion*10}  
            </c:if>
            <form action="TakeQuizController" method="POST">
                <input type="submit" name="subject" value="Math">
                <input type="submit" name="subject" value="PRJ">
                <input type="submit" name="subject" value="CEA">
            </form>
            </br>
            <a href="history.jsp">History</a>
            
        </c:if>
        <c:if test="${not empty sessionScope.HAS_QUIZ}">
            <h2>You have an unfinished quiz</h2>
            <form action="TakeQuizController" method="POST">
                <input type="hidden" name="subject" value="${sessionScope.HAS_SUB.subjectName}">
                <input type="submit" value="CONTINUE" name="btnCotinue">
            </form>
        </c:if>    
    </body>
</html>
