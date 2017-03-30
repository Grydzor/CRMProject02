<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Корзина | iShop</title>

      <%@include file="embedded/load-footer-libraries.jsp"%>
  </head>
  <body>

    <%@include file="embedded/menu.jsp"%>
    <div class="product-big-title-area">
        <%--<div class="container">--%>
            <div class="row">
                <div class="col-md-12">
                    <div class="product-big-title text-center">
                        <h2>Корзина</h2>
                    </div>
                </div>
            <%--</div>--%>
        </div>
    </div> <!-- End Page title area -->

    <c:choose>
    <c:when test="${order != null && fn:length(order.items) > 0}">
    <div class="single-product-area">
        <div class="zigzag-bottom"></div>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="product-content-right">
                                    <fieldset>
                                        <table cellspacing="0" class="shop_table cart">
                                            <thead>
                                                <tr>
                                                    <th class="product-remove">&nbsp;</th>
                                                    <th class="product-thumbnail">&nbsp;</th>
                                                    <th class="product-name">Товар</th>
                                                    <th class="product-price">Цена</th>
                                                    <th class="product-quantity">Количество</th>
                                                    <th class="product-subtotal">Итого</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%-- Start cart item --%>
                                                <c:forEach items="${order.items}" var="item">
                                                    <tr class="cart_item">
                                                        <td class="product-remove">
                                                            <a title="Remove this item" class="remove" href="#">×</a>
                                                        </td>

                                                        <td class="product-thumbnail">
                                                            <a href="single-product"><img width="145" height="145" alt="poster_1_up" class="shop_thumbnail" src="../..${item.product.pictureList[0].imageLink}"></a>
                                                        </td>

                                                        <td class="product-name">
                                                            <a href="single-product">${item.product.name} ${item.product.capacity.string} ${item.product.color.string}</a>
                                                        </td>

                                                        <td class="product-price">
                                                            <span class="amount">${item.product.priceVATFormat} грн.</span>
                                                        </td>

                                                        <td class="product-quantity">
                                                            <div class="quantity buttons_added">
                                                                <input id="minus" type="button" class="minus" value="-">
                                                                <input type="text" class="number" size="2" title="Qty" value="1" style="text-align: center">
                                                                <input id="plus" type="button" class="plus" value="+">
                                                            </div>
                                                        </td>

                                                        <td class="product-subtotal">
                                                            <span class="amount">${item.sumVATFormat} грн.</span>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <%-- End cart item --%>
                                                <tr>
                                                    <td colspan="4" style="text-align: right">
                                                        Всего:
                                                    </td>
                                                    <td>
                                                        <b>${order.amount}</b>
                                                    </td>
                                                    <td>
                                                        <b>${order.summary} грн.</b>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="6">
                                                        <button type="submit" onclick="checkout()">Оформить</button>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </fieldset>
                    </div>                    
                </div>
            </div>
        </div>
    </div>
    </c:when>
        <c:otherwise>
            <div class="cart-no-items">
                В корзине нет товаров.
            </div>
        </c:otherwise>
    </c:choose>

    <%@include file="embedded/footer.jsp"%>
    <%@include file="embedded/load-libraries.jsp"%>
  </body>
</html>