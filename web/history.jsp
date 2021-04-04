<%-- 
    Document   : history
    Created on : Jan 31, 2021, 3:39:25 AM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
    </head>
    <body>
        <h1>QUIZ NOT LET
            <a href="LogoutController" style="float: right">Logout</a>&nbsp;
            <font style="float: right; color: blue">${sessionScope.LOGIN_USER.name}</font>
        </h1>
        <a href="studenhom.jsp">Back to home</a>
        <form action="HistorySearch" method="POST">
            <select name="cbsubject_his">
                <option value="1" <c:if test="${param.cbsubject_his eq('1')}">selected="true"</c:if>>Math</option>
                <option value="2" <c:if test="${param.cbsubject_his eq('2')}">selected="true"</c:if>>PRJ</option>
                <option value="3" <c:if test="${param.cbsubject_his eq('3')}">selected="true"</c:if>>CEA</option>
                </select>
                <input type="submit" value="Search">
            </form>
        <c:if test="${requestScope.LIST_HIS!=null}">
            <c:if test="${not empty requestScope.LIST_HIS}">
                <c:forEach var="quiz" items="${requestScope.LIST_HIS}">
                    DATE: ${quiz.h_start}    Answer correct: ${quiz.total}/${requestScope.NUM_QUIZ}     Score: ${quiz.total/requestScope.NUM_QUIZ*10}</br>
                </c:forEach>
            </c:if>
        </c:if>
                    <span style="text-align: end">
            <c:if test="${requestScope.NUM_HIS > 1}">
                <c:forEach var="i" begin="1" end="${requestScope.NUM_HIS}">
                    <a href="HistorySearch?index=${i}&cbsubject_his=${param.cbsubject_his}">${i}</a>
                </c:forEach>
            </c:if> 
        </span>
    </body>
</html>
