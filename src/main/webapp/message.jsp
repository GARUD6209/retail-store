<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>message</title>
</head>
<body>
<%
	String status = (String) request.getAttribute("status");
	String message = (String) request.getAttribute("message");
	String redirectURL = (String) request.getAttribute("redirectURL");
	%>


	<h1><%=status%></h1>
	<h1><%=message%></h1>
	<h1> <a href="<%=redirectURL%>">Click here to redirect..</a></h1>
</body>
</html>