<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>iPhone | iShop</title>
    <%@include file="embedded/load-footer-libraries.jsp"%>
  </head>
  <body>
    <%@include file="embedded/menu.jsp"%>
    
    <div class="product-big-title-area">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="product-bit-title text-center">
                        <h2>iPhone</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="single-product-area">
        <div class="zigzag-bottom"></div>
        <div class="container">
            <div class="row">
                <c:forEach items="${products}" var="product">
                <div class="col-md-2">
                    <div class="single-shop-product">
                        <div class="product-upper">
                            <img src="../..${product.pictureList[0].imageLink}" alt="">
                        </div>
                        <h2 style="height: 50px"><a href="">${product.name} ${product.capacity.string} ${product.color.string}</a></h2>
                        <div class="product-carousel-price">
                            ${product.priceVATFormat} грн.
                        </div>
                        <div class="product-option-shop" id="addToCart${product.id}">
                            <%@include file="embedded/elements/buttonCart.jsp"%>
                        </div>
                    </div>
                </div>
                </c:forEach>
            </div>

            <%-- Page numbers --%>
            <div class="row">
                <div class="col-md-12">
                    <div class="product-pagination text-center">
                        <nav>
                            <ul class="pagination">
                                <%-- First page --%>
                                <c:if test="${page > 1}">
                                <li>
                                  <a href="shop" aria-label="Previous">
                                      <i class="fa fa-angle-double-left" aria-hidden="true"></i>
                                  </a>
                                </li>
                                </c:if>
                                    <%-- Before the previous --%>
                                    <c:if test="${page > 2}">
                                        <li><a href="shop?page=${page - 2}">${page - 2}</a></li>
                                    </c:if>
                                    <%-- Previous --%>
                                    <c:if test="${page > 1}">
                                        <li><a href="shop?page=${page - 1}">${page - 1}</a></li>
                                    </c:if>
                                    <%-- Current --%>
                                    <li><span style="background-color: lightgrey; color: black;">${page}</span></li>
                                    <%-- Next --%>
                                    <c:if test="${page < numberOfPages}">
                                        <li><a href="shop?page=${page + 1}">${page + 1}</a></li>
                                    </c:if>
                                    <%-- After the next --%>
                                    <c:if test="${page + 1 < numberOfPages}">
                                        <li><a href="shop?page=${page + 2}">${page + 2}</a></li>
                                    </c:if>
                                    <c:if test="${page < numberOfPages && (numberOfPages - page != 1 && numberOfPages - page != 2)}">
                                    <li>
                                      <a href="shop?page=${numberOfPages}" aria-label="Next">
                                          <i class="fa fa-angle-double-right" aria-hidden="true"></i>
                                      </a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>                        
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="embedded/footer.jsp"%>
    <%@include file="embedded/load-libraries.jsp"%>
  </body>
</html>