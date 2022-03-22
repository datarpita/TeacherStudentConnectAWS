/* $(document).ready(function () {        	
	$("#studentform").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		ajaxPost();
	});
        	
	function ajaxPost() {		
		console.log("In ajaxpost");
		// PREPARE FORM DATA
		var formData = {
			studentRollNo : $("#studentRollNo").val(),
			name : $("#name").val(),
			dob : $("#dob").val(),
			gender : $("#gender").val()			
		}

		console.log('FormData::' + formData);
		// DO POST
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/submitstudent",
			data : JSON.stringify(formData),
			dataType : 'json',
			success : function(result) {
				//console.log(result.status)
				//if (result.status == "success") {
					$("#studentIdDiv")
							.html(
									"<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>"
											+ "New student created: Student Id: " + result.studentid
											+ "</p>");
				//} else {
				//	$("#studentIdDiv").html("<strong>Error</strong>");
				//}
				console.log(result);
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
	}*/
	
	
/*	$("#addstudentbttn").click(function(event){
		console.log("addstudentbttn clicked");
		event.preventDefault();
		
		var id = $(event.target).closest('tr').find(".teacherid").html();
		console.log(id)
		
		$.ajax({
	        type: "GET",
	        url: "/showstudent",
	        data: { 
	            teacherid: $("#teacherid").val()	            
	        },
	        success: function(result) {
	            console.log('ok');
	        },
	        error: function(result) {
	            console.log('error');
	        }
   		});
		
	});*/
//});