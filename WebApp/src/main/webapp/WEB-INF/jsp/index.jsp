<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Authorization</title>
    <link rel="stylesheet" href="../../css/style.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="../../css/signin.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
    <script src="../../bootstrap/js/bootstrap.js"></script>
</head>
<body>
<div id="login">
    <form class="form-signin" action="/backoffice" method="get">
        <h2 class="form-signin-heading" style="text-align: center">Welcome!</h2>
        <label for="inputEmail" class="sr-only">Email address</label>
        <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <div class="inline-block">
            <label class="remember-me">
                <input type="checkbox" > Remember me
            </label>
            <a href="recover" class="recover-pass">Lost your password?</a>
        </div>
        <button name="signin" class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        <button name="signup" class="btn btn-lg btn-success btn-block" type="button">Sign up</button>
    </form>
</div>
</body>
</html>