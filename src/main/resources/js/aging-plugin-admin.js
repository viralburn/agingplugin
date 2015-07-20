(function($) { // this closure helps us keep our variables to ourselves.
// This pattern is known as an "iife" - immediately invoked function expression

	// form the URL
	var url = AJS.contextPath() + "/rest/aging-admin/1.0/";

	// wait for the DOM (i.e., document "skeleton") to load. This likely isn't
	// necessary for the current case,
	// but may be helpful for AJAX that provides secondary content.
	$(document).ready(function() {
		
		// Setup the submit callback.
		AJS.$("#agingadmin").submit(function(e) {
			e.preventDefault();
			updateConfig();
		});
		
		// request the config information from the server
		$.ajax({
			url : url,
			dataType : "json"
		}).done(function(config) { // when the configuration is returned...
			// ...populate the form.
			$("#periodType").val(config.periodType);
			$("#periodAmount").val(config.periodAmount);
			$("#ignoreLabel").val(config.ignoreLabel);
		});
	});
	
	function updateConfig() {
		var url = AJS.contextPath() + "/rest/aging-admin/1.0/";
		AJS.$.ajax({
			url : url,
			type : "PUT",
			contentType : "application/json",
			data : '{ "ignoreLabel": "' + AJS.$("#ignoreLabel").attr("value") + '"'
					+ ', "periodAmount": ' + AJS.$("#periodAmount").attr("value")
					+ ', "periodType": ' + AJS.$("#periodType").attr("value")
					+ ' }',
			processData : false
		});
	}
	
})(AJS.$ || jQuery);


