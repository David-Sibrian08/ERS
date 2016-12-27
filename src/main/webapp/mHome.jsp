<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="beans.Reimbursement"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<% 
	response.setHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.1.1.min.js"
	integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
	crossorigin="anonymous"></script>

<link rel="stylesheet" type="text/css"
	href="//cdn.datatables.net/1.10.13/css/jquery.dataTables.css">

<script type="text/javascript" charset="utf8"
	src="//cdn.datatables.net/1.10.13/js/jquery.dataTables.js"></script>


<style>
/*	
	Side Navigation Menu V2, RWD
	===================
	License:
	http://goo.gl/EaUPrt
	===================

*/
@charset "UTF-8";

@import
	url(http://fonts.googleapis.com/css?family=Open+Sans:300,400,700);

.buttonText {
	color: black;
	font-weight: bold;
}

a.selected {
	background-color: #1F75CC;
	color: white;
	z-index: 100;
}

.messagepop {
	background-color: #FFFFFF;
	border: 1px solid #999999;
	cursor: default;
	display: none;
	margin-top: 15px;
	position: absolute;
	text-align: left;
	width: 394px;
	z-index: 50;
	padding: 25px 25px 20px;
}

label {
	display: block;
	margin-bottom: 3px;
	padding-left: 15px;
	text-indent: -15px;
}

.messagepop p, .messagepop.div {
	border-bottom: 1px solid #EFEFEF;
	margin: 8px 0;
	padding-bottom: 8px;
}

body {
	font-family: 'Open Sans', sans-serif;
	font-weight: 300;
	line-height: 1.42em;
	color: #A7A1AE;
	background-color: #1F2739;
}

h1 {
	padding-top: 50px;
	font-size: 3em;
	font-weight: 300;
	line-height: 1em;
	text-align: center;
	color: #FFFFFF;
}

.logout {
	float: left;
	font-size: 14;
	font-style: bold;
	color: white;
	text-decoration: none;
}

#logout:hover span {
	font-weight: bold
}

h2 {
	font-size: 2em;
	font-weight: 300;
	text-align: center;
	display: block;
	line-height: 1em;
	padding-bottom: 2em;
	color: #CCCCCC;
}

h2 a {
	font-weight: 700;
	text-transform: uppercase;
	color: #FB667A;
	text-decoration: none;
}

.blue {
	color: #185875;
}

.yellow {
	color: #FFF842;
}

.container th h1 {
	font-weight: bold;
	font-size: 1em;
	text-align: left;
	color: #185875;
}

.container td {
	font-weight: normal;
	font-size: 1em;
	-webkit-box-shadow: 0 2px 2px -2px #0E1119;
	-moz-box-shadow: 0 2px 2px -2px #0E1119;
	box-shadow: 0 2px 2px -2px #0E1119;
}

.container {
	text-align: left;
	overflow: hidden;
	width: 80%;
	margin: 0 auto;
	display: table;
	padding: 0 0 8em 0;
}

.container td, .container th {
	padding-bottom: 2%;
	padding-top: 2%;
	padding-left: 2%;
}

/* Background-color of the odd rows */
.container tr:nth-child(odd) {
	background-color: #323C50;
}

/* Background-color of the even rows */
.container tr:nth-child(even) {
	background-color: #2C3446;
}

.container th {
	background-color: #1F2739;
}

.container td:first-child {
	color: #FB667A;
}

.container tr:hover {
	background-color: #464A52;
	-webkit-box-shadow: 0 6px 6px -6px #0E1119;
	-moz-box-shadow: 0 6px 6px -6px #0E1119;
	box-shadow: 0 6px 6px -6px #0E1119;
}

.container td:hover {
	background-color: #FFF842;
	color: #403E10;
	font-weight: bold;
	box-shadow: #7F7C21 -1px 1px, #7F7C21 -2px 2px, #7F7C21 -3px 3px,
		#7F7C21 -4px 4px, #7F7C21 -5px 5px, #7F7C21 -6px 6px;
	transform: translate3d(6px, -6px, 0);
	transition-delay: 0s;
	transition-duration: 0.1s;
	transition-property: all;
	transition-timing-function: line;
}

@media ( max-width : 800px) {
	.container td:nth-child(4), .container th:nth-child(4) {
		display: none;
	}
}

<!--
Nav Bar Styling-->body {
	margin: 0;
}

ul.topnav {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #333;
}

ul.topnav li {
	float: left;
}

ul.topnav li a {
	display: inline-block;
	color: #f2f2f2;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
	transition: 0.3s;
	font-size: 17px;
}

