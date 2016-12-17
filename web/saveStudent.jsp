<%@page import="model.*, controller.*, utils.*, service.*, dao.*"%>
<%@page import="java.util.*, java.io.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/stiluri.css">
<script src="//code.jquery.com/jquery-2.2.0.min.js"></script>
<!--  <script src="js/scripturi.js"></script> -->
<script src="js/script.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add/Save</title>
</head>
<body>
<div class="wraper wraper2">
<form action = "student" method = "POST" enctype="multipart/form-data" >
			        	<input type = "hidden" name = "action" value = "${SAVE}">
			        	<input type = "hidden" name = "id" value = "${newStudent.id}"/>
	<div class="photo">
		<div class="wrapphoto">
			<c:choose>
			<c:when test="${newStudent.picture != null}">
				<img id="output" class="js_photo photouser" src="image?id=${newStudent.id}" name="image">
			</c:when>
			<c:otherwise>
				<img id="output" class="js_photo photouser" src="image/f.png" name="image">
			</c:otherwise>
			</c:choose>
			
		</div>
	</div>		
							
		<div class="block">
			<span>First Name:</span><input class="js_firstName" type="text" name="firstName" value="${newStudent.firstName}">
			<span class="tab">Group:</span> 
			<select name="group" class="js_group">
				<option value="0">Group Title</option>
					<c:forEach var="groupId" items="${groups}">
					<c:choose>
					    <c:when test="${newStudent.group == groupId.id}">
					       <option value = "${groupId.id}" selected><c:out value="${groupId.name}"/></option>
					    </c:when>
					    <c:otherwise>
					        <option value = "${groupId.id}"><c:out value="${groupId.name}"/></option>
					    </c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
		
		<div class="block">
			<span>Last Name:</span><input class="js_lastName" type="text" name="lastName" value="${newStudent.lastName}">
		</div>
		
		<div class="block">
			<span>Data of Birth:</span><input type="text" name="dob" class="js_dob" placeholder="31-12-2016" value="${newStudent.dob}">
		</div>1
		
		<div class="block">
			<span>Gender:</span> 
				<c:choose>
					<c:when test="${newStudent.gender == 'M'}">
						<input type="radio" class="js_gender"  name="gender" value="M" checked><span class="gender"> male</span>
						<input name="gender" value="F" class="js_gender" type="radio"><span class=" gender"> female</span> 
					</c:when>
					<c:otherwise>
						<input type="radio" class="js_gender"  name="gender" value="M"><span class="gender"> male</span>
						<input name="gender" value="F" class="js_gender" type="radio" checked><span class=" gender"> female</span> 
					</c:otherwise>
				</c:choose>	
		</div>
		
		<div class="block">
			<span>Country:</span><input class="js_country" type="text" name="country" value="${newStudent.country}">
		</div>
		
		<div class="block">
			<span>City:</span><input type="text" class="js_city" name="city" value="${newStudent.city}">
		</div>
		
		<div class="block">
			<span>Address Line:</span><input type="text" class="js_address" name="address" value="${newStudent.address}">
		</div>
		
		<div class="block" id='TextBoxDiv1'>
			<span>Phone:</span> 
			
			<select  class="js_phone_type type" name="phone[${idx.index+1}].phoneType" id="selectPhoneType">
				<option value="0">Phone Type</option>
				
				<c:forEach var="type" items="${phoneTypes}">
								<c:choose>
									<c:when test="${newStudent.phones[0].type.id == type.id}">
										<option value="${type.id}" selected><c:out
												value="${type.phoneNameType}" /></option>
									</c:when>
									<c:otherwise>
										<option value="${type.id}"><c:out
												value="${type.phoneNameType}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
			</select> 

			<input type="text" id="${idx.index+1}"   class="js_phone phonenum" onkeyup="mask(addPhone, event);" placeholder="Phone number" name="phone[${idx.index+1}].number" value="${newStudent.phones[0].number}">	
			<input type="button"  class="phonesplus" id='addInput' value="+">
			<input type="button"  class="phonesminus" id='removeInput' value="-">
			<input type="hidden" id='countInput' value="${fn:length(newStudent.phones)}">
			<input type="file" name="picture" id="picture" onchange="openFile(event)">
		</div>
		<c:if test="${fn:length(newStudent.phones)>=2}">
		<c:forEach var="phone" items="${newStudent.phones}" varStatus="idx" begin="1">
			<div class="block1" id='TextBoxDiv${idx.index+1}'>
			<span></span> 
				<select  class="js_phone_type type" name="phone[${idx.index+1}].phoneType" id="selectPhoneType">
					<option value="0">Phone Type</option>				
					<c:forEach var="type" items="${phoneTypes}">
						<c:choose>
							<c:when test="${phone.type.id == type.id}">
								<option value="${type.id}" selected><c:out
										value="${type.phoneNameType}" /></option>
							</c:when>
							<c:otherwise>
								<option value="${type.id}"><c:out
									value="${type.phoneNameType}" /></option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
				<input type="text" id="${idx.index+1}"   class="js_phone phonenum" onkeyup="mask(addPhone, event);" placeholder="Phone number" name="phone[${idx.index+1}].number" value="${phone.number}">
			</div>
		</c:forEach> 
		</c:if>
		<div class="block">	
			
		</div>
		<input type = "submit" class='js_save but3 but7' value = "Save"/>
</form>		
	<form action="student">
	<input type = "hidden" name = "action" value = "${LIST}">
	<input class="but4" type="submit" value="Cancel"></form>
</div>
</body>
</html>