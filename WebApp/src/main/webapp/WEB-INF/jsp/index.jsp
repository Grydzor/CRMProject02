<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Authorization</title>
    <link rel="stylesheet" href="../../css/signin.css" />
    <link rel="stylesheet" href="../../css/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../bootstrap/css/bootstrap.css">
    <script src="../../js/jquery.min.js"></script>
    <script src="../../bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
    <%-- NAVIGATION BAR --%>
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <a style="color:green" class="navbar-brand" href="/"><i class="fa fa-briefcase" aria-hidden="true"></i> Online Store</a>
            </div>
            <ul class="nav navbar-nav pull-right">
                <li><a href="#">About</a></li>
                <li><a href="#">Basket</a></li>
                <li class="dropdown" id="loginMenu">
                    <a class="dropdown-toggle" href="#" data-toggle="dropdown" id="navLogin">Login</a>
                    <ul class="dropdown-menu pull-right" aria-labelledby="dLabel" role="menu">
                        <form class="form-signin" method="get" name="loginform">
                            <label for="inputEmail" class="sr-only">Email address</label>
                            <input type="email" id="inputEmail" class="form-control" placeholder="Email address" autofocus>
                            <label for="inputPassword" class="sr-only">Password</label>
                            <input type="password" id="inputPassword" class="form-control" placeholder="Password">
                            <div class="inline-block">
                                <label class="remember-me">
                                    <input type="checkbox"> Remember me
                                </label>
                                <a href="recover" class="recover-pass">Lost your password?</a>
                            </div>
                            <input value="Sign in" name="signin" class="btn btn-lg btn-primary btn-block" type="submit" onclick="this.form.action = '/backoffice'"/>
                            <input value="Sign up" name="signup" class="btn btn-lg btn-success btn-block" type="submit" onclick="this.form.action = '/register'"/>
                        </form>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
    <%-- END NAVIGATION BAR --%>

</body>
</html>
