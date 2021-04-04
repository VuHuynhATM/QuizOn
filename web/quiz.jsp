<%-- 
    Document   : quiz
    Created on : Jan 30, 2021, 2:21:53 PM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QUIZ Page</title>
        <script language="javascript">
            var m = null;
            var s = null;

            var timeout = null;

            function start()
            {
                if (m === null)
                {
                    m = parseInt(document.getElementById('mg').innerHTML);
                    s = parseInt(document.getElementById('sg').innerHTML);
                }
                if (s === -1) {
                    m -= 1;
                    s = 59;
                }
                if (m === -1) {
                    window.location = "DoQuizController?btnaction=Submit";
                    clearTimeout(timeout);
                }

                document.getElementById('mg').innerText = m.toString();
                document.getElementById('sg').innerText = s.toString();

                timeout = setTimeout(function () {
                    s--;
                    start();
                }, 1000);
            }
        </script>
    </head>
    <body onload="start()">
        <h1>QUIZ NOT LET
            <font style="float: right; color: blue">${sessionScope.LOGIN_USER.name}</font>
        </h1>
        </br>
        <h2>${sessionScope.NameQuiz}</h2>
        <div style="text-align: center; size: 508px">
            <span id="mg">${requestScope.M}</span> :
            <span id="sg">${requestScope.S}</span>
        </div>
        <form action="DoQuizController" method="POST">
            <font style="font-size: 30px">Question ${sessionScope.indexquiz}: ${requestScope.QuizDetail.ques.questio_context}</font>
            </br>
            <c:forEach var="ans" items="${requestScope.QuizDetail.ques.list}">
                <input type="radio" name="rdans" value="${ans.answerID}" <c:if test="${requestScope.QuizDetail.yourChoice eq(ans.answerID)}">checked="true"</c:if> ><font>${ans.answer_context}</font></br>
            </c:forEach>
            
            <input type="submit" name="action" value="previous">
            <input type="hidden" name="index_pre" value="${sessionScope.indexquiz-1}">
            
            <c:forEach var="i" begin="1" end="${sessionScope.NUM_QUES}">
                <input type="submit" name="index" value="${i}">
            </c:forEach>
            
            <input type="submit" name="action"  value="next">
            <input type="hidden" name="index_next" value="${sessionScope.indexquiz+1}">
            
            </br>
            </br><input type="submit" name="btnaction" value="Submit">
        </form>
    </body>
</html>
