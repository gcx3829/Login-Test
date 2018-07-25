<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Patron Welcome Page</title>
</head>
<body>
<h1 name="FirstMsg" value="FirstMsg" id = "FirstMsg"> ${message} !!! </h1>
<h2 name="SecondMsg" value="SecondMsg" id = "SecondMsg"> ${secondMessage}</h2>
<h3> Overdue Books</h3>
<table id="Overdue Books"
rules = "all"> ${displayTable1} </table>
<h4 name="OverdueMsg" value="OverdueMsg" id = "OverdueMsg"> ${overdueMessage}</h4>
<br><br>
<form name="checkform" action="ReturnsController" method="post">
	ISBN Number: <input type="text" name="ISBN" id="ISBN">
	<input type="submit" name="submit" value="Return" id="Return"> <br><br><br>
</form>
<form name="checkform" action="CheckOutController" method="post">	
	<input type="submit" name="submit" value="Search and Check Out"  id="Search and Check Out">
</form>
<h3> Checked Out Books</h3>
<table id="Checked Out"
rules = "all"> ${displayTable} </table> 

	<br><br>
	
<h3> Waitlisted Books</h3>
<table id="Waitlist"
rules = "all"> ${displayTable2} </table> 

<br><br>
	
	<a href="logout.jsp">logout</a>

</body>
</html>
