// opens menu when hover on it
$('#userMenu').hover(
    function () {
        if ($('.dropdown.open').length == 0) {
            $('.dropdown .dropdown-toggle').dropdown("toggle");
            document.getElementById("inputEmail").focus();
        }
    },
    function () {}
);

function logOut() {
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://" + window.location.host + "/logout");
    xhttp.send();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            alert("here");
            document.getElementById("uMenu").innerHTML = this.responseText;
        }
    }
}

var emailRegEx = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
var $inputEmail = $("#inputEmail");
var $inputPassword = $("#inputPassword");

function logIn() {
    if (emailRegEx.test($inputEmail.val()) &&
        $inputPassword.val().length > 5) {

        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "http://" + window.location.host + "/login");
        xhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8');

        var obj = {"email":$inputEmail.val(), "password":$inputPassword.val()};
        xhttp.send(JSON.stringify(obj));
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                alert("here");
                document.getElementById("uMenu").innerHTML = this.responseText;
            }
        };
    }
}

function addToCart(id) {
    var data = {"id":id, "qty":1};
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "http://" + window.location.host + "/addtocart",
        data: JSON.stringify(data),
        timeout: 100000,
        success: function(data) {
            document.getElementById("addToCart" + id).innerHTML = data;
            updateSummary();
        },
        error: function() {
            console.log("error during adding product to cart");
        }
    });
}

function updateSummary() {
    $.ajax({
        type: "POST",
        url: "http://" + window.location.host + "/updateorderdata",
        timeout: 100000,
        success: function(data) {
            var json = JSON.parse(data);
            document.getElementById("summary").innerHTML = json.summary + " грн.";
            document.getElementById("amount").innerHTML = json.amount;
        },
        error: function() {
            console.log("error during receiving summary");
        }
    });
}

$('li.dropdown a').on('click', function (event) {
    $(this).parent().toggleClass('open');
});

$('body').on('click', function (e) {
    if (!$('li.dropdown').is(e.target)
        && $('li.dropdown').has(e.target).length === 0
        && $('.open').has(e.target).length === 0
    ) {
        $('li.dropdown').removeClass('open');
    }
});