ul.topnav li a:hover {
	background-color: #555;
}

ul.topnav li.icon {
	display: none;
}

@media screen and (max-width:680px) {
	ul
	 
	.topnav
	 
	li
	 
	:not
	 
	(
	:first-child
	 
	)
	{
	display
	 
	:
	 
	none
	 
	;
}

ul.topnav li.icon {
	float: right;
	display: inline-block;
}

}
@media screen and (max-width:680px) {
	ul.topnav.responsive {
		position: relative;
	}
	ul.topnav.responsive li.icon {
		position: absolute;
		right: 0;
		top: 0;
	}
	ul.topnav.responsive li {
		float: none;
		display: inline;
	}
	ul.topnav.responsive li a {
		display: block;
		text-align: left;
	}
}

.disabled {
	pointer-events: none;
	cursor: default;
}
<!-- Data Tables -->
#rTable_filter {
	color: white
}

#rTable_length {
	color: white;
}

#rTable_length > label > select {
	color: black;
}

#rTable_filter > label {
	color: white;
}

#rTable_info {
	color: white;
}

#rTable_paginate {
	color: white; 
}

#rTable_previous {
	background-color: antiquewhite;
}

#rTable_next {
	background-color: antiquewhite;
}

#rTable_paginate > span > a:nth-child(n) {
	background-color: antiquewhite;
}

#rTable_paginate > span > a:nth-child(1) {
	background-color: antiquewhite;
}

#rTable_filter > label > input[type="search"] {
	color: black;
}
</style>

<script>
$(document).ready( function () {
    $('#rTable').DataTable();
} );
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERS</title>
</head>
<body>
	<ul class="topnav" id="myTopnav">
		<li><a class="disabled" href="" style="font-weight: bold">Hi,
				${user.firstName}</a></li>
		<form method="POST" action="logout.do">
			<li style="float: right"><a href="/ERS/login.jsp">Log Out</a></li>
		</form>
	</ul>

	<%
		ArrayList<Reimbursement> userReimbursements = (ArrayList<Reimbursement>) request
				.getAttribute("managerInfo");
	%>
	<h1>
		<span class="blue"></span>Expense<span class="blue"></span>Reimbursements Console
	</h1>

	<h2>Welcome, ${user.firstName} ${user.lastName} here are the most
		recent pending requests.</h2>

	<table class="container" style="color:white">
		<thead>
			<tr>
				<th><h1>ID</h1></th>
				<th><h1>Amount</h1></th>
				<th><h1>Submitted</h1></th>
				<!-- <th><h1>Resolved</h1></th> -->
				<th><h1>Description</h1></th>
				<!-- <th><h1>Resolved By</h1></th> -->
				<th><h1>Type</h1></th>
				<th><h1>Status</h1></th>
				<th><h1></h1></th>
			</tr>
		</thead>
		<tbody>

			<c:choose>
				<c:when test="${fn:length(managerInfo)== 0}">
					<h1 id="noReimbursements">All reimbursements resolved :)</h1>
				</c:when>

				<c:otherwise>

					<c:forEach var="i" begin="0" end="${fn:length(managerInfo)-1}">
						<!-- begin="0" end= "${fn:length(userReimbursement)} -->
						<form method="POST" action="update.do">
							<input type="hidden" name="resolver" value="${user.userName}" />
							<tr>
								<td>${managerInfo[i].id}<input type="hidden" name="rowId"
									value="${managerInfo[i].id}" /></td>
								<td>$<fmt:formatNumber value="${managerInfo[i].amount}"
										minFractionDigits="2" /></td>
								<!--  -->
								<fmt:formatDate value="${managerInfo[i].submitDate}"
									var="readableDate" type="date" pattern="MM-dd-YYYY" />
								<td>${readableDate}</td>
								<fmt:formatDate value="${managerInfo[i].resolveDate}"
									var="formattedDate" type="date" pattern="MM-dd-YYYY" />
								<%-- <td>${formattedDate}</td> --%>
								<td>${managerInfo[i].description}</td>
								<%-- <td>${managerInfo[i].resolver.firstName}</td> --%>
								<td>${managerInfo[i].type.reimbType}</td>
								<td>${managerInfo[i].status.status}</td>
								<td>
									<button type="submit" class="btn btn-success"
										style="width: 90px" name="statusChange" value="approved">Approve</button>
									<button type="submit" class="btn btn-danger"
										style="width: 90px" name="statusChange" value="denied">Deny</button>
								</td>
							</tr>
						</form>
					</c:forEach>
				</c:otherwise>
			</c:choose>

		</tbody>
	</table>
</body>
</html>