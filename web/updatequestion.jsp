<%-- 
    Document   : updatequestion
    Created on : Jan 30, 2021, 12:14:31 AM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>update Page</title>
    </head>
    <body>
        <h1>Update question</h1>
        <form action="UpdateController" method="POST">
            Question Context:  <input type="text" name="txtq_context" value="${requestScope.QUESTION_UP.questio_context}${param.txtq_context}" size="100" ></br>
            <input type="hidden" name="txtq_id" value="${requestScope.QUESTION_UP.questionID}">
            Answer 1:  <input type="text" name="txta_context1" value="${requestScope.ANSWER_UP0.answer_context}${param.txta_context1}" size="100"><input type="radio" name="txta" value="${requestScope.ANSWER_UP0.answerID}" <c:if test="${requestScope.ANSWER_UP0.answer_correct}">checked="true"</c:if> size="100" ></br>
            <input type="hidden" name="txta_id1" value="${requestScope.ANSWER_UP0.answerID}">
            Answer 2:  <input type="text" name="txta_context2" value="${requestScope.ANSWER_UP1.answer_context}${param.txta_context2}" size="100"><input type="radio" name="txta" value="${requestScope.ANSWER_UP1.answerID}" <c:if test="${requestScope.ANSWER_UP1.answer_correct}">checked="true"</c:if> size="100" ></br>
            <input type="hidden" name="txta_id2" value="${requestScope.ANSWER_UP1.answerID}">
            Answer 3:  <input type="text" name="txta_context3" value="${requestScope.ANSWER_UP2.answer_context}${param.txta_context3}" size="100"><input type="radio" name="txta" value="${requestScope.ANSWER_UP2.answerID}" <c:if test="${requestScope.ANSWER_UP2.answer_correct}">checked="true"</c:if> size="100" ></br>
            <input type="hidden" name="txta_id3" value="${requestScope.ANSWER_UP2.answerID}">
            Answer 4:  <input type="text" name="txta_context4" value="${requestScope.ANSWER_UP3.answer_context}${param.txta_context4}" size="100"><input type="radio" name="txta" value="${requestScope.ANSWER_UP3.answerID}" <c:if test="${requestScope.ANSWER_UP3.answer_correct}">checked="true"</c:if> size="100" ></br>
            <input type="hidden" name="txta_id4" value="${requestScope.ANSWER_UP3.answerID}">
            Status :
            <select name="cbstatus">
                <option value="true" <c:if test="${requestScope.QUESTION_UP.status or param.cbstatus eq('true')}">selected="selected"</c:if> >Active</option>
                    <option value="false" <c:if test="${!requestScope.QUESTION_UP.status or param.cbstatus eq('false')}">selected="selected"</c:if> >Inactive</option>
            </select>
            Subject :
            <select name="cbsubject" >
                    <option value="1" <c:if test="${requestScope.QUESTION_UP.subID eq('1') or param.cbsubject eq('1')}">selected="selected"</c:if> >Math</option>
                    <option value="2" <c:if test="${requestScope.QUESTION_UP.subID eq('2') or param.cbsubject eq('2')}">selected="selected"</c:if> >PRJ</option>
                    <option value="3" <c:if test="${requestScope.QUESTION_UP.subID eq('3') or param.cbsubject eq('3')}">selected="selected"</c:if> >CEA</option>
            </select>
            <h3>${requestScope.MESS} </h3>
            <input type="submit" value="Save">
        </form>
    </body>
</html>
