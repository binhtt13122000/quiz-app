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
    <link rel="stylesheet" href="css/header.css" type="text/css">
</head>
<body>
<c:set value="${sessionScope.USER != null}" var="isLoggedIn" />
<c:set value="${isLoggedIn ? sessionScope.USER.name.charAt(0) : 'H'}" var="avatar" />
<nav class="navbar navbar-expand-lg navbar-dark bg-default">
    <a class="navbar-brand" href="#">FPT University</a>

    <button class="navbar-toggler"type="button">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="d-none d-xl-flex justify-content-end collapse navbar-collapse">
        <ul class="navbar-nav">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <img src="https://s3.eu-central-1.amazonaws.com/bootstrapbaymisc/blog/24_days_bootstrap/fox.jpg" width="40" height="40" class="rounded-circle">
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="#">History</a>
                    <a class="dropdown-item" href="logout">Log Out</a>
                </div>
            </li>
        </ul>
    </div>

</nav>
</body>
</html>
