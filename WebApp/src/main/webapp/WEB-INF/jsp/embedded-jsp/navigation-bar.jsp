<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- NAVIGATION BAR --%>
<nav class="navbar navbar-inverse transparent navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a style="color:green" class="navbar-brand" href="/"><i class="fa fa-briefcase" aria-hidden="true"></i> Online Store</a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="#">About</a></li>
            <li><a href="#">Cart</a></li>
            <li id="menu" class="dropdown">
                <c:if test="${customer != null}">
                    <%@include file="user-form.jsp"%>
                </c:if>
                <c:if test="${customer == null}">
                    <%@include file="login-form.jsp"%>
                </c:if>
            </li>
        </ul>
        <form class="navbar-form navbar-right" role="search" method="get" action="/search">
            <div class="input-group">
                <input class="form-control col-lg-1" id="inputSearch" placeholder="Product search" maxlength="50" type="text">
                <div class="input-group-btn">
                    <button class="btn btn-default" type="submit">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </div>
        </form>
    </div>
</nav>
<%-- END NAVIGATION BAR --%>

<script src="../../js/loginHelper.js"></script>