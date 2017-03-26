<link rel="stylesheet" href="../../css/sign-in.css">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<a class="dropdown-toggle" href="#"><i class="fa fa-user"></i>Аккаунт</a>
<div class="dropdown-menu" id="loginForm">
    <fieldset class="form-signin form-group">
        <div class="input-group">
            <input type="email" id="inputEmail" placeholder="Email">
        </div>
        <div class="input-group">
            <input type="password" id="inputPassword" placeholder="Password">
        </div>
        <div class="lost-pass">
            <a href="/recover">Lost your password?</a>
        </div>
        <div class="btn-login">
            <button class="btn-block" type="submit" onclick="logIn()">Sign in</button>
        </div>
    </fieldset>
    <div class="have-acc" style="margin-top: 10px">
        Don't have an account?
        <a name="signup" href="/register">Sign up</a>
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

