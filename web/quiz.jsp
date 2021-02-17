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
        .pagination-item {
            color: #2dcc70;
        }

        .pagination-item:hover {
            color: #2dcc70;
        }

        .page-active {
            color: white;
            background-color: #2dcc70;
        }

        .page-active:hover {
            color: white;
            background-color: #2dcc70;
        }

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
<body onload="loadTime()">
<header>
    <%@include file="header.jsp" %>
</header>
<main class="row container-fluid flex-column-reverse flex-md-row mx-auto pt-5 main">
    <c:set var="quizId" value="${sessionScope.QUIZ_ID}"/>
    <div class="col-12 col-md-3">
        <div class="card mb-5">
            <div class="card-header bg-default">
                <h3><small class="text-white">Stopwatch</small></h3>
            </div>
            <div class="mt-2 card-body">
                <div>
                    <strong>Time: </strong><span id="timeLabel">0:00</span><span>/${sessionScope.SELECTED_SUBJECT.timeToTakeQuiz}:00</span>
                </div>
                <div>
                    <strong>Answered: </strong> 0/${sessionScope.LIST_QUESTION.size()}
                </div>
                <div class="mt-3">
                    <button class="btn btn-block" style="color: white; background-color: #2dcc70" onclick="submit()">Submit</button>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header bg-default">
                <h3><small class="text-white">Summary</small></h3>
            </div>
            <div class="mt-2">
                <div class="row no-gutters">
                    <c:forEach items="${sessionScope.LIST_QUESTION}" varStatus="counter">
                        <div class="col-3">
                            <div class="card mx-auto mb-2" style="width: 80%">
                                <div class="card-header">
                                </div>
                                <div class="mx-auto">${counter.count}</div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <div class="col-12 col-md-9">
        <h1 class="text-center" style="color:#2dcc70;">${sessionScope.SELECTED_SUBJECT.id} - ${sessionScope.SELECTED_SUBJECT.name}</h1>
        <c:forEach items="${sessionScope.LIST_QUESTION}" var="question" varStatus="count">
            <div class="card mt-3">
                <div class="card-header">
                    <div>
                            ${count.count}, ${question.question}
                    </div>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-12 mb-2">
                            <div id="question-${question.id}-A" onclick="choose('question-${question.id}-A', 'answer-${question.id}', ${count.count}, 1)" class="answer-${question.id} card" style="cursor: pointer; border-radius: 10px">A. ${question.answerA}</div>
                        </div>
                        <div class="col-12 mb-2">
                            <div id="question-${question.id}-B" onclick="choose('question-${question.id}-B', 'answer-${question.id}', ${count.count}, 2)" class="answer-${question.id} card" style="cursor: pointer; border-radius: 10px">B. ${question.answerB}</div>
                        </div>
                        <div class="col-12 mb-2">
                            <div id="question-${question.id}-C" onclick="choose('question-${question.id}-C', 'answer-${question.id}', ${count.count}, 3)" class="answer-${question.id} card" style="cursor: pointer; border-radius: 10px">C. ${question.answerC}</div>
                        </div>
                        <div class="col-12 mb-2">
                            <div id="question-${question.id}-D" onclick="choose('question-${question.id}-D', 'answer-${question.id}', ${count.count}, 4)" class="answer-${question.id} card" style="cursor: pointer; border-radius: 10px">D. ${question.answerD}</div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</main>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script>
    function choose(id, className, index, answer){
        let elements = document.getElementsByClassName(className);
        Array.prototype.map.call(elements, function (el){
            el.classList.remove('bg-info');
            el.classList.remove('text-white');
            return true;
        })
        document.getElementById(id).classList.add('bg-info');
        document.getElementById(id).classList.add('text-white');
        sessionStorage.setItem('${quizId}-' + index, answer);
    }

    function submit(){
        let str = '';
        for(let i = 0; i < ${sessionScope.SELECTED_SUBJECT.totalOfQuestionsPerQuiz}; i++){
            let answer = sessionStorage.getItem('${quizId}-' + (i + 1));
            if(answer === null){
                answer = '0'
            }
            str = str.concat(answer)
        }
        sessionStorage.clear();
        location.href = 'submit?answer=' + str;
    }

    window.addEventListener('beforeunload', function (e) {
        e.preventDefault();
        e.returnValue = '';
    });

    function loadTime(){
        let time = ${requestScope.SELECTED_SUBJECT.timeToTakeQuiz * 60};
        let minute = 0;
        let second = 0;
        let interval = setInterval(function() {
            time--;
            second = second + 1;
            if (second == 60) {
                minute = minute + 1;
                second = 0;
            }
            document.getElementById('timeLabel').innerHTML = "" + minute + ":" + second;
            if (time == 0) {
                clearInterval(interval);
                submit();
            }
        }, 1000)
    }
</script>
</body>
</html>


