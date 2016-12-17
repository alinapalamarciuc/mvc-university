var openFile = function(event) {
	var input = event.target;
	var reader = new FileReader();
	reader.onload = function() {
		var dataURL = reader.result;
		var output = document.getElementById('output');
		output.src = dataURL;
	};
	reader.readAsDataURL(input.files[0]);
};



$(function() {
    $('[name="idStudent"]').change(function() {
    	var check = ($("input:checked").length)-1;
        if (check >= 1) {      	
        	$('#deleteButton').removeAttr('disabled');         
        } else {
        	 $('#deleteButton').attr('disabled', 'disabled');
        }
    });
});

$(document).ready(function(){

	var counter =$('#countInput').val();  
	counter ++;
    $("#addInput").click(function () { 	
    	
    var newTextBoxDiv = $(document.createElement('div')).attr("id", 'TextBoxDiv' + counter, "class", 'blok1');
newTextBoxDiv.after().html('<span></span> <Select class="js_phone_type type" name="phone['+counter+'].phoneType"><option value="0">Phone Type</option><option value="1">Mobile</option><option value="2">Stationar</option></Select><input id="'+counter+'" onkeyup="mask('+counter+', event);" class="js_phone phonenum" name="phone['+counter+'].number" placeholder="Phone number" />');
newTextBoxDiv.appendTo("#TextBoxDiv"+(counter-1));
counter++;
     });
     $("#removeInput").click(function () {
    if(counter==2){         
          return false;
       }   
    counter--;
    var i=2;

        $("#TextBoxDiv" + counter).remove();
     });

   
  });
