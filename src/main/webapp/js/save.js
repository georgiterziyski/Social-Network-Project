
$(document).on("click", '.save-favourite', function(e) {
    $selectedgif = $(this).closest('.list-group-item');
    var url = $selectedgif.find('img').attr('src');
    var title = $selectedgif.find("h5").text();
    $.ajax({
        method : "POST",
        url : "addFavourite",
        data : {
            url   : url,
            title : title
        }
    }).done(function(response) {
        console.log(response);
        $selectedgif.find("button").text("Запазено");
    }).fail(function() {
		window.location = "login.html"
	});
})