(function($) { // this closure helps us keep our variables to ourselves.
// This pattern is known as an "iife" - immediately invoked function expression

	// wait for the DOM (i.e., document "skeleton") to load. This likely isn't
	// necessary for the current case,
	// but may be helpful for AJAX that provides secondary content.
	$(document).ready(function() {
		$("#title-heading").after($('#page-metadata-agingtext'));
	});
	
})(AJS.$ || jQuery);


