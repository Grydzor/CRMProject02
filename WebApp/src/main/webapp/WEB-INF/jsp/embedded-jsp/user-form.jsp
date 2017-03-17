<link rel="stylesheet" href="../../css/logged-in.css" />

<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="navLogin">Logged in as <span style="color: blue">${customer.name}</span></a>
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


