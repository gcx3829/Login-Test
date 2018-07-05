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
	<h1> Process Returned Books </h1>
	<br>	
	Returned book ISBN:   <input type="text" name="ISBN" id = "ISBN"> <br>	
	<br/>
	<h2> What is the status of the book</h2>
	
	<input type="submit" name="submit" value="Presentable" >
	<input type="submit" name="submit" value="Repair Needed" >
	<input type="submit" name="submit" value="Ruined" >
	<br><br><br>
	<input type="reset" name="reset">
	<br>
	<input type="submit" name="submit" value="Return to Home page" >
</form>
<br/>
<br>
 ${displayTable}
<a href = "index.jsp">Back to index</a>
</body>
</html>