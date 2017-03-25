<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>BackOffice</title>
    <%@include file="../../html/load-libraries.html"%>
</head>
<body>
<%@include file="embedded-jsp/navigation-bar.jsp"%>

<div class="row row-centered">
    <fieldset class="acc-details col-centered">
        <h4>My account details:</h4>
        <div class="input-group">
            <span class="input-group-addon"><i style="width: 18px" class="fa fa-envelope"></i></span>
            <label class="form-control" id="regEmail">${customer.email}</label>
        </div>
        <div class="input-group">
            <span class="input-group-addon"><i style="width: 18px" class="fa fa-address-book"></i></span>
            <label class="form-control" id="regName">${customer.name}</label>
        </div>
        <div class="input-group">
            <span class="input-group-addon"><i style="width: 18px" class="fa fa-address-book"></i></span>
            <label class="form-control" id="regSurname">${customer.surname}</label>
        </div>
        <div class="input-group">
            <span class="input-group-addon"><i style="width: 18px" class="fa fa-phone"></i></span>
            <label class="form-control" id="regPhone">${customer.mobile}</label>
        </div>
        <div class="input-group">
            <span class="input-group-addon"><i style="width: 18px" class="fa fa-map-marker"></i></span>
            <label class="form-control" id="regAddress">${customer.address}</label>
        </div>
    </fieldset>
    <fieldset class="acc-details col-centered" >
        <h4>My orders:</h4>
        <c:forEach items="${orders}" var="order">
            <div class="input-group">
                <div class="input-group-addon" style="font-size: 120%"><b>Order #${order.id}</b>:</div>
                <div class="input-group-addon" style="text-align: left">
                    <div>${order.date},</div>
                    <br>
                    <div> total - ${order.amount} item(s),</div>
                    <br>
                    <div> <i class="fa fa-eur" aria-hidden="true"></i>${order.summary}</div>
                </div>
            </div>
        </c:forEach>
    </fieldset>

</div>


</body>
</html>
