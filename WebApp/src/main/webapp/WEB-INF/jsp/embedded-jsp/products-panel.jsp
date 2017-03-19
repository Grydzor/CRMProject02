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
                        <i class="fa fa-eur" aria-hidden="true"></i>${product.price}
                    </span>
                </div>
                <div class="inline">
                    <button class="btn btn-primary">
                        <i class="fa fa-shopping-cart fa-2x" aria-hidden="true"></i>
                    </button>
                </div>

            </div>
        </div>
    </div>
    </c:forEach>
    <div>
        <c:if test="${page > 1}">
            <a href="/products?page=${page - 1}" class="btn btn-default">
                <i class="fa fa-arrow-left" aria-hidden="true"></i> TO ${page - 1} page
            </a>
        </c:if>
        <a href="/products?page=${page + 1}" class="btn btn-default">
            TO ${page + 1} page <i class="fa fa-arrow-right" aria-hidden="true"></i>
        </a>
    </div>
</div>
