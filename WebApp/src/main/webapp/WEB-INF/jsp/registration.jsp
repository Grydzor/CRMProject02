<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration | iShop</title>
    <%@include file="embedded/load-footer-libraries.jsp"%>
    <link rel="stylesheet" href="../../css/sign-up.css">
</head>
<body>
<%@include file="embedded/menu.jsp"%>

<fieldset class="form-horizontal" id="reg-form">
    <div class="input-group">
        Пожалуйста, заполните поля для успешной регистрации:
    </div>
    <div>
        <div class="input-group">
            <span class="input-group-addon"><i style="width: 18px" class="fa fa-envelope"></i></span>
            <input type="text" name="email" id="regEmail" placeholder="Электронная почта" autofocus>
        </div>
    </div>

    <div class="input-group">
        <span class="input-group-addon"><i style="width: 18px" class="fa fa-lock"></i></span>
        <input type="password" name="password" id="regPassword" placeholder="Пароль">
    </div>
    <span style="color: rgba(0,0,0,0.5)">
        <i style="margin: 0 28px 0 20px;" class="fa fa-info" aria-hidden="true"></i>
        минимум 6 символов
    </span>
    <div class="input-group">
        <span class="input-group-addon"><i style="width: 18px" class="fa fa-lock"></i></span>
        <input type="password" name="confirmPassword" id="confirmRegPassword" placeholder="Подтвердите пароль">
    </div>
    <span id="passwordMismatch" style="color: rgba(255,0,0,0.5); display: none">
        <i style="margin: 0 23px 0 15px;" class="fa fa-exclamation-triangle" aria-hidden="true"></i>
        пароли не совпадают
    </span>
    <div class="input-group">
        <span class="input-group-addon"><i style="width: 18px" class="fa fa-address-book"></i></span>
        <input type="text" name="name" id="regName" placeholder="Имя">
    </div>
    <div class="input-group">
        <span class="input-group-addon"><i style="width: 18px" class="fa fa-address-book"></i></span>
        <input type="text" name="surname" id="regSurname" placeholder="Фамилия">
    </div>
    <div class="input-group">
        <span class="input-group-addon"><i style="width: 18px" class="fa fa-phone"></i></span>
        <input type="text" name="phone" id="regPhone" placeholder="Номер телефона">
    </div>
    <span style="color: rgba(0,0,0,0.5)">
        <i style="margin: 0 25px 0 15px" class="fa fa-question-circle-o" aria-hidden="true"></i>
        012-345-6789 or just 0123456789
    </span>
    <div class="input-group">
        <span class="input-group-addon"><i style="width: 18px" class="fa fa-map-marker"></i></span>
        <input type="text" name="address" id="regAddress" placeholder="Адрес">
    </div>
    <span style="color: rgba(0,0,0,0.5)">
        <i style="margin: 0 25px 0 15px" class="fa fa-question-circle-o" aria-hidden="true"></i>
        ул. Васильковская, 7, Киев, 02000
    </span>

    <div class="form-group">
        <button onclick="checkFields()">Зарегистрироваться</button>
    </div>
</fieldset>
<div style="margin-top: 50px; text-align: center" id="regStatus"></div>

<script>
    var emailRegEx = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var phoneRegEx = /^\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})$/;

    var $regEmail = $("#regEmail");
    var $regPhone = $("#regPhone");
    var $regPassword = $("#regPassword");
    var $confirmRegPassword = $("#confirmRegPassword");
    var $passwordMismatch = $("#passwordMismatch");
    var $regName = $("#regName");
    var $regSurname = $("#regSurname");
    var $regAddress = $("#regAddress");

    function isEmpty(el) {
        $(el).css("border-color", el.value.length > 0 ? "#67b168" : "rgba(255,0,0,0.5)");
    }
    $regEmail.blur(function () {
        $(this).css("border-color", emailRegEx.test(this.value) ? "#67b168" : "rgba(255,0,0,0.5)");
    });
    $regPhone.blur(function () {
        $(this).css("border-color", phoneRegEx.test(this.value) ? "#67b168" : "rgba(255,0,0,0.5)");
    });
    $regPassword.blur(function () {
        $(this).css("border-color", this.value.length > 5 ? "#67b168" : "rgba(255,0,0,0.5)");
    });
    $confirmRegPassword.blur(function () {
        if (this.value.length > 5) {
            if ($regPassword.val() == $confirmRegPassword.val()) {
                $(this).css("border-color", "#67b168");
                $passwordMismatch.css("display", "none");
            } else {
                $(this).css("border-color", "rgba(255,0,0,0.5)");
                $passwordMismatch.css("display", "block");
            }
        } else {
            $(this).css("border-color", "rgba(255,0,0,0.5)");
            $passwordMismatch.css("display", "none");
        }
    });
    $regName.blur(function () {isEmpty(this)});
    $regSurname.blur(function () {
        isEmpty(this)});
    $regAddress.blur(function () {isEmpty(this)});

    function checkFields() {
        if (emailRegEx.test($regEmail.val()) &&
                phoneRegEx.test($regPhone.val()) &&
                $regPassword.val().length > 5 &&
                $confirmRegPassword.val().length > 5 &&
                $confirmRegPassword.val() == $regPassword.val() &&
                $regName.val().length > 0 &&
                $regSurname.val().length > 0 &&
                $regAddress.val().length > 0) {

            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "http://" + window.location.host + "/finishReg");
            xhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            var obj = {"email":$regEmail.val(),
                       "password":$regPassword.val(),
                       "confirmPassword":$confirmRegPassword.val(),
                       "name":$regName.val(),
                       "surname":$regSurname.val(),
                       "phone":$regPhone.val(),
                       "address":$regAddress.val()};
            xhttp.send(JSON.stringify(obj));
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    $("#reg-form").hide();
                    document.getElementById("regStatus").innerHTML = this.responseText;
                }
            };
        }
    }
</script>

<%@include file="embedded/load-libraries.jsp"%>
<%@include file="embedded/footer.jsp"%>
</body>
</html>
