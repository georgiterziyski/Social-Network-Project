$(function(){

	var getCurrentUser = function(){
		$.ajax({
			method: "GET",
			url: "getCurrentUser"
		})
		.done(function(response) {
			console.log(response);
			$("#status").text("Изход");
			$(".navbar-brand").text("Здравей, "+response.username)
			var $email = $("#email");
            $email.val(response.email);
            var $user = $("#user");
            $user.val(response.username);
			var $pass = $("#password");
            $pass.val(response.password);
            var $rep_pass = $("#repeat-password");
            $rep_pass.val(response.password);
		}).fail(function(response) {
                window.location = "login.html";
				return;
		});
	}
    getCurrentUser();
    
	$("#status").on("click", function(e){
		e.preventDefault();
		if($("#status").val() === "Вход"){
			return;
		}
		$.ajax({
            method : "POST",
			url : "logout",
			data: null
        }).done(function(response) {
			window.location = response;
        });
	})
	
	$("#save").on("click", function(e) {
        e.preventDefault();
	    var email = $("#email").val();
        var password = $("#password").val();
        var username = $("#user").val();
        $.ajax({
            method : "POST",
            url    : "updateUser",
            data   : 
            {
                email 	 : email,
                password : password,
                username : username
            }
        }).done(function(response) {
            window.location = "profile.html";
        }).fail(function(response) {
			console.log(response);
		});
    })
    
    	$("#delete").on("click", function(e) {
        e.preventDefault();
	    var email = $("#email").val();
        var password = $("#password").val();
        $.ajax({
            method : "POST",
            url    : "deleteUser",
            data   : 
            {
                email 	 : email,
                password : password
            }
        }).done(function(response) {
            window.location = "login.html";
        }).fail(function(response) {
			console.log(response);
		});
    })
	
})