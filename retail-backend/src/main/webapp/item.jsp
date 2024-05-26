<%@page import="com.amstech.retail.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>item page</title>
</head>
<body>


	<%
	UserDTO userDTOEdit = (UserDTO) request.getAttribute("userDTOEdit");
	if (userDTOEdit != null) {
	%>






	<form action="items" method="post">

		<input type="hidden" name="task" value="addItem"> <input
			type="hidden" id="storeInfoId" name="storeInfoId"
			value="<%=userDTOEdit.getId()%>" placeholder="Store Id" required>

		<input type="text" id="name" name="name" placeholder="Item Name"
			required><br> <br> <input type="number" step="0.01"
			id="currentPrice" name="current-price" placeholder="Price" required><br>
		<br>


		<textarea id="description" name="description"
			placeholder="Description" required></textarea>
		<br>
		<br> <input type="submit" value="Submit">
	</form>
	<%
	}
	%>
</body>
</html>