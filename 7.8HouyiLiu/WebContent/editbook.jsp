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
	
	ISBN:   <input type="text" name="ISBN" id = "ISBN"> <br>
	
	Title:  <input type="text" name="title" id = "title"><br>
	
	Author: <input type="text" name="author" id = "author"><br>
	
	<br/>
	
	<input type="submit" name="submit" value="editbookcomplete" >
	<input type="reset" name="reset">
</form>
<br/>
<table rules = "all"> ${displayTable} </table> 
<a href = "index.jsp">Back to index</a>
</body>
</html>