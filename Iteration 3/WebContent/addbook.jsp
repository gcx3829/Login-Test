<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="script.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Book</title>

</head>
<body>
<h1 name="FirstMsg" value="FirstMsg" id = "FirstMsg"> ${message} !!! </h1>
<h2 name="SecondMsg" value="SecondMsg" id = "SecondMsg"> ${secondMessage}</h2>

<form name="regform" action="ManageBooksController" method="post">
	<br>${message}<br>
	Add Book <br>
	ISBN:   <input type="text" name="ISBN" id = "ISBN"> <br>
	
	Title:  <input type="text" name="title" id = "title"><br>
	
	Author: <input type="text" name="author" id = "author"><br>
	
	Category: <input type="text" name="genre" id = "genre"><br>
	
	Edition: <input type="text" name="edition" id = "edition"><br>
	
	<input type="submit" name="submit" value="add new book" id = "add">
	<input type="reset" name="reset" id = "reset"><br><br><br>
	<input type="submit" name="submit" value="Return to Home page" id = "go home">
</form>
<br/>
<table id="AllBooks"
rules = "all"> ${displayTable} </table> 
<a href = "index.jsp">Back to index</a>
</body>
</html>