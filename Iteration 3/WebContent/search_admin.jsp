<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="script.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Staff Search Books</title>

</head>
<body>
<form name="regform" action="SearchController" method="post">
	<br>${message}<br>
	Search <br>
	
	InventoryID:   <input type="text" name="InventoryID" id = "InventoryID"> <br>
	
	<input type="submit" name="submit" value="Search by InventoryID" > <br>	<br>
	
	ISBN:   <input type="text" name="ISBN" id = "ISBN"> <br>
	
	Title:  <input type="text" name="title" id = "title"><br>
	
	Author: <input type="text" name="author" id = "author"><br>
	
	Category: <input type="text" name="genre" id = "genre"><br>
	
	Edition: <input type="text" name="edition" id = "edition"><br>
	
	Rented By:  <input type="text" name="RentedBy" id = "RentedBy"><br>
	
	Check Out Date: <input type="text" name="CheckOutDate" id = "CheckOutDate"><br>
	
	Return Deadline: <input type="text" name="ReturnByDate" id = "ReturnByDate"><br>
	
	Book Status:  <select name="Status">
	<option value=10>Any Status</option>
	<option value=1>Available</option>
	<option value=0>CheckedOut</option>
	<option value=2>Returned</option>
	<option value=3>Under Repair</option>
	<option value=4>Not Presentable</option>
	<option value=5>Waitlisted</option>
	</select>
	
	<br/>
	
	<input type="submit" name="submit" value="Search by Parameters" id = "search" > <br>
	<input type="reset" name="reset"><br><br><br>
	<input type="submit" name="submit" value="Return to Home page" >
</form>
<br/>
<table id="SearchResults"
rules = "all"> ${displayTable} </table> <br><br>
<a href = "index.jsp">Back to index</a>
</body>
</html>