/**
 * footer.js 18
 */

$(function() {
	$(window).scroll(function() {
		if ($(this).scrollTop() > 400) {
			$('#move_top_btn').fadeIn();
		} else {
			$('#move_top_btn').fadeOut();
		}
	});
	
	$("#move_top_btn").click(function() {
		$('html, body').animate({
			scrollTop : 0
		}, 400);
		return false;
	});
});