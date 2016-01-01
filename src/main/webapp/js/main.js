$(document).ready(function() {
	
	$('.js-render').click(function (e) {
	  
	  var dot = $('.js-dot-source').val();
	  $('.js-render').prop('disabled', true);
	  
	  $.ajax({
		  url: "getgraph",
		  data: { 'dot' : dot },
		  type: "POST",
		  success: function(data) {
			  var graph = $(".js-svg-graph");
			  graph.empty();
			  var importedSVGRootElement = document.importNode(data.documentElement,true);
			  //append the imported SVG root element to the appropriate HTML element
			  graph.append(importedSVGRootElement);

		  },
		  error: function() {
			  $('#myModal').modal('show');
		  },
		  complete: function() {
			  $('.js-render').prop('disabled', false);
		  }
	  });
	  

	  e.preventDefault();
	  
	});

});