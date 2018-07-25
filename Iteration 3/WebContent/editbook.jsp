<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="script.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Book</title>

</head>
<body>
<h1 name="FirstMsg" value="FirstMsg" id = "FirstMsg"> ${message} !!! </h1>
<h2 name="SecondMsg" value="SecondMsg" id = "SecondMsg"> ${secondMessage}</h2>

<form name="regform" action="ManageBooksController" method="post">
	<br>${message}<br>
	Edit Book <br>
	Target Inventory ID:   <input type="text" name="TargetInventoryID" id = "TargetInventoryID"> <br>
	
	Target ISBN:   <input type="text" name="TargetISBN" id = "TargetISBN"> <br>
	
	ISBN:   <input type="text" name="ISBN" id = "ISBN"> <br>
	
	Title:  <input type="text" name="title" id = "title"><br>
	
	Author: <input type="text" name="author" id = "author"><br>
	
	Genre: <input type="text" name="genre" id = "genre"><br>
	
	Edition: <input type="text" name="edition" id = "edition"><br>
	
	Check Out By:   <input type="text" name="RentedBy" id = "RentedBy"> <br>
	
	Check Out Date:   <input type="text" name="CheckOutDate" id = "CheckOutDate"> <br>
	
	Return Deadline:   <input type="text" name="ReturnByDate" id = "ReturnByDate"> <br>
	
	Book Status:  <select name="Status">
	<option value=10>No Change</option>
	<option value=4>Not Presentable</option>
	<option value=3>Under Repair</option>
	<option value=2>Returned</option>
	<option value=1>Available</option>
	<option value=0>CheckedOut</option>
	</select>
	
	
	<br/>
	
	<input type="submit" name="submit" value="Edit Title" id = "edit title">
	<input type="submit" name="submit" value="Edit Book" id = "edit book"> 
	<input type="reset" name="reset" id = "reset"> <br><br><br>
	<input type="submit" name="submit" value="Return to Home page" id = "go home">
</form>
<br/>
<table id="AllBooks"
rules = "all"> ${displayTable} </table> 
<a href = "index.jsp">Back to index</a>
</body>
</html>