<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="beans.Reimbursement"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	response.addHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.addHeader("Pragma", "no-cache");
	response.addDateHeader("Expires", 0);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" type="text/css"
	href="//cdn.datatables.net/1.10.13/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8"
	src="//cdn.datatables.net/1.10.13/js/jquery.dataTables.js"></script>
	
<link rel="stylesheet" type="text/css" href="/DataTables/datatables.css">
 
<script type="text/javascript" charset="utf8" src="/DataTables/datatables.js"></script>


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

.logout {
	float: right;
}

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
	font-size: 3em;
	font-weight: 300;
	line-height: 1em;
	text-align: center;
	color: #FFFFFF;
	padding-top: 50px;
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
	color: #FFFFFF;
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

<!--Reimbursement button-->
#myTopnav > li:nth-child(2) > div > button {
	background-color: #333333;
}

#buttonText: hover {
	font-weight:bold;
}
</style>

<script>
$(document).ready( function () {
    $('#rTable').DataTable();
} );
</script>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- NEW. DELETE IF BUSTED -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ERS</title>

</head>

<body>

	<ul class="topnav" id="myTopnav" style="text-align:center">
		<li><a class="disabled" href="" style="font-weight: bold">Hi,
				${user.firstName}</a></li>
		<li><div style="text-align: center; margin-top: 10px; display:inline-block">
				<button type="button" class="btn btn-secondary" data-toggle="modal"
					data-target="#exampleModal" data-whatever="@mdo" style="background-color:#333333">
					<span class="buttonText" style="color:white; font-weight:bold; font-size: 15px">Submit New Reimbursement Request</span>
				</button>
			</div></li>
		<form method="POST" action="logout.do">
			<li style="float: right"><a href="/ERS/login.jsp">Log Out</a></li>
		</form>
	</ul>

	<%
		ArrayList<Reimbursement> userReimbursements = (ArrayList<Reimbursement>) request.getAttribute("userInfo");
	%>
	<h1>
		<span class="blue"></span>Expense<span class="blue"></span> <span
			class="yellow">Reimbursements</span>
	</h1>

	<h2>Welcome, ${user.firstName} ${user.lastName} here are your most
		recent reimbursement requests.</h2>

	<table id="rTable" class="container" style="color:white">
		<thead>
			<tr>
				<th><h1>ID</h1></th>
				<th><h1>Amount</h1></th>
				<th><h1>Submitted</h1></th>
				<th><h1>Resolved</h1></th>
				<th><h1>Description</h1></th>
				<th><h1>Resolved By</h1></th>
				<th><h1>Type</h1></th>
				<th><h1>Status</h1></th>
			</tr>
		</thead>
		<tbody>

			<!-- GIVE NO REIMBURSEMENTS A STYLE -->
			<c:choose>
				<c:when test="${fn:length(userInfo)== 0}">
					<h1 id="noReimbursements">All your reimbursements are resolved :)</h1>
				</c:when>

				<c:otherwise>
					<c:forEach var="i" begin="0" end="${fn:length(userInfo)-1}">
						<!-- begin="0" end= "${fn:length(userReimbursement)} -->

						<tr>
							<td>${userInfo[i].id}</td>
							<td>$<fmt:formatNumber value="${userInfo[i].amount}"
									minFractionDigits="2" /></td>
							<!--  -->
							<fmt:formatDate value="${userInfo[i].submitDate}"
								var="readableDate" type="date" pattern="MM-dd-YYYY" />
							<td>${readableDate}</td>
							<fmt:formatDate value="${userInfo[i].resolveDate}"
								var="formattedDate" type="date" pattern="MM-dd-YYYY" />
							<td>${formattedDate}</td>
							<td>${userInfo[i].description}</td>
							<td>${userInfo[i].resolver.firstName}</td>
							<td>${userInfo[i].type.reimbType}</td>
							<td>${userInfo[i].status.status}</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>

		</tbody>
	</table>
	<!-- <div style="text-align: center; margin-top: 25px">
		<button type="button" class="btn btn-secondary" data-toggle="modal"
			data-target="#exampleModal" data-whatever="@mdo">
			<span class="buttonText">Submit New Reimbursement Request</span>
		</button>
	</div> -->

	<form method="POST" action="insert.do">
		<input type="hidden" name="author" value="${user.userID}" />

		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="exampleModalLabel">Enter
							Reimbursement Details</h4>
					</div>
					<div class="modal-body">

						<div class="form-group">
							<label for="recipient-name" class="control-label">Amount:</label>
							<div class="input-group">
								<span class="input-group-addon">$</span> <input type="number"
									step="0.01" class="form-control" name="amount"
									id="reimbursementAmount" placeholder="Enter a dollar amount"
									required="required">
							</div>
						</div>
						<div class="form-group">
							<label for="message-text" class="control-label">Description:</label>
							<textarea rows="2" class="form-control" name="description"
								placeholder="Enter a description for this reimbursement (optional)"></textarea>
						</div>

						<div class="form-group">
							<select name="reimbursementType" required="required"
								class="form-control">
								<option value="" disabled selected>Type</option>
								<option value="1">Lodging</option>
								<option value="2">Travel</option>
								<option value="3">Food</option>
								<option value="4">Other</option>
							</select>
						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<input type="submit" value="Submit" class="btn btn-default">
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>