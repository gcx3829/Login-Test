<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Staff Welcome Page</title>
</head>
<body>
<h1> ${message} !!! </h1>
<h2> ${secondMessage}</h2>
<form name="checkform" action="ManageController" method="post">
	<input type="submit" name="submit" value="addbook" id = "Add">
	<input type="submit" name="submit" value="editbook" id = "Edit">
	<input type="submit" name="submit" value="process returned books" id = "process">
	<input type="submit" name="submit" value="Search" id = "search">
</form>

<table id="BookList"
rules = "all"> ${displayTable} </table> 

<br/>

<table id="UserList"
rules = "all"> ${displayTable2} </table> 

	<a href="logout.jsp">logout</a>

</body>
</html>
