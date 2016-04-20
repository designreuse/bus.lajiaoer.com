$(document).ready(function () {
	$.ajax({url:"http://api.lajiaoer.com/rest/city",xhrFields:{
		withCredentials:true
	},
	crossDomain:true});
});