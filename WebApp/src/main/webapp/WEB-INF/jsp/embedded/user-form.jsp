<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="userMenu">Hello, <span style="text-decoration: underline">${customer.name}</span></a>
<div class="dropdown-menu pull-right" id="userForm">
    <fieldset class="user-form" style="text-align: center">
        <div>
            Go to <a href="/backoffice">BackOffice</a>
        </div>
        <div>
            <button class="btn btn-danger" id="logOut" onclick="logOut()">Log out</button>
        </div>
    </fieldset>
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