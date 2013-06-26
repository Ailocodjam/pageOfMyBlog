/**
*	code by @lgm 2013.5.26
*/
$('.one a').live('click',function(e) {
	$('#profile').slideToggle(300);
	$(this).toggleClass('active');
	return false;
});

$('#nav a').live({
	mouseenter:function(){
		$(this).children().show();
	},
	mouseleave:function(){
		$(this).children().hide();
	}
});

