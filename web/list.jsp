<%@ page import="model.Action"%>
<%@ page import="model.*, controller.*, service.*, dao.*"%>
<%@ page import="java.util.*, java.io.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/stiluri.css">
<script src="//code.jquery.com/jquery-2.2.0.min.js"></script>
<script src="js/scripturi.js"></script>
<script src="js/script.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista studentilor</title>

</head>
<body>
<c:forEach var="error" items="${errors}">
		<c:out value="${error.value}" />
		</br>
	</c:forEach>
	<div class="wraper">
		<div class="search">
			<form action="student" method="POST">
				<input type="hidden" name="action" value="${LIST}">

				<div class="block">
					<span>Name:</span><input type="text" name="partialName" placeholder="Partial name" value="${search.name}">		
					<span class="tab">Date of birth:</span><input type="text" name="dobStart" class="js_dobstart"
						placeholder="Start Date 01-01-2016" value="${search.dataOfBirthStart}">
					<input type="text" name="dobEnd" class="js_dobend" placeholder="End Date 31-12-2016" value="${search.dataOfBirthEnd}">
				</div>
				<div class="block">
					<span>Address:</span>
					<input type="text" name="partialAdres" placeholder="Partial address" value="${search.address}">
					<span class="tab">Discipline:</span>
					<c:choose>
					<c:when test="${empty search.discipline}">
					<select name="discipline" placeholder="asd">
						<option value="0">Discipline Title</option>
						<c:forEach var="discipline" items="${disciplines}">
							<option value="${discipline.id}">${discipline.title}</option>
						</c:forEach>
					</c:when>
					<c:otherwise>
					<select name="discipline" placeholder="asd">
							<option value="0">Discipline Title</option>
							<c:forEach var="discipline" items="${disciplines}">
								<c:choose>
									<c:when test="${search.discipline == discipline.id}">
										<option value="${discipline.id}" selected><c:out
												value="${discipline.title}" /></option>
									</c:when>
									<c:otherwise>
										<option value="${discipline.id}"><c:out
												value="${discipline.title}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</c:otherwise>
					</c:choose>
								
					<c:choose>
					<c:when test="${!empty errors['disciplineAverage']}">
						<input type="text" name="disciplineAverage" placeholder="Discipline Average" value="${search.averageDiscipline}" title = "tu nu faci ce trebuie" style="color: red">
					</c:when>
					<c:otherwise>
						<input type="text" name="disciplineAverage" placeholder="Discipline Average" value="${search.averageDiscipline}">
					</c:otherwise>
				</c:choose>		
				</div>

				<div class="block">
					<span>Group:</span>
					<c:choose>
					<c:when test="${empty search.group}">
						<select name="group" class="js_group">
							<option value="0" selected>Group Title</option>
							<c:forEach var="groupId" items="${groups}">
								<option value="${groupId.id}"><c:out
										value="${groupId.name}" /></option>
							</c:forEach>
						</select>
					</c:when>
					<c:otherwise>
						<select name="group" class="js_group">
							<option value="0">Group Title</option>
							<c:forEach var="groupId" items="${groups}">
								<c:choose>
									<c:when test="${search.group == groupId.id}">
										<option value="${groupId.id}" selected><c:out
												value="${groupId.name}" /></option>
									</c:when>
									<c:otherwise>
										<option value="${groupId.id}"><c:out
												value="${groupId.name}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
					
					<span class="tab">Total Average:</span>
					
						<c:choose>
					<c:when test="${!empty errors['totalAverage']}">
					 <input type="text" name="totalAverage" placeholder="Total Average" value="${search.totalAverage}" style="color: red">
					</c:when>
					<c:otherwise>
					 <input type="text" name="totalAverage" placeholder="Total Average" value="${search.totalAverage}">
					</c:otherwise>
				</c:choose>		
				</div>

				<div class="block">


					<span>Gender:</span>
					<c:choose>
						<c:when test="${search.gender == 'M'}">
									<input type="radio" class="js_gender" name="gender" value="M" checked>
									<span class="gender"> male</span>
									<input name="gender" value="F" class="js_gender" type="radio">
									<span class=" gender"> female</span>
									<input name="gender" value="all" type="radio">
									<span class="gender">all</span> 
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${search.gender == 'F'}">
									<input type="radio" class="js_gender" name="gender" value="M">
									<span class="gender"> male</span>
									<input name="gender" value="F" class="js_gender" checked type="radio">
									<span class=" gender"> female</span>
									<input name="gender" value="all" type="radio">
									<span class="gender">all</span> 
								</c:when>
								<c:otherwise>
									<input type="radio" class="js_gender" name="gender" value="M">
									<span class="gender"> male</span>
									<input name="gender" value="F" class="js_gender" type="radio">
									<span class=" gender"> female</span>
									<input name="gender" value="all" type="radio" checked>
									<span class="gender">all</span> 
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<input type="submit" class='but1' value="Search" /> <input class="but2" value="Reset" type="submit" form="f1">
				</div>
				<!-- 					  <input name="gender" value="all" checked="checked" type="radio">
				<span class="gender">all</span> 

 -->
			</form>
			<form action="student" method="GET" id="f1">
				<input type="hidden" name="action" value="${LIST}">
			</form>
		</div>
		<div class="wraptab">

			<table>
				<tr>
					<td><input class="checkall" type="checkbox" name="idStudent" ></td>
					<td>Picture</td>
					<td>Name</td>
					<td>Birth Day</td>
					<td>Gender</td>
					<td>Address</td>
					<td>Phone</td>
					<td>Library Abonament</td>
					<td>Marks</td>
					<td>Action</td>
				</tr>

				<c:forEach var="student" items="${students}">
					<tr>
						<td><input type="checkbox" name="idStudent" value="${student.id}" form="form"></td>
						<c:choose>
							<c:when test="${student.picture != null}">
								<td><img id="output" src="/image?id=${student.id}" name="image" height="60 px" width="=60px"></td>
							</c:when>
							<c:otherwise>
								<td><img id="output" src="image/f.png" name="image" height="60 px" width="=60px"></td>
							</c:otherwise>
						</c:choose>
						<td><c:out value="${student.firstName} ${student.lastName}" /></td>
						<fmt:formatDate pattern="dd-MM-yyyy" value="${student.dob}" var="fromDate" />
						<td><c:out value="${fromDate}" /></td>
						<%-- <td><c:out value="${student.group.name}" /></td> --%>
						<td><c:out value="${student.gender}" /></td>
						<td><c:out value="${student.addres.country} ${student.addres.city} ${student.addres.address}" /></td>
						<td><c:forEach var="phone" items="${student.phone}">
								<c:out value="${phone.type.phoneNameType} ${phone.number}" />
								</br>
							</c:forEach></td>
						<td>
							<%--<a href="student?action=${SAVE_LIBRARY}&id=${student.abonament.id}">--%>
								<c:out value="Status: ${student.abonament.abonamentStatus}"
								/>
					<%--	</a>--%>
							</br> <c:if test="${student.abonament.abonamentStatus == 'ACTIVE'}">
								<fmt:formatDate pattern="dd-MM-yyyy" value="${student.abonament.startDate}" var="fromDate" />
								<c:out value="From: ${fromDate}" />
								<br />
								<fmt:formatDate pattern="dd-MM-yyyy" value="${student.abonament.endDate}" var="fromDate" />
								<c:out value="To: ${fromDate}" />
								<br />
							</c:if>
						<td>
							<c:if test="${empty student.average}">
							 	This student does not have notes
		 					</c:if>
	 						<c:forEach var="average" items="${student.average}">
	 							<c:choose>
									<c:when test="${average.averageMark >=5}">
										<c:out value="${average.discipline.title}: ${average.averageMark}" />
										</br>
									</c:when>
									<c:otherwise>
										<c:out value="${average.discipline.title}: N/A" />
										</br>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</td>
						<td>
						 <form action="student" method="GET">	
						        <input type="hidden" name="action" value="${SAVE}" >							
								<input type="hidden" name="id" value="${student.id}" />
								<input type="submit" class="butedit" value="Edit"/>								
							</form>
							<form action="student" method="GET">
								<input type="hidden" name="action" value="${SAVE_MARK}">
								<input type="hidden" name="id" value="${student.id}" />
								<input type="submit" class='butadd' value="Add mark" />
							</form>  
						</td>
					</tr>

				</c:forEach>

			</table>
		</div>
		<div>
			<form action="student" method="GET">
				<input type="hidden" name="action" value="${SAVE}"> <input type="hidden" name="id" value="0" /> <input
					type="submit" class='but3' value="Add New"
				/>
			</form>

			<form action="student" method="POST" id="form">
				<input type="hidden" name="action" value="${DELETE}"> <input type="submit" id="deleteButton" class='but4 js_delete' disabled="disabled" value="Delete" />
			</form>
			<form action="student" method="GET">
				<input type="hidden" name="action" value="${DOWNLOAD}"> <input type="submit" class='but5' value="Download" />
			</form>

		</div>
	</div>

</body>
</html>