<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page  import="com.amstech.retail.dto.UserDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>
</head>
<body>

<% UserDTO userDTO = (UserDTO)request.getAttribute("loginUserDTO"); %>
	<form action="user" method = "get">
		<input type = "hidden" name = "task" value = "findById">
		<input type = "text" name = "id" value = "1">
		<button type = "submit">Edit</button>
		
		
	</form>
	
	<form action="user" method = "get">
		<input type = "hidden" name = "task" value = "itemAddById">
		<input type = "hidden" name = "id" value = "<%=userDTO.getId()%>">
		<button type = "submit">add item</button>		
	</form>
	
	<form action="items" method = "get">
		<input type = "hidden" name = "task" value = "findAllItems">
		<input type = "hidden" name = "id" value = "<%=userDTO.getId()%>">
		<button type = "submit">find All Items</button>		
	</form>
	
<form action="user" method = "get">
		<input type = "hidden" name = "task" value = "queryAddById">
		<input type = "hidden" name = "id" value = "<%=userDTO.getId()%>">
		<button type = "submit">Add Query</button>		
	</form>
	
	
	
</body>
</html>