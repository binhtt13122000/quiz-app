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
    <link rel="stylesheet" href="css/login.css"/>
    <link href='https://fonts.googleapis.com/css?family=Anaheim' rel='stylesheet'>
    <link href='https://fonts.googleapis.com/css?family=Arsenal' rel='stylesheet'>
    <title>FPTU Quiz</title>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col d-none d-md-block">
            <img src="images/banner.png" width="700px" height="550px">
        </div>
        <div class="col">
            <div class="login__content">
                <h1 class="login__title">FPT HCM Quiz Practice</h1>
                <p class="login__description">Place for us to practice exercises and improve skills</p>
                <form class="form mb-3" method="post" action="login">
                    <div class="form-group">
                        <label>Email Address</label>
                        <input name="email" required type="text" class="form-control" value="${param.email}"/>
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input name="password" required type="password" class="form-control" value="${param.password}"/>
                        <c:if test="${requestScope.ERROR != null}">
                            <p style="color: red">${requestScope.ERROR}</p>
                            ${requestScope.remove("ERROR")}
                        </c:if>
                    </div>
                    <div class="d-flex justify-content-start">
                        <input type="submit" class="btn btn-success" value="Sign in"/>
                    </div>
                </form>
                <div class="mb-3">
                    <h3 class="login__subtitle">Or use your FPT Email to Login</h3>
                    <div class="btn--google">
                        <div class="btn-google__icon">
                            <img class="google-icon"
                                 src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"/>
                        </div>
                        <a class="btn-google__text">
                            Sign in with Google
                        </a>
                    </div>
                </div>
                <div class="mb-3">
                    <p>Haven't had account before? <span><a href="#" data-toggle="modal" data-target="#registerModal">Register</a></span>
                    </p>
                    <c:if test="${requestScope.REGISTER_STATUS != null}">
                        <p style="color: ${requestScope.REGISTER_STATUS.equals("register failed!") ? "red": "green"}">${requestScope.REGISTER_STATUS}</p>
                        ${requestScope.remove("REGISTER_STATUS")}
                    </c:if>
                </div>
            </div>
        </div>
        <div class="modal fade registerModal" id="registerModal" tabindex="-1" aria-labelledby="registerModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="registerModalLabel">Register</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form method="post" action="register" id="formRegister">
                        <div class="modal-body">
                            <div class="form-group">
                                <label>Email</label>
                                <input required type="email" class="form-control" name="emailTxt" value="${param.emailTxt}"/>
                            </div>
                            <div class="form-group">
                                <label>Your name</label>
                                <input required class="form-control" name="nameTxt" value="${param.nameTxt}"/>
                            </div>
                            <div class="form-group">
                                <label>Password</label>
                                <input id="passwordTxt" required type="password" class="form-control" name="passwordTxt"
                                       value="${param.passwordTxt}"/>
                            </div>
                            <div class="form-group">
                                <label>Confirm Password</label>
                                <input id="confirmTxt" required type="password" class="form-control" name="confirmTxt"
                                       value="${param.confirmTxt}"/>
                                <span id='message'></span>
                            </div>
                        </div>
                        <div id="messageRegister"></div>
                        <div class="modal-footer">
                            <input type="submit" class="btn btn-success" value="Register"/>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script>
    $('#passwordTxt, #confirmTxt').on('keyup', function () {
        if ($('#passwordTxt').val() == $('#confirmTxt').val()) {
            $('#message').html('Matching').css('color', 'green');
        } else
            $('#message').html('Not Matching').css('color', 'red');
    });

    $("#formRegister").submit(function(e){
        if ($('#passwordTxt').val() != $('#confirmTxt').val()){
            e.preventDefault();
        }
    });
</script>
</body>
</html>


