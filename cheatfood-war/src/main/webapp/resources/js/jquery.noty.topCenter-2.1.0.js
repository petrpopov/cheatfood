;(function($) {

	$.noty.layouts.topCenter = {
		name: 'topCenter',
		options: { // overrides options

		},
		container: {
			object: '<ul id="noty_topCenter_layout_container" />',
			selector: 'ul#noty_topCenter_layout_container',
			style: function() {
				$(this).css({
					top: 20,
					left: 0,
					position: 'fixed',
					width: '710px',
					height: 'auto',
					margin: 0,
                    "margin-top": 40,
					padding: 0,
					listStyleType: 'none',
					zIndex: 10000000
				});

				$(this).css({
					left: ($(window).width() - $(this).outerWidth(false)) / 2 + 'px'
				});
			}
		},
		parent: {
			object: '<li />',
			selector: 'li',
			css: {}
		},
		css: {
			display: 'none',
			width: '710px'
		},
		addClass: ''
	};

})(jQuery);