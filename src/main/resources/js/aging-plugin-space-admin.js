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
			url : url + AJS.params.spaceKey,
			dataType : "json"
		}).done(function(config) { // when the configuration is returned...
			// ...populate the form.
			$("#periodType").val(config.periodType);
			$("#periodAmount").val(config.periodAmount);
			if (config.useMaster == 1) {
				$("#useMaster").attr('checked', true);
			} else {
				$("#useMaster").attr('checked', false);
			}
		});
	});
	
	function updateConfig() {
		var url = AJS.contextPath() + "/rest/aging-admin/1.0/";
		var useMaster = 0;
		if (AJS.$("#useMaster").attr("checked")) {
			useMaster = 1;
		}
		AJS.$.ajax({
			url : url,
			type : "PUT",
			contentType : "application/json",
			data : '{ "spaceKey": "' + AJS.params.spaceKey + '"'
					+ ', "useMaster": ' + useMaster
					+ ', "periodAmount": ' + AJS.$("#periodAmount").attr("value")
					+ ', "periodType": ' + AJS.$("#periodType").attr("value")
					+ ' }',
			processData : false
		});
	}
	
})(AJS.$ || jQuery);


