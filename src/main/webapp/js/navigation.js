'use strict';

var Navigation = function() {

	this.openPage = function(page) {
		$("#page").empty();
		$.ajax({
			url : "page/" + page + ".html",
			type : 'GET',
			dataType : 'html',
			success : $.proxy(function(data) {
				$("#page").empty().html(data);
			}, this), 
			error : this.ajaxError,
		})
	};
	
	this.ajaxError = function(data) {
		alert(data)
	};

};

$(function() {
	var nav = new Navigation();
	$("a[page]").on("click", function() {
		nav.openPage($(this).attr("page"));
	});
});
