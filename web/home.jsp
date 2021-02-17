<%-- 
    Document   : index
    Created on : Jan 26, 2021, 1:00:41 PM
    Author     : binht
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

        .disabled-link {
            background-color: darkgray !important;
            color: white !important;
        }

        .main {
            min-height: 80vh;
            width: 80vw;
        }

        @media screen and (max-width: 576px) {
            .main {
                width: 100vw;
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
                    <li style="cursor: pointer"><a
                            href="loadQuestion?page=1&questionContent=${param.questionContent}&subjectId=all&isDeleted=${param.isDeleted}">All
                        Questions</a></li>
                    <c:forEach var="subject" items="${applicationScope.SUBJECTS}">
                        <li style="cursor: pointer">
                            <a
                                    href="loadQuestion?page=1&questionContent=${param.questionContent}&subjectId=${subject.id}&isDeleted=${param.isDeleted}"
                            >
                                    ${subject.id}-${subject.name}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="card mt-4">
            <div class="card-header bg-default">
                <h3><small class="text-white">Search</small></h3>
            </div>
            <div class="card-body">
                <form method="post" action="loadQuestion?page=1&subjectId=${param.subjectId}">
                    <div class="form-group">
                        <label>Search by name</label>
                        <input class="form-control" type="text" name="questionContent"
                               value="${param.questionContent}"/>
                    </div>
                    <div>
                        <c:if test="${param.isDeleted == null || empty param.isDeleted.trim()}" var="checkDelete">
                            <input type="checkbox" class="form-check-inline" name="isDeleted" value="isDeleted"/>
                        </c:if>
                        <c:if test="${!checkDelete}" >
                            <input checked="true" type="checkbox" class="form-check-inline" name="isDeleted" value=""/>
                        </c:if>
                        <label>Deleted</label>
                    </div>
                    <input type="submit" class="btn float-right" style="background-color: #2dcc70; color: white"
                           value="Search"/>
                </form>
            </div>
        </div>
    </div>
    <div class="col-12 col-md-9">
        <div class="d-flex justify-content-between" style="height: 38px">
            <c:if test="${requestScope.TOTAL_PAGE > 1}">
                <nav class="d-none d-md-block" aria-label="Page navigation questions">
                    <ul class="pagination">
                        <li class="page-item ${param.page == 1 ? 'disabled': ''}">
                            <fmt:parseNumber var="pageIndex" value="${param.page > requestScope.TOTAL_PAGE ? '1':param.page}" integerOnly="true"/>
                            <a class="page-link ${param.page == 1 ? 'disabled-link': ''}" style="color: #2dcc70"
                               href="loadQuestion?page=${pageIndex - 1}&questionContent=${param.questionContent}&subjectId=${param.subjectId}&isDeleted=${param.isDeleted}"
                               aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <c:forEach begin="1" end="${requestScope.TOTAL_PAGE}" step="1" varStatus="counter">
                            <li class="page-item"><a
                                    class="page-link pagination-item ${counter.count == pageIndex ? 'page-active': ''}"
                                    href="loadQuestion?page=${counter.count}&questionContent=${param.questionContent}&subjectId=${param.subjectId}&isDeleted=${param.isDeleted}">${counter.count}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item ${param.page == requestScope.TOTAL_PAGE ? 'disabled': ''}">
                            <a class="page-link ${param.page == requestScope.TOTAL_PAGE ? 'disabled-link': ''}"
                               style="color: #2dcc70"
                               href="loadQuestion?page=${pageIndex + 1}&questionContent=${param.questionContent}&subjectId=${param.subjectId}&isDeleted=${param.isDeleted}"
                               aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </c:if>
            <button class="btn" style="background-color: #2dcc70; color: white; border-radius: 25px" data-toggle="modal"
                    data-target="#createModal">
                Create+
            </button>
        </div>
        <c:forEach items="${requestScope.LIST_QUESTION}" var="question">
            <div class="card mt-3">
                <div class="card-header">
                    <div>
                            [${question.id}] ${question.question}
                    </div>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-sm-12 col-md-6 ${question.correctAnswer == 1 ? 'bg-info text-white': ''}">
                            A. ${question.answerA}
                        </div>
                        <div class="col-sm-12 col-md-6 ${question.correctAnswer == 2 ? 'bg-info text-white': ''}">
                            B. ${question.answerB}
                        </div>
                        <div class="col-sm-12 col-md-6 ${question.correctAnswer == 3 ? 'bg-info text-white': ''}">
                            C. ${question.answerC}
                        </div>
                        <div class="col-sm-12 col-md-6 ${question.correctAnswer == 4 ? 'bg-info text-white': ''}">
                            D. ${question.answerD}
                        </div>
                    </div>
                </div>
                <div class="card-footer">
                    <div class="d-flex justify-content-end">
                        <button class="btn btn-primary mr-1" data-toggle="modal" data-target="#updateModal-${question.id}">
                            Update
                        </button>
                        <div class="modal fade" id="updateModal-${question.id}" tabindex="-1" aria-labelledby="updateModal-${question.id}-Label"
                             aria-hidden="true">
                            <div class="modal-dialog modal-xl">
                                <form method="post" action="updateQuestion?id=${question.id}&page=${param.page}&questionContent=${param.questionContent}&subjectId=${param.subjectId}&isDeleted=${param.isDeleted}">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="updateModal-${question.id}-Label">Update Exercise</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="col-sm-12 col-md-6">
                                                    <div class="form-group">
                                                        <label>Question</label>
                                                        <input class="form-control" name="updateQuestion" value="${question.question}"/>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Answer A</label>
                                                        <input class="form-control" name="updateAnswerA" value="${question.answerA}"/>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Answer B</label>
                                                        <input class="form-control" name="updateAnswerB" value="${question.answerB}"/>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Answer C</label>
                                                        <input class="form-control" name="updateAnswerC" value="${question.answerC}"/>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Answer D</label>
                                                        <input class="form-control" name="updateAnswerD" value="${question.answerD}"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-12 col-md-6">
                                                    <div class="form-group">
                                                        <label>Correct Answer</label>
                                                        <select name="updateCorrectAnswer" class="custom-select form-control">
                                                            <c:forEach step="1" begin="1" end="4" varStatus="count">
                                                                <c:if test="${count.count == question.correctAnswer}" var="checkedAnswer">
                                                                    <option selected="selected" value="${count.count}">
                                                                        <sup>&#${count.count + 64};</sup>
                                                                    </option>
                                                                </c:if>
                                                                <c:if test="${!checkedAnswer}">
                                                                    <option value="${count.count}"><sup>&#${count.count + 64};</sup></option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Subject</label>
                                                        <input type="hidden" name="updateSubject" value="${question.subId}"/>
                                                        <input disabled class="form-control" value="${question.subId}" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-success">Update Exercise</button>
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <c:if test="${question.status}">
                            <button class="btn btn-danger" data-toggle="modal" data-target="#deleteModal-${question.id}">
                                Delete
                            </button>
                            <div class="modal fade" id="deleteModal-${question.id}" tabindex="-1" aria-labelledby="deleteModal-${question.id}-Label" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="deleteModal-${question.id}-Label">Delete modal</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            Do you confirm to delete?
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                            <button type="button" class="btn btn-danger" onclick="location.href='deleteQuestion?id=${question.id}&page=${param.page}&questionContent=${param.questionContent}&subjectId=${param.subjectId}&isDeleted=${param.isDeleted}'">Delete</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${!question.status}">
                            <button disabled class="btn btn-danger">
                                Deleted
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="modal fade createModal" id="createModal" tabindex="-1" aria-labelledby="createModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <form method="post" action="createQuestion?page=${param.page}&questionContent=${param.questionContent}&subjectId=${param.subjectId}&isDeleted=${param.isDeleted}">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createModalLabel">Create Exercise</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-sm-12 col-md-6">
                                <div class="form-group">
                                    <label>Question</label>
                                    <input class="form-control" name="question" value="${param.question}"/>
                                </div>
                                <div class="form-group">
                                    <label>Answer A</label>
                                    <input class="form-control" name="answerA" value="${param.answerA}"/>
                                </div>
                                <div class="form-group">
                                    <label>Answer B</label>
                                    <input class="form-control" name="answerB" value="${param.answerB}"/>
                                </div>
                                <div class="form-group">
                                    <label>Answer C</label>
                                    <input class="form-control" name="answerC" value="${param.answerC}"/>
                                </div>
                                <div class="form-group">
                                    <label>Answer D</label>
                                    <input class="form-control" name="answerD" value="${param.answerD}"/>
                                </div>
                            </div>
                            <div class="col-sm-12 col-md-6">
                                <div class="form-group">
                                    <label>Correct Answer</label>
                                    <select name="correctAnswer" class="custom-select form-control">
                                        <option value="1">A</option>
                                        <option value="2">B</option>
                                        <option value="3">C</option>
                                        <option value="4">D</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Choose Subject</label>
                                    <select class="custom-select form-control" name="subject">
                                        <c:forEach items="${applicationScope.SUBJECTS}" var="subject">
                                            <option value="${subject.id}">${subject.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Create Exercise</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</main>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
</body>
</html>


