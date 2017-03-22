<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cart</title>
    <%@include file="../../html/load-libraries.html"%>
</head>
<body>
<%@include file="embedded-jsp/navigation-bar.jsp"%>

<div class="row row-centered">
    <form class="cart-details col-centered" action="/checkout" method="post">
    <h4>Your cart:</h4>
    <c:forEach items="${order.items}" var="item" varStatus="loop">
        <div class="row row-centered product-small">
            <div class="col-centered">
                <span style="font-size: 130%"><b>${loop.index + 1}</b>. ${item.product.name}</span>
            </div>
            <hr>
            <div class="col-centered">
                <span class="small-price"><i class="fa fa-eur" aria-hidden="true"></i>${item.priceVATFormat}</span>
                 x <input type="text" class="form-control inline small-price" style="width: 60px; margin-bottom: 5px" name="qty" id="qty" placeholder="Qty" value="${item.amount}">
            </div>
        </div>
    </c:forEach>
        <c:if test="${customer == null}">
            <div class="input-group">
                <span class="input-group-addon"><i style="width: 18px" class="fa fa-address-book"></i></span>
                <input type="text" class="form-control" name="name" id="regName" placeholder="Name">
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i style="width: 18px" class="fa fa-phone"></i></span>
                <input type="text" class="form-control" name="phone" id="regPhone" placeholder="Phone number">
            </div>
        </c:if>
        <div class="inline-block">
            <div class="inline" style="text-align: left; padding: 7px 7px 7px 7px">
                <button class="btn-primary btn" type="button">Checkout</button>
            </div>
            <div class="inline" style="text-align: right; padding: 7px 7px 7px 7px">
                <h4 style="text-align: right">Total: <span class="price"><i class="fa fa-eur" aria-hidden="true"></i>${order.summaryFormat}</span> </h4>
            </div>
        </div>
    </form>
</div>
</body>
</html>
