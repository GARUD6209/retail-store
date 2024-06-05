<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.amstech.retail.dto.UserDTO"%>

<% 
response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Update User Information</title>
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
            <button 
            class="btn btn-danger" type="submit">Logout</button>
        </form>
</div>

	<div class="main-content">
		<div class="container">
			<div class="row">
				<div class="col">
					<h2>Update User Information</h2>
					<%
					UserDTO userDTOEdit = (UserDTO) request.getAttribute("userDTOEdit");
					if (userDTOEdit != null) {
					%>
					<form action="user" method="post">
						<input type="hidden" name="task" value="updateById">
						<div class="form-group">
							<label for="id">ID</label> <input type="text"
								class="form-control" id="id" name="id"
								value="<%=userDTOEdit.getId()%>" readonly>
						</div>
						<div class="form-group">
							<label for="name">Name</label> <input type="text"
								class="form-control" id="name" name="name"
								value="<%=userDTOEdit.getName()%>">
						</div>
						<div class="form-group">
							<label for="address">Address</label> <input type="text"
								class="form-control" id="address" name="address"
								value="<%=userDTOEdit.getAddress()%>">
						</div>
						<div class="form-group">
							<label for="mobile-number">Mobile Number</label> <input
								type="text" class="form-control" id="mobile-number"
								name="mobile-number"
								value="<%=userDTOEdit.getMobile_number()%>">
						</div>
						<div class="form-group">
							<label for="email">Email</label> <input type="text"
								class="form-control" id="email" name="email"
								value="<%=userDTOEdit.getEmail()%>">
						</div>
						<div class="form-group">
							<label for="gst-number">GST Number</label> <input type="text"
								class="form-control" id="gst-number" name="gst-number"
								value="<%=userDTOEdit.getGstNumber()%>">
						</div>
						<button type="submit" class="btn btn-success">Update</button>
					</form>
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
