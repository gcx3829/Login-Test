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
<form name="regform" action="ManageController" method="post">
	<br>${message}<br>
	Edit Book <br>
	TargetISBN:   <input type="text" name="TargetISBN" id = "TargetISBN"> <br>
	
	TargetCopy:   <input type="text" name="TargetCopyID" id = "TargetCopyID"> <br>
	
	ISBN:   <input type="text" name="ISBN" id = "ISBN"> <br>
	
	Title:  <input type="text" name="title" id = "title"><br>
	
	Author: <input type="text" name="author" id = "author"><br>
	
	Category: <input type="text" name="category" id = "category"><br>
	
	Edition: <input type="text" name="edition" id = "edition"><br>
	
	Copy Number:   <input type="text" name="CopyID" id = "CopyID"> <br>
	
	Check Out Date:   <input type="text" name="CheckOutDate" id = "CheckOutDate"> <br>
	
	Return Deadline:   <input type="text" name="ReturnByDate" id = "ReturnByDate"> <br>
	
	Check Out By:   <input type="text" name="RentedBy" id = "RentedBy"> <br>
	
	Book Status:  <select name="Status">
	<option value=4>Not Presentable</option>
	<option value=3>Under Repair</option>
	<option value=2>Returned</option>
	<option value=1>Available</option>
	<option value=0>CheckedOut</option>
	</select>
	
	
	<br/>
	
	<input type="submit" name="submit" value="editbookcomplete" >
	<input type="reset" name="reset">
</form>
<br/>
<table rules = "all"> ${displayTable} </table> 
<a href = "index.jsp">Back to index</a>
</body>
</html>