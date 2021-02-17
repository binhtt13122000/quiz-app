<%-- 
    Document   : index
    Created on : Jan 26, 2021, 1:00:41 PM
    Author     : binht
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link href='https://fonts.googleapis.com/css?family=Anaheim' rel='stylesheet'>
    <link href='https://fonts.googleapis.com/css?family=Arsenal' rel='stylesheet'>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <title>FPTU Quiz</title>
    <style>
        .main {
            min-height: 80vh;
            width: 80vw;
        }



        @media screen and (max-width: 576px) {
            .main {
                width: 100vw;
            }

            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<header>
    <%@include file="header.jsp" %>
</header>
<main class="row container-fluid mx-auto pt-5 main">
    <div class="d-none d-md-block col-3">
        <div class="card">
            <div class="card-header bg-default">
                <h3><small class="text-white">Main Menu</small></h3>
            </div>
            <div class="mt-2">
                <ul>
                    <c:forEach var="subject" items="${applicationScope.SUBJECTS}">
                        <li style="cursor: pointer"><a href="loadHistory?id=${subject.id}">${subject.id}-${subject.name}</a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    <c:set var="defaultSubject" value="${requestScope.SELECTED_SUBJECT}"/>
    <div class="col-12 col-md-9">
        <div class="container-fluid">
            <h1 class="text-center" style="color:#2dcc70;">${defaultSubject.id} - ${defaultSubject.name}</h1>
            <p class="h5 text-center text-md-left" style="cursor: pointer" id="linkShowTable">Review Quiz <i class="bi bi-box-arrow-down"></i>
            </p>
            <div id="reviewTable" class="d-none">
                <p class="text-muted text-center d-md-none">Review Exercise don't support in Mobile!</p>
                <table class="d-none d-md-block table table-hover table-striped">
                    <thead>
                    <tr>
                        <th scope="col">State</th>
                        <th scope="col">Grade/100.00</th>
                        <th scope="col">Review</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.HISTORY}" var="quiz">
                        <tr>
                            <td>
                                <c:if test="${quiz.point == 0}" var="isReject">
                                    <h5>Reject!</h5>
                                    <p>
                                        Not submitted!
                                    </p>
                                </c:if>
                                <c:if test="${!isReject}">
                                    <h5>Finished!</h5>
                                    <p>
                                        Submitted ${quiz.end}
                                    </p>
                                </c:if>
                            </td>
                            <td>${quiz.point}</td>
                            <td>
                                <c:if test="${!isReject}">
                                    <a href="#">Review</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="dropdown-divider"></div>
            <div>
                <p class="h5 text-center text-md-left">Take Quiz</p>
                <div class="mt-3">
                    <p>Time: ${defaultSubject.timeToTakeQuiz}min</p>
                    <p>Number of questions: ${defaultSubject.totalOfQuestionsPerQuiz}</p>
                </div>
                <div>
                    <button class="btn btn-primary" onclick="location.href='loadQuiz?id=${defaultSubject.id}&quesPerQuiz=${defaultSubject.totalOfQuestionsPerQuiz}&time=${defaultSubject.timeToTakeQuiz}'">Take Quiz</button>
                </div>
            </div>
        </div>
    </div>
</main>
<footer>
    d
</footer>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script>
        $('#linkShowTable').click(function (){
            let className = $('#reviewTable').attr('class');
            console.log(className)
            if(className === 'd-none'){
                $('#reviewTable').removeClass('d-none')
            } else {
                $('#reviewTable').addClass('d-none')
            }
        })
</script>
</body>
</html>


