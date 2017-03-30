<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="../../../css/sign-in.css">
<a class="dropdown-toggle" href="#"><i class="fa fa-user"></i>Аккаунт</a>
<div class="dropdown-menu">
    <div>
        <p>
            <input class="input-data" type="email" id="inputEmail" placeholder="Email">
        </p>
        <p>
            <input class="input-data" type="password" id="inputPassword" placeholder="Password">
        </p>
        <p class="lost-pass">
            <a class="login-a" href="/recover">Lost your password?</a>
        </p>
        <p class="btn-login">
            <button class="btn-block" type="submit" onclick="logIn()">Sign in</button>
        </p>
    </div>
    <div class="have-acc">
        Don't have an account? <a class="login-a" href="/register">Sign up</a>
    </div>
</div>

<script>
    $('#userMenu').hover(
        function () {
            if ($('.dropdown.open').length == 0) {
                $('.dropdown .dropdown-toggle').dropdown("toggle");
                document.getElementById("inputEmail").focus();
            }
        },
        function () {}
    );
</script>

