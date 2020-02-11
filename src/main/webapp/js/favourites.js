$(function(){

	var getCurrentUser = function(){
		$.ajax({
			method: "GET",
			url: "getCurrentUser"
		})
		.done(function(response) {
			if(!response){
                window.location = "login.html";
				return;
			}
			console.log(response);
			$("#status").text("Изход");
			$(".navbar-brand").text("Здравей, "+response.username)
			loadUserFavourites();
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
	
	loadUserFavourites = function() {
		$.ajax({
			method : "GET",
			url : "getFavourites"
		}).done(
				function(response) {
					for (var i = 0; i < response.length; i++) {
						var favourite = response[i];
						renderObject(favourite.url,
									 favourite.title,
									 favourite.id);
					}
				}).fail(function(response) {
			console.log(response);
		})
	}
	var renderObject = function(url, title, id){
        var $list = $('#view-temp').html();
        $list = $($list);
        $list.find('button').attr('id', id);
        $list.find('h5').text(title);
		$list.find('img').attr('src', url);
        $list.addClass("card");
        $list.find('div').filter(":first").addClass("card-body");
        $list.find('img').addClass("card-img-top");
        $(".container").prepend($list);
    }
	
	$(".remove-all").on("click", function() {
	    $.ajax({
	        method : "POST",
	        url : "removeAllFavourites"
	    }).done(function(response) {
	        console.log(response);
	        $(".container").empty();
		}).fail(function(response) {
			console.log(response);
		})
	})
	
	$(document).on("click", '.remove-favourite', function(e) {
	    $selectedgif = $(this).closest('.list-group-item');
	    var id = $selectedgif.find('button').attr('id');
	    $.ajax({
	        method : "POST",
	        url : "removeFavourite",
	        data : {
	            id : id
	        }
	    }).done(function(response) {
	        console.log(response);
	        $selectedgif.remove();
		}).fail(function(response) {
			console.log(response);
		})
	})
})