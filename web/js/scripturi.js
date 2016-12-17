$(document).ready(function(){

/*---------VALIDARE FORMA DE CAUTARE-------------*/
	var isDobEnd = false;
	var isDobStart = false;
	
	$(".js_dobstart").keyup(function(){
		$(this).removeAttr("style");
		isDobStart = false;
		var l = $(this).val();
		
		var data;
		var luna;
		var anu;
		
		
		if (l.length==2 || l.length==5)
			$(this).val(l+"-");
		
		if(l.length>=2){
			data = parseInt(l.slice(0, 2), 10);
		
			if (data>31){
				$(this).val(l).css("color","red");
				$(this).val(data);
			}
			if (data<10) data = "0"+data;
				if(l.length>=5){
					luna = parseFloat(l.slice(3, 5), 10);
					
					if (luna>12){
						$(this).val(l).css("color","red");
						$(this).val(data+"-"+luna);
					}
					if (luna<10) luna = "0"+luna;
						if(l.length>=10){
							anu = parseInt(l.slice(6,10), 10);
							if (anu > 2020 || anu < 1950){
								$(this).val(l).css("color","red");
							}else{
								isDobStart = true;
							}
							$(this).val(data+"-"+luna+"-"+anu);
						} 
				} 
		}
		if (isNaN(data) || isNaN(luna) || isNaN(anu))
			isDobStart = false;
		
	});
	$(".js_dobend").keyup(function(){
		$(this).removeAttr("style");
		isDobEnd = false;
		var l = $(this).val();
		
		var data;
		var luna;
		var anu;
		
		
		if (l.length==2 || l.length==5)
			$(this).val(l+"-");
		
		if(l.length>=2){
			data = parseInt(l.slice(0, 2), 10);
		
			if (data>31){
				$(this).val(l).css("color","red");
				$(this).val(data);
			}
			if (data<10) data = "0"+data;
				if(l.length>=5){
					luna = parseFloat(l.slice(3, 5), 10);
					
					if (luna>12){
						$(this).val(l).css("color","red");
						$(this).val(data+"-"+luna);
					}
					if (luna<10) luna = "0"+luna;
						if(l.length>=10){
							anu = parseInt(l.slice(6,10), 10);
							if (anu > 2020 || anu < 1950){
								$(this).val(l).css("color","red");
							}else{
								isDobEnd = true;
							}
							$(this).val(data+"-"+luna+"-"+anu);
						} 
				} 
		}
		if (isNaN(data) || isNaN(luna) || isNaN(anu))
			isDobEnd = false;
		
	});
	
	$(".checkall").change(function () {
	    $("input:checkbox").prop('checked', $(this).prop("checked"));
	});	
	
/*---------VALIDARE FORMA DE CAUTARE-------------*/	
	
/*-----------VALIDARE FORMA DE ADAUGARE---------------*/	
var isFirstName = false;
var isLastName = false;
var isDob = false;
var isGender = false;
var isCountry = false;
var isCity = false;
var isAddress = false;
var isPhoneType = false;
var isPhone = false;
var isGroup = false;

	$(".js_group").click(function(){
		$(this).removeAttr("style");
		isGroup = false;
		var l = $(this).val();
		if(l!="0"){
			isGroup = true;
		}else{
			$(this).val(l).css("color","red");
		}
	});

	$(".js_phone").keyup(function(){
		$(this).removeAttr("style");
		isPhone = false;
		var l = $(this).val();
		if((l.length>=3)&&(l.length<=15)){
			l = parseInt(l, 10);
			$(isNaN(l))
				isPhone = true;
		}else{
			$(this).val(l).css("color","red");
		}
	});

	$(".js_phone_type").click(function(){
		$(this).removeAttr("style");
		isPhoneType = false;
		var l = $(this).val();
		if(l!="0"){
			isPhoneType = true;
		}else{
			$(this).val(l).css("color","red");
		}
	});

	$(".js_address").keyup(function(){
		$(this).removeAttr("style");
		isAddress = false;
		var l = $(this).val();
		if((l.length>=3)&&(l.length<=20)){
			isAddress = true;
		}else{
			$(this).val(l).css("color","red");
		}
	});

	$(".js_city").keyup(function(){
		$(this).removeAttr("style");
		isCity = false;
		var l = $(this).val();
		if((l.length>=3)&&(l.length<=20)){
			isCity = true;
		}else{
			$(this).val(l).css("color","red");
		}
	});

	$('.js_gender').click(function(){
		isGender = true;
		$(".gender").removeAttr("style");
	});
	
	$(".js_country").keyup(function(){
		$(this).removeAttr("style");
		$(".gender").removeAttr("style");
		isCountry = false;
		
		if (!isGender){
			$(".gender").css("color","red");
		}
		var l = $(this).val();
		if((l.length>=3)&&(l.length<=20)){
			isCountry = true;
		}else{
			$(this).val(l).css("color","red");
		}
	});
	
	$(".js_firstName").keyup(function(){
		$(this).removeAttr("style");
		isFirstName = false;
		var l = $(this).val();
		if((l.length>=3)&&(l.length<=20)){
			isFirstName = true;
		}else{
			$(this).val(l).css("color","red");
		}
	});
	
	$(".js_lastName").keyup(function(){
		$(this).removeAttr("style");
		isLastName = false;
		var l = $(this).val();
		if((l.length>=3)&&(l.length<=20)){
			isLastName = true;
		}else{
			$(this).val(l).css("color","red");
		}
	});
	
	$(".js_dob").keyup(function(){
		$(this).removeAttr("style");
		isDob = false;
		var l = $(this).val();
		
		var data;
		var luna;
		var anu;
		
		
		if (l.length==2 || l.length==5)
			$(this).val(l+"-");
		
		if(l.length>=2){
			data = parseInt(l.slice(0, 2), 10);
		
			if (data>31){
				$(this).val(l).css("color","red");
				$(this).val(data);
			}
			if (data<10) data = "0"+data;
				if(l.length>=5){
					luna = parseFloat(l.slice(3, 5), 10);
					
					if (luna>12){
						$(this).val(l).css("color","red");
						$(this).val(data+"-"+luna);
					}
					if (luna<10) luna = "0"+luna;
						if(l.length>=10){
							anu = parseInt(l.slice(6,10), 10);
							if (anu > 2020 || anu < 1950){
								$(this).val(l).css("color","red");
							}else{
								isDob = true;
							}
							$(this).val(data+"-"+luna+"-"+anu);
						} 
				} 
		}
		if (isNaN(data) || isNaN(luna) || isNaN(anu))
			isDob = false;
		
	});
	
	//cind apasam pe SAVE verificam forma
	$(".js_save").click(function(e){
	    if (!isDob || !isFirstName || !isLastName || !isGender || !isCountry || !isCity || !isAddress || !isPhoneType || !isPhone || !isGroup){
	    	e.preventDefault();	
	    	if (!isDob) $(".js_dob").css("color","red");
	    	if (!isFirstName) $(".js_firstName").css("color","red");
	    	if (!isLastName) $(".js_lastName").css("color","red");
	    	if (!isGender) $(".gender").css("color","red");
	    	if (!isCountry) $(".js_country").css("color","red");
	    	if (!isCity) $(".js_city").css("color","red");
	    	if (!isAddress) $(".js_address").css("color","red");
	    	if (!isPhoneType) $(".js_phone_type").css("color","red");
	    	if (!isPhone) $(".js_phone").css("color","red");
	    	if (!isGroup) $(".js_group").css("color","red");
	    }
	  });
/*-----------VALIDARE FORMA DE ADAUGARE---------------*/
	  /*-----------VALIDARE FORMA DE ADAUGARE NOTA---------------*/	
	  var isDiscipline = false;
	  var isProfessor = false;
	  var isMark = false;

	  	$(".js_discipline").click(function(){
	  		$(this).removeAttr("style");
	  		isDiscipline = false;
	  		var l = $(this).val();
	  		if(l!="0"){
	  			isDisipline = true;
	  		}else{
	  			$(this).val(l).css("color","red");
	  		}
	  	});
	  	
		$(".js_professor").click(function(){
	  		$(this).removeAttr("style");
	  		isProfessor = false;
	  		var l = $(this).val();
	  		if(l!="0"){
	  			isProfessor = true;
	  		}else{
	  			$(this).val(l).css("color","red");
	  		}
	  	});

		$(".js_mark").keyup(function(){
			$(this).removeAttr("style");
			isMark = false;
			var l = $(this).val();
			if(l.length<=4){
				l = parseInt(l, 10);
				$(isNaN(l))
					isMark = true;
			}else{
				$(this).val(l).css("color","red");
			}
		});
		
	  	//cind apasam pe SAVE verificam forma
	  	$(".js_save1").click(function(e){
	  	    if (!isDiscipline || !isProfessor || !isMark ){
	  	    	e.preventDefault();	
	  	    	if (!isDiscipline) $(".js_discipline").css("color","red");
	  	    	if (!isProfessor) $(".js_professor").css("color","red");
	  	    	if (!isMark) $(".js_mark").css("color","red");
	  	    	
	  	    }
	  	  })
/*-----------VALIDARE FORMA DE ADAUGARE NOTA---------------*/
/*-----------VALIDARE FORMA DE ADAUGARE ABONAMENT---------------*/	
		  var isStatus = false;
		  var isStartDate = false;
		  var isEndDate = false;

		  	$(".js_status").click(function(){
		  		$(this).removeAttr("style");
		  		isStatus = false;
		  		var l = $(this).val();
		  		if(l!="0"){
		  			isStatus = true;
		  		}else{
		  			$(this).val(l).css("color","red");
		  		}
		  	});
			
			$(".js_start_date").keyup(function(){
				$(this).removeAttr("style");
				isStartDate = false;
				var l = $(this).val();
				
				var data;
				var luna;
				var anu;
				
				
				if (l.length==2 || l.length==5)
					$(this).val(l+"-");
				
				if(l.length>=2){
					data = parseInt(l.slice(0, 2), 10);
				
					if (data>31){
						$(this).val(l).css("color","red");
						$(this).val(data);
					}
					if (data<10) data = "0"+data;
						if(l.length>=5){
							luna = parseFloat(l.slice(3, 5), 10);
							
							if (luna>12){
								$(this).val(l).css("color","red");
								$(this).val(data+"-"+luna);
							}
							if (luna<10) luna = "0"+luna;
								if(l.length>=10){
									anu = parseInt(l.slice(6,10), 10);
									if (anu > 2020 || anu < 1950){
										$(this).val(l).css("color","red");
									}else{
										isStartDate = true;
									}
									$(this).val(data+"-"+luna+"-"+anu);
								} 
						} 
				}
				if (isNaN(data) || isNaN(luna) || isNaN(anu))
					isStartDate = false;
				
			});
			$(".js_end_date").keyup(function(){
				$(this).removeAttr("style");
				isEndDate = false;
				var l = $(this).val();
				
				var data;
				var luna;
				var anu;
				
				
				if (l.length==2 || l.length==5)
					$(this).val(l+"-");
				
				if(l.length>=2){
					data = parseInt(l.slice(0, 2), 10);
				
					if (data>31){
						$(this).val(l).css("color","red");
						$(this).val(data);
					}
					if (data<10) data = "0"+data;
						if(l.length>=5){
							luna = parseFloat(l.slice(3, 5), 10);
							
							if (luna>12){
								$(this).val(l).css("color","red");
								$(this).val(data+"-"+luna);
							}
							if (luna<10) luna = "0"+luna;
								if(l.length>=10){
									anu = parseInt(l.slice(6,10), 10);
									if (anu > 2020 || anu < 1950){
										$(this).val(l).css("color","red");
									}else{
										isEndDate = true;
									}
									$(this).val(data+"-"+luna+"-"+anu);
								} 
						} 
				}
				if (isNaN(data) || isNaN(luna) || isNaN(anu))
					isEndDate = false;
				
			});
			
		  	//cind apasam pe SAVE verificam forma
		  	$(".js_save2").click(function(e){
		  	    if (!isStatus || !isStartDate || !isEndDate ){
		  	    	e.preventDefault();	
		  	    	if (!isStatus) $(".js_status").css("color","red");
		  	    	if (!isStartDate) $(".js_start_date").css("color","red");
		  	    	if (!isEndDate) $("js_end_date").css("color","red");
		  	    	
		  	    }
		  	  })
		  	  
	
			$(".js_delete").click(function(e){
				var i = confirm("Confirm delete");
				if (!i)
					e.preventDefault();
				
			});

 /*-----------VALIDARE FORMA DE ADAUGARE ABONAMENT---------------*/

//	var heightWraptab = $(".wraptab").css("height");
//	$(".wraptab").css("height", "122px");
//	$(".wraptab").animate({'height':heightWraptab}, 2000);
});