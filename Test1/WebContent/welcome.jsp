<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>
<h1> ${message} !!! </h1>
<h2> ${secondMessage} !!! </h2>
<p>${bookMessage}</p>
<form name="checkform" action="LoginController" method="post">
	ISBN Number: <input type="text" name="ISBN" id="ISBN"><br>
	<input type="submit" name="submit" value="Check Out">
</form>

	<a href="logout.jsp">logout</a>

</body>
</html>