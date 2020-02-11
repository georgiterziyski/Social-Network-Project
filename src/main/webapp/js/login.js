    $("#login-button").on("click", function(e) {
        e.preventDefault();
	    var email = $("#email").val();
        var password = $("#password").val();
        $.ajax({
            method : "POST",
            url    : "login",
            data   : 
            {
                email 	 : email,
                password : password
            }
        }).done(function(response) {
            window.location = response;
        });
    })


    $("#confirm-register").on("click", function(){
	    $("#register-form").submit();
    })