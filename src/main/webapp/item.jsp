<%@ page import="com.amstech.retail.dto.UserDTO"%>
<%@ page import="com.amstech.retail.dto.ItemDTO"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<% 
response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
	
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Item Page</title>
<!-- Bootstrap CSS -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Custom CSS -->
<style>
body {
    display: flex;
    margin: 0;
    font-family: Arial, sans-serif;
}

.sidebar {
    width: 250px;
    height: 100vh;
    position: fixed;
    top: 0;
    left: 0;
    background-color: #343a40;
    padding-top: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.sidebar a, .sidebar form {
    width: 100%;
    text-align: center;
    margin: 5px 0;
}

.sidebar a, .sidebar button {
    padding: 15px;
    text-align: center;
    text-decoration: none;
    font-size: 18px;
    color: white;
    border: none;
    background-color: #343a40;
    transition: background-color 0.3s, color 0.3s;
    display: block;
    width: 100%;
}

.sidebar a:hover, .sidebar button:hover {
    background-color: #575d63;
    color: #ffffff;
    cursor: pointer;
}

.sidebar .btn {
    width: 100%;
    padding: 15px;
}

.sidebar .btn-primary {
   
    color: white;
}

.sidebar .btn-primary:hover {
    background-color: #0056b3;
}

.main-content {
    margin-left: 250px;
    padding: 20px;
    width: calc(100% - 250px);
}

.container {
    margin-top: 50px;
}
</style>
</head>
<body>
	<%
	UserDTO auth = (UserDTO) session.getAttribute("auth");
	List<ItemDTO> itemDTOList = (List) request.getAttribute("itemDTOList");
	%>
	 <div class="sidebar">
        <a href="home.jsp" class="btn btn-primary">Home</a>
        <form action="user" method="get">
            <input type="hidden" name="task" value="findById">
            <input type="hidden" name="id" value="<%=auth.getId()%>">
            <button type="submit" class="btn btn-primary">Edit Profile</button>
        </form>
        <form action="order" method="get">
            <input type="hidden" name="task" value="findAllOrdersByStoreId">
            <input type="hidden" name="id" value="<%=auth.getId()%>">
            <button type="submit" class="btn btn-primary">Order History</button>
        </form>
        <a href="item.jsp" class="btn btn-primary">Add Item</a>
        <form action="items" method="get">
            <input type="hidden" name="task" value="findAllItems">
            <input type="hidden" name="id" value="<%=auth.getId()%>">
            <button type="submit" class="btn btn-primary text-white">Find All Items</button>
        </form>
        <a href="query.jsp" class="btn btn-primary">Add Query</a>
        <form action="logout" method="post">
            <button class="btn btn-danger" type="submit">Logout</button>
        </form>
    </div>

	<div class="main-content">
		<div class="container">
			<div class="row">
				<div class="col">
					<h2>
						Welcome,<%=auth.getName()%></h2>
						
						
						
						<%
					ItemDTO editItemDTO = (ItemDTO) request.getAttribute("editItemDTO");
					%>
					<%
					if (editItemDTO != null) {
					%>
					<form action="items" method="post" class="mt-4">
						<input type="hidden" name="task" value="updateById"> <input
							type="hidden" name="itemId" value="<%=editItemDTO.getId()%>">
						<div class="form-group">
							<label for="editName">Item Name</label> <input type="text"
								class="form-control" id="editName" name="name"
								value="<%=editItemDTO.getName()%>">
						</div>
						<div class="form-group">
							<label for="editPrice">Price</label> <input type="text"
								class="form-control" id="editPrice" name="current-price"
								value="<%=editItemDTO.getCurrent_price()%>">
						</div>
						<div class="form-group">
							<label for="editDescription">Description</label> <input
								type="text" class="form-control" id="editDescription"
								name="description" value="<%=editItemDTO.getDescription()%>">
						</div>
						<button type="submit" class="btn btn-primary">Update</button>
					</form>
				
					<%
					}
					else	if (auth != null && itemDTOList == null ) {
					%>
					<form action="items" method="post" class="mt-4">
						<input type="hidden" name="task" value="addItem"> <input
							type="hidden" id="storeInfoId" name="storeInfoId"
							value="<%=auth.getId()%>" required>
						<div class="form-group">
							<label for="name">Item Name</label> <input type="text"
								class="form-control" id="name" name="name"
								placeholder="Item Name" required>
						</div>
						<div class="form-group">
							<label for="currentPrice">Price</label> <input type="number"
								step="0.01" class="form-control" id="currentPrice"
								name="current-price" placeholder="Price" required>
						</div>
						<div class="form-group">
							<label for="description">Description</label>
							<textarea class="form-control" id="description"
								name="description" placeholder="Description" required></textarea>
						</div>
						<button type="submit" class="btn btn-success">Submit</button>
					</form>
					<%
					}
					
					
					%>

					<%
					if (itemDTOList != null && !itemDTOList.isEmpty()) {
					%>
					<table class="table table-bordered table-hover mt-4">
						<thead class="thead-light">
							<tr>
								<th>Id</th>
								<th>Name</th>
								<th>Price</th>
								<th>Description</th>
								<th>Store Id</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<%
							int i = 1;
							for (ItemDTO itemDTO : itemDTOList) {
							%>
							<tr>
								<td><%=i++%></td>
								<td><%=itemDTO.getName()%></td>
								<td><%=itemDTO.getCurrent_price()%></td>
								<td><%=itemDTO.getDescription()%></td>
								<td><%=itemDTO.getStoreInfoId()%></td>
								<td>
									<form action="items" method="get" class="d-inline">
										<input type="hidden" name="task" value="findItemById">
										<input type="hidden" name="itemId"
											value="<%=itemDTO.getId()%>">
										<button type="submit" class="btn btn-info btn-sm">Edit</button>
									</form>
									<form action="items" method="get" class="d-inline">
										<input type="hidden" name="task" value="deleteItemById">
										<input type="hidden" name="itemId"
											value="<%=itemDTO.getId()%>">
										<button type="submit" class="btn btn-danger btn-sm">Delete</button>
									</form>
								</td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
					<%
					}
					%>

					
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS, Popper.js, and jQuery -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
