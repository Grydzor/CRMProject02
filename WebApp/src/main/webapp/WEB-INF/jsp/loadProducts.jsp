<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <%@include file="../../html/load-libraries.html"%>
</head>
<body>
<%@include file="../jsp/embedded-jsp/navigation-bar.jsp"%>
<div class="container">
    <div class="row">
        <c:forEach items="${products}" var="product">
        <%--<img src="${product.img}"/>--%>
        <div class="col-md-3 product">
            <a href="/">${product.name}</a>
            <div class="row">
                <div class="col-sm-6">
                    <span class="price">${product.price} UAH</span>
                </div>
                <button class="btn btn-primary"></button>
            </div>
        </div>
        </c:forEach>
    </div>
</div>
</html>
