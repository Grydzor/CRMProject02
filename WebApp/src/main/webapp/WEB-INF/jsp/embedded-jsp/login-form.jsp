<link rel="stylesheet" href="../../css/sign-in.css" />

<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="navLogin">Login</a>
<div class="dropdown-menu pull-right" id="loginForm">
    <fieldset class="form-signin" name="loginform">
        <div class="input-group">
            <span class="input-group-addon"><i style="width: 18px" class="fa fa-envelope"></i></span>
            <input type="text" class="form-control" id="inputEmail" placeholder="Email">
        </div>
        <div class="input-group">
            <span class="input-group-addon"><i style="width: 18px" class="fa fa-lock fa-lg"></i></span>
            <input type="password" class="form-control" id="inputPassword" placeholder="Password">
        </div>
        <div class="inline-block">
            <label class="remember-me">
                <input type="checkbox"> Remember me
            </label>
            <a href="recover" class="recover-pass">Lost your password?</a>
        </div>
        <button name="signin" class="btn btn-lg btn-primary btn-block" onclick="logIn()">Sign in</button>
    </fieldset>
    <div style="margin-top: 10px">
        Don't have an account?
        <a name="signup" href="/register">Sign up</a>
    </div>
</div>