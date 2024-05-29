<%@page import="com.amstech.retail.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="com.amstech.retail.dto.ItemDTO"%>
	<%@page import="java.util.List"%>
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
	
	
	<table>		
		<thead>
			<tr>
				<th>id</th>
				<th>name</th>
				<th>price</th>
				<th>description</th>
				<th>store id</th>
			</tr>
		</thead>
	
			<%  List<ItemDTO> itemDTOList = (List) request.getAttribute("itemDTOList");%>	
		<%if(itemDTOList != null && !itemDTOList.isEmpty()){
			for(ItemDTO itemDTO : itemDTOList){
		%>
		<tbody>		
			<tr>		
				<td><%=itemDTO.getId() %></td>
				<td><%=itemDTO.getName()%></td>
				<td><%=itemDTO.getCurrent_price()%></td>
				<td><%=itemDTO.getDescription()%></td>
				<td><%=itemDTO.getStoreInfoId()%></td>
				<td>
			    	<form action = "items" method = "get">
						<input type = "hidden" name = "task" value = "findItemById">
						<input type = "hidden" name = "itemId" value = "<%=itemDTO.getId()%>">
						<button type = "submit">edit</button>
					</form>
					<form action = "items" method = "get">
						<input type = "hidden" name = "task" value = "deleteItemById">
						<input type = "hidden" name = "itemId" value = "<%=itemDTO.getId()%>">
						<button type = "submit">delete</button>
					</form>
				</td>
			</tr>
			
		</tbody>
	
			<%}
		}%>
	</table>
	
	
	<% ItemDTO editItemDTO = (ItemDTO)request.getAttribute("editItemDTO");%>
	<%if(editItemDTO != null){ %>
		<form action="items" method = "post">
			<input type = "hidden" name = "task" value = "updateById">
			<input type = "hidden" name = "itemId" value = "<%=editItemDTO.getId() %>">
			<input type = "text" name = "name" value = "<%=editItemDTO.getName() %>">
			<input type = "text" name = "current-price" value = "<%=editItemDTO.getCurrent_price()%>">
			<input type = "text" name = "description" value = "<%=editItemDTO.getDescription()%>">		
			<button type = "submit">update</button>
		</form>
	<%}%>
	
</body>
</html>