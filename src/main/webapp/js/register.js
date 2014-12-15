'use strict';

$(function() {
	$("form[role='register']").on("submit", function(e) {
		var form = e.target;
		$.ajax({
			url : "auth/register",
			type : 'POST',
			dataType : 'json',
			contentType : 'application/json',
			data : JSON.stringify({
				user : form['user'].value,
				email : form['email'].value,
				password : form['password'].value
			}),
			success : $.proxy(function(data) {
				alert(data);
				// nav.openPopin('register-succes');
			}, this),
			error : nav.ajaxError,
		});

		return false;
	});
});
