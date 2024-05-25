<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.amstech.retail.dto.UserDTO"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update User Information</title>
</head>
<body>
       <%
		UserDTO userDTOEdit = (UserDTO) request.getAttribute("userDTOEdit");
		if (userDTOEdit != null) {
		%>


		<form action="user" method = "post">
			<input type = "hidden" name = "task" value = "updateById">
			<input type = "text" name = "id" value = "<%=userDTOEdit.getId() %>">
			<input type = "text" name = "id" value = "<%=userDTOEdit.getName() %>">
			<input type = "text" name = "id" value = "<%=userDTOEdit.getAddress() %>">
			<input type = "text" name = "id" value = "<%=userDTOEdit.getMobile_number()%>">
			<input type = "text" name = "id" value = "<%=userDTOEdit.getEmail() %>">
			<input type = "text" name = "id" value = "<%=userDTOEdit.getGstNumber()%>">		
			<button type = "submit">update</button>
		</form>
	
	<%
	}
	%>
    
</body>
</html>
