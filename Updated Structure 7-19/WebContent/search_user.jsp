<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="script.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Patron Search Books</title>
</head>
<body>
<h2> ${secondMessage}</h2>
<form name="regform" action="SearchController" method="post">
	<br>${message}<br>
	Search <br>
	
	ISBN:   <input type="text" name="ISBN" id = "ISBN"> <br>
	
	Title:  <input type="text" name="title" id = "title"><br>
	
	Author: <input type="text" name="author" id = "author"><br>
	
	
	Category: <input type="text" name="category" id = "category"><br>
	
	Edition: <input type="text" name="edition" id = "edition"><br>
	
	<br/>
	
	<input type="submit" name="submit" value="Search"  id = "search">
	<input type="reset" name="reset" id = "reset"><br>
	<input type="submit" name="submit" value="Return to Home page"  id = "go home">
</form>
<form name="regform" action="CheckOutController" method="post">
	<br> <br> Check Out Book <br>
	
	Check Out ISBN:   <input type="text" name="ISBN" id = "ISBN"> <br>
	<input type="submit" name="submit1" value="Check Out" id = "check out"><br>
	<br/>
</form>
<br/>
<h3> Search Results </h3>
<table id="SearchResults"
rules = "all"> ${displayTable} </table> <br><br>


<a href = "index.jsp">Back to index</a>
</body>
</html>
