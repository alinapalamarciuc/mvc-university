<%@ page import="model.Action"%>
<%@page import="model.*, controller.*, utils.*, service.*, dao.*"%>
<%@page import="java.util.*, java.io.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/stiluri.css">
<script src="//code.jquery.com/jquery-2.2.0.min.js"></script>
<!-- <script src="js/scripturi.js"></script> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Mark</title>
</head>
<body>
<div class="wraper wraper4">
<div >
			<span style="width:100%; font-size:18px; text-align: center; padding-bottom: 10px;">
			Add Mark to student <c:out value = " ${student.firstName} ${student.lastName}"/><%--  (Group: ${student.group.name}) --%></span>
		</div>
	<form action="student" method="POST">
	<input type="hidden" name="action" value="${SAVE_MARK}">
	<input type="hidden" name="id" value="${student.id}" />
								
		
		<div class="block">
			<span class="tab">Discipline:</span> 
			<select id = "disciplinaEl" name = "title" class="js_discipline" onchange="addOption()" >
				<option value="0">Discipline title</option>
				 <script type="text/javascript">
								var title = new Array();
								var profesori = new Array();
								var profesoriId = new Array();
								var countOnChange = new Array();
							</script>
				<c:forEach items="${disciplines}" var="disciplina">
								<option value = "${disciplina.key}">${disciplina.key}</option>
									<c:set var = "profesorName" value = "${disciplina.value}"/>
									<c:out value = "${profesorName}"/>
									<script type="text/javascript">
										title.push('<c:out value="${disciplina.key}"/>');
									</script>
									<script type="text/javascript">
										var profesor = new Array();
										var profesorId = new Array();
									</script>
									<c:forEach items="${disciplina.value}" var="value">
										<script type="text/javascript">
											profesor.push('<c:out value="${value.firstName} ${value.lastName}"/>');
											profesorId.push('<c:out value="${value.id}"/>');
										</script>
									</c:forEach>
									<script type="text/javascript">
										profesori.push(profesor);
										profesoriId.push(profesorId);
									</script>
							</c:forEach> 
							<script type="text/javascript">
								var disciplina = document.getElementById("disciplinaEl");
								function removeOption(length){
									var selectList = document.getElementById("profesor");
									for(j = 1; j <= length-2; j++){
										selectList.removeChild(selectList.childNodes[2]);
									}
								}
								function addOption(){
									if(disciplina.selectedIndex != 0) {
										var numeDisc = disciplinaEl.options[disciplinaEl.selectedIndex].value;
										removeOption(countOnChange[countOnChange.length-1]);
										var selectList = document.getElementById("profesor");
										for(var j = 0; j <= title.length; j++){
											if(title[j] == numeDisc){
												for (var i = 0; i < profesori[j].valueOf(j).length; i++) {
												    var option = document.createElement("option");
												    option.value = profesoriId[j][i];
												    option.text = profesori[j][i];
												    selectList.appendChild(option);
												}
											}
										}
										countOnChange.push(selectList.childNodes.length);
										
									}
								}
							</script>
							 			</select>
		</div>
				
		<div class="block">
			<span class="tab">Professor:</span> 
			<select id = "profesor" name = "profesorId" class="js_professor">
				<option value="0"> Professor name</option>
			</select>
		</div>

		<div class="block">
			<span  class="tab">Mark:</span><input class="js_mark" type="text" name="mark">
		</div>
			
		<div class="block">
			<input value="Save" class="js_save1 but3 but7" style="margin-left:150px" type="submit"> 
		</div>
	</form>
	<form action="student" method="get">
		<input type = "hidden" name = "action" value = "${LIST}">
	<input class="but4" type="submit" value="Cancel"></form>
</div>
</body>
</html>