<%@ page import="model.AbonamentStatus"%>
<%@page import="model.*, controller.*, utils.*, service.*, dao.*"%>
<%@page import="java.util.*, java.io.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/stiluri.css">
<script src="//code.jquery.com/jquery-2.2.0.min.js"></script>
<!-- <script src="js/scripturi.js"></script> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert abonament</title>
</head>
<body>
<div class="wraper wraper4">
<div >
			<span style="width:100%; font-size:18px; text-align: center; padding-bottom: 10px;">${student.firstName} 
			${student.lastName}  - student abonament</span>
		</div>
		<form action="student" method="POST">
		<input type="hidden" name="action" value="${SAVE_LIBRARY}">
		<input type="hidden" name="id" value="${student.abonament.id}" />
			
		<div class="block">
			<span class="tab">Status:</span>
			<%--${fn:length(enum)}--%>
			<select name="status" class="js_status">
				<option value="0">None/Active/Suspended</option>
				<c:forEach var="enum" items="${enum}">
				<c:choose>
				    <c:when test="${enum == student.abonament.abonamentStatus}">
				       <option value = "${enum}" selected><c:out value="${enum}"/></option>
				    </c:when>
				    <c:otherwise>
				        <option value = "${enum}"><c:out value="${enum}"/></option>
				    </c:otherwise>
				</c:choose>
			</c:forEach>
			</select>

		</div>
		<div class="block">
				<c:choose>
				    <c:when test="${student.abonament.abonamentStatus == 'ACTIVE'}">
				    <fmt:formatDate pattern="dd-MM-yyyy" value="${student.abonament.startDate}" var="fromDate" />
				       <span  class="tab">Start Date:</span><input class="js_start_date" type="text" name="start_date" value="${fromDate}">
				    </c:when>
				    <c:otherwise>
				        <span  class="tab">Start Date:</span><input class="js_start_date" type="text" name="start_date">
				    </c:otherwise>
				</c:choose>
			
		</div>
		
		<div class="block">
			<c:choose>
				    <c:when test="${student.abonament.abonamentStatus == 'ACTIVE'}">
				    <fmt:formatDate pattern="dd-MM-yyyy" value="${student.abonament.endDate}" var="fromDate" />
						<span  class="tab">End Date:</span><input class="js_end_date" type="text" name="end_date" value="${fromDate}">
				    </c:when>
				    <c:otherwise>
						<span  class="tab">End Date:</span><input class="js_end_date" type="text" name="end_date" >
				    </c:otherwise>
				</c:choose>
		</div>
			
		<div class="block">
			<input value="Save" class="js_save2 but3 but7" style="margin-left:150px" type="submit"> 
		</div>
	</form>
	<form action="student">
	<input type = "hidden" name = "action" value = "${LIST}">
	<input class="but4" type="submit" value="Cancel"></form>
</div>
</body>
</html>