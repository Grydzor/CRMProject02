<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="filter-products-panel.jsp"%>
<div class="row row-centered">
    <c:forEach items="${products}" var="product">
    <%--<img src="${product.img}"/>--%>
    <div class="col-md-3 product col-centered">
        <div class="row row-centered">
            <img src="../../img/product_images/${product.filename}.jpg" height="300px"/>
        </div>
        <div class="row row-centered">
            <a href="#" class="name">${product.name}</a>
        </div>
        <hr>
        <div class="row description">
            <b>Description:</b> <span>${product.description}</span>
        </div>
        <hr>
        <div class="row row-centered">
            <div class="inline-block">
                <div class="inline">
                    <span class="price">
                        <i class="fa fa-eur" aria-hidden="true"></i>${product.priceVATFormat}
                    </span>
                </div>
                <div class="inline">
                    <button class="btn btn-primary" onclick="addToCart('${product.id}', '1')">
                        <i class="fa fa-shopping-cart fa-2x" aria-hidden="true"></i>
                    </button>
                </div>

            </div>
        </div>
    </div>
    </c:forEach>
    <div>
        <c:if test="${page > 1}">
            <a href="/search?page=${page - 1}<c:if test="${query != null}">&q=${query}</c:if>" class="btn btn-default">
                <i class="fa fa-arrow-left" aria-hidden="true"></i> TO ${page - 1} page
            </a>
        </c:if>
        <%-- Temporary! --%>
        <c:if test="${fn:length(products) == 12}">
            <a href="/search?page=${page + 1}<c:if test="${query != null}">&q=${query}</c:if>" class="btn btn-default">
                TO ${page + 1} page <i class="fa fa-arrow-right" aria-hidden="true"></i>
            </a>
        </c:if>
    </div>
</div>