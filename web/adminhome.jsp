<%-- 
    Document   : adminhome
    Created on : Jan 26, 2021, 7:50:01 PM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>admin home Page</title>
    </head>
    <body>
        <h1>QUIZ NOT LET
            <a href="LogoutController" style="float: right">Logout</a>
        </h1>
        <form action="SearchController" method="POST">
            <input type="radio" name="rdsearch" value="SearchBySubject" <c:if test="${sessionScope.METHOD_SEARCH eq'SearchBySubject'}">checked="true"</c:if>>
            <select name="cbsubject">
                <option value="1" <c:if test="${sessionScope.SUB_SEARCH eq('1')}">selected="selected"</c:if> >Math</option>
                <option value="2" <c:if test="${sessionScope.SUB_SEARCH  eq('2')}">selected="selected"</c:if> >PRJ</option>
                <option value="3" <c:if test="${sessionScope.SUB_SEARCH  eq('3')}">selected="selected"</c:if> >CEA</option>
            </select></br>
            <input type="radio" name="rdsearch" value="SearchByStatus" <c:if test="${sessionScope.METHOD_SEARCH eq'SearchByStatus'}">checked="true"</c:if>>
            <select name="cbstatus">
                <option value="true" <c:if test="${sessionScope.STATUS_SEARCH  eq('true')}">selected="selected"</c:if> >Active</option>
                <option value="false" <c:if test="${sessionScope.STATUS_SEARCH  eq('false')}">selected="selected"</c:if> >Inactive</option>
            </select></br>
            <input type="radio" name="rdsearch" value="SearchByQuestion" <c:if test="${sessionScope.METHOD_SEARCH eq'SearchByQuestion'}">checked="true"</c:if>>
            <input type="text" name="txtsearch" value="${sessionScope.NAME_SEARCH}">
            <input type="submit" value="Search">
        </form>
        <a href="createquestion.jsp">Create Question</a></br>
        ${requestScope.CREATE_SUCC}
        </br>
        <c:forEach var="sub" items="${sessionScope.HASH_QUESTION}">
            </br>
            <c:if test="${sub.key eq('1')}"><font style="font-size: 30px; color: blue; font-weight: bold; ">Math</font></c:if>
            <c:if test="${sub.key eq('2')}"><font style="font-size: 30px; color: blue; font-weight: bold; ">PRJ</font></c:if>
            <c:if test="${sub.key eq('3')}"><font style="font-size: 30px; color: blue; font-weight: bold; ">CEA</font></c:if>
                </br>
            <c:forEach var="listques" varStatus="counter" items="${sub.value}">
                <h4>Question ${(sessionScope.index-1)*5+counter.count}. ${listques.questio_context}</h4>
                <c:forEach var="ans" items="${listques.list}">
                    <c:if test="${ans.answer_correct}"><font style="color: red">&nbsp;&nbsp;&nbsp;${ans.answer_context}</font></c:if>
                    <c:if test="${!ans.answer_correct}">&nbsp;&nbsp;&nbsp;${ans.answer_context}</c:if></br>
                </c:forEach>
                </br>
                &nbsp;&nbsp;&nbsp;Status 
                <select name="cbstatusofques" disabled="true">
                    <option value="1" <c:if test="${listques.status}">selected="selected"</c:if> >Active</option>
                    <option value="0" <c:if test="${!listques.status}">selected="selected"</c:if> >Inactive</option>
                    </select>
                    <form action="UpdateView" method="POST">
                        <input type="hidden" name="txtquestionID" value="${listques.questionID}">
                    <input type="hidden" name="txtquestion" value="${listques.questio_context}">
                    <input type="submit" value="Update">
                </form>
                <form action="DeleteController" method="POST">
                    <input type="hidden" name="txtquestionID" value="${listques.questionID}">
                    <input type="hidden" name="txtstatus" value="${listques.status}">
                    <input type="submit" <c:if test="${listques.status}">value="Delete"</c:if> <c:if test="${!listques.status}">value="Active"</c:if> >
                    </form>
            </c:forEach>
        </c:forEach>
        </br>
        <c:if test="${sessionScope.NUM_PAGE > 1}">
            <c:forEach var="i" begin="1" end="${sessionScope.NUM_PAGE}">
                <a href="SearchController?index=${i}">${i}</a>
            </c:forEach>
        </c:if>

    </body>
</html>
