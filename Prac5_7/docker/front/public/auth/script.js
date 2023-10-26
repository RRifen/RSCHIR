$(document).ready(function () {
    $(".login_button").click((event) => {
        event.preventDefault();
        $.ajax({
            url: 'http://localhost:8081/auth/login',
            type: 'POST',
            data: JSON.stringify({
                login: $("#login_username").val(),
                password: $("#login_password").val()
            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: (data) => {
                if (data.hasOwnProperty('jwt-token')) {
                    localStorage.setItem('jwt-token', data["jwt-token"]);
                    window.location.href = "../market/index.html";
                } else {
                    alert('Invalid credentials');
                }
            }
        });
    });

    $(".register_button").click((event) => {
        event.preventDefault();
        $.ajax({
            url: 'http://localhost:8081/auth/registration',
            type: 'POST',
            data: JSON.stringify({
                login: $("#register_username").val(),
                name: $("#register_name").val(),
                email: $("#register_email").val(),
                password: $("#register_password").val()
            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: (data) => {
                if (data.hasOwnProperty('jwt-token')) {
                    localStorage.setItem('jwt-token', data["jwt-token"]);
                    window.location.href = "../market/index.html";
                } else {
                    alert('Invalid credentials');
                }
            }
        });
    });

    $(".become_seller").click((event) => {
        event.preventDefault();
        $.ajax({
            url: 'http://localhost:8081/auth/seller',
            type: 'POST',
            data: JSON.stringify({
                login: $("#login_username").val(),
                password: $("#login_password").val()
            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: (data) => {
                if (data.hasOwnProperty('jwt-token')) {
                    localStorage.setItem('jwt-token', data["jwt-token"]);
                    window.location.href = "../market/index.html";
                } else {
                    alert('Invalid credentials');
                }
            }
        });
    })
});