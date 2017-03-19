<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Authorization</title>
    <%@include file="../../html/load-libraries.html"%>
</head>
<body>
<%@include file="embedded-jsp/navigation-bar.jsp"%>
<div style="text-align: center">
    <h1>
        Welcome in our elite Italian store!
    </h1>
    <h4>
        Here you can buy our premium products, enjoy the taste and aroma of its ingredients.
        <br>
        <br>
        So choose it!
    </h4>
</div>
<%@include file="loadProducts.jsp"%>
</body>
</html>
