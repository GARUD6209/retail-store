<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page  import="com.amstech.retail.dto.UserDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<% UserDTO userDTO = (UserDTO)request.getAttribute("loginUserDTO"); %>
	<form action="user" method = "get">
		<input type = "hidden" name = "task" value = "findById">
		<input type = "text" name = "id" value = "1">
		<button type = "submit">Edit</button>
	</form>
	
	
	 <%
		UserDTO userDTOEdit = (UserDTO) request.getAttribute("userDTOEdit");
		if (userDTOEdit != null) {
		%>


		<form action="user" method = "post">
			<input type = "hidden" name = "task" value = "updateById">
			<input type = "text" name = "id" value = "<%=userDTOEdit.getId() %>">
			<input type = "text" name = "name" value = "<%=userDTOEdit.getName() %>">
			<input type = "text" name = "address" value = "<%=userDTOEdit.getAddress() %>">
			<input type = "text" name = "mobile-number" value = "<%=userDTOEdit.getMobile_number()%>">
			<input type = "text" name = "email" value = "<%=userDTOEdit.getEmail() %>">
			<input type = "text" name = "gst-number" value = "<%=userDTOEdit.getGstNumber()%>">		
			<button type = "submit">update</button>
		</form>
	
	<%
	}
	%>
</body>
</html>