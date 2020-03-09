$(function(){

	var getCurrentUser = function(){
		$.ajax({
			method: "GET",
			url: "getCurrentUser"
		})
		.done(function(response) {
			if(!response){
				$("#status").text("Вход");
				$(".navbar-brand").val("");
				return;
			}
			console.log(response);
			$("#status").text("Изход");
			$(".navbar-brand").text("Здравей, "+response.username)
		});
	}
	//getCurrentUser();

	$("#status").on("click", function(e){
		//e.preventDefault();
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
	
	$("#button").on("click", function(e){
		e.preventDefault();
		var input = $("#input").val();
		if(input === ""){
			return;
		}
		$("#input").val("");
		var url = "http://api.giphy.com/v1/gifs/search?q=";
		var apiKey = "DBFMyEd3Qs9z4ht48oGNqyk7E0GCMMzs";
        $.ajax({
            url: "http://api.giphy.com/v1/gifs/search?q="+
            input +
            "&api_key=DBFMyEd3Qs9z4ht48oGNqyk7E0GCMMzs&limit=32",
            type: "GET",
        })
        .done(function( response ) {
            console.log("Success", response);
            var view = $("#view").text();
            for(var i in response.data){
            	var url = response.data[i].images.original.url;
            	var title = response.data[i].title;
            	var obj = createObject(url, title);
            	$("#result").prepend(obj);
            }
        })
	})
	
    var createObject = function(url, title){
        var $list = $('#view-temp').html();
        $list = $($list);
        $list.find('h5').text(title);
		$list.find('img').attr('src', url);
    	if($("#view").text() === "Grid"){
            $list.addClass("card");
            $list.find('div').filter(":first").addClass("card-body");
            $list.find('img').addClass("card-img-top");
    	} else {
            $list.addClass("media");
            $list.find('img').addClass("align-self-center mr-3");
            $list.find('div').filter(":first").addClass("media-body");
    	}
        return $list;
    }
	
	function changeView(view){
        $('#result').children().each(function () {
        	var $gif = $(this);
        	if(view === "Grid"){
         	    $gif.removeClass("media").addClass("card");
         	    $gif.find("div").filter(":first").removeClass("media-body").addClass("card-body");
         	    $gif.find("img").removeClass("align-self-center mr-3").addClass("card-img-top");
        	} else {
        		$gif.removeClass("card").addClass("media");
        		$gif.find("div").filter(":first").removeClass("card-body").addClass("media-body");
        		$gif.find("img").removeClass("card-img-top").addClass("align-self-center mr-3");
			}
        });
	}	
	
	function toggleView(){
		var view = $("#view").text();
		if(view == "Grid"){
			$("#view").text("List");
		} else {
			$("#view").text("Grid");
		}
		if( $('#result').is(':empty') ) {
			return;
		}
		changeView($("#view").text());	
	}
	
	$('.btn-view').on("click", function(){
	  toggleView();	
	})
	
})



