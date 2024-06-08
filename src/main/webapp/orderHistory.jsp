<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@ page import="com.amstech.retail.dto.UserDTO"%>
<%@ page import="com.amstech.retail.dto.OrderDTO"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.text.SimpleDateFormat, java.util.Date"%>

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
<title>Order History</title>
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

@media ( max-width : 768px) {
	.sidebar {
		width: 100%;
		height: auto;
		position: relative;
	}
	.main-content {
		margin-left: 0;
		width: 100%;
	}
}

@media ( max-width : 576px) {
	.sidebar a, .sidebar button {
		font-size: 16px;
		padding: 10px;
	}
	.main-content {
		padding: 10px;
	}
}
</style>
</head>
<body>
	<%
	UserDTO auth = (UserDTO) session.getAttribute("auth");
	List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders");
	OrderDTO order = (OrderDTO) request.getAttribute("order");
	%>
	<div class="sidebar">
		<a href="home.jsp" class="btn btn-primary">Home</a>
		<form action="user" method="get">
			<input type="hidden" name="task" value="findById"> <input
				type="hidden" name="id" value="<%=auth.getId()%>">
			<button type="submit" class="btn btn-primary">Edit Profile</button>
		</form>
		<form action="order" method="get">
			<input type="hidden" name="task" value="findAllOrdersByStoreId">
			<input type="hidden" name="id" value="<%=auth.getId()%>">
			<button type="submit" class="btn btn-primary">Order History</button>
		</form>
		<a href="item.jsp" class="btn btn-primary">Add Item</a>
		<form action="items" method="get">
			<input type="hidden" name="task" value="findAllItems"> <input
				type="hidden" name="id" value="<%=auth.getId()%>">
			<button type="submit" class="btn btn-primary text-white">Find
				All Items</button>
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
						Welcome,
						<%=auth.getName()%></h2>
					<form action="order" method="get" class="form-inline">
						<input type="hidden" name="task" value="searchOrderByOrderNumber">
						<input type="text" name="orderNumber" class="form-control mr-2"
							placeholder="Enter Order Number">
						<button type="submit" class="btn btn-primary">Search</button>
					</form>
					<form action="order" method="get" class="form-inline mt-3">
						<input type="hidden" name="task" value="findOrdersByDateRange">
						<input type="date" name="startDate" class="form-control mr-2" placeholder="Start Date">
						<input type="date" name="endDate" class="form-control mr-2" placeholder="End Date">
						<button type="submit" class="btn btn-primary">Find Orders</button>
					</form>
					
					<%
					if (orders != null && !orders.isEmpty()) {
					%>
					<table class="table table-bordered table-hover mt-4">
						<thead class="thead-light">
							<tr>
								<th>S.No</th>
								<th>Order Number</th>
								<th>Total Amount</th>
								<th>Status</th>
								<th>Customer Name</th>
								<th>Customer Number</th>
								<th>Date and time</th>
								<th>For Details</th>
								<th>For Bill</th>
							</tr>
						</thead>
						<tbody>
							<%
							int i = 1;
							for (OrderDTO orderDTO : orders) {
							%>
							<tr>
								<td><%=i++%></td>
								<td><%=orderDTO.getOrderNumber()%></td>
								<td><%=orderDTO.getTotalAmount()%></td>
								<td><%=orderDTO.getStatus()%></td>
								<td><%=orderDTO.getCustomerName()%></td>
								<td><%=orderDTO.getCustomerNumber()%></td>
								<%
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
								String formattedDate = sdf.format(orderDTO.getOCreateDateTime());
								%>
								<td><%=formattedDate%></td>
								<td>
									<form action="order" method="get" class="form-inline">
										<input type="hidden" name="task"
											value="searchOrderByOrderNumber"> <input
											type="hidden" name="orderNumber" class="form-control mr-2"
											value="<%=orderDTO.getOrderNumber()%>">
										<button type="submit" class="btn btn-primary">details</button>
									</form>
								</td>
								<td>
									<form action="OrderPDFServlet" method="get" target="_blank">
										<input type="hidden" name="orderNumber"
											value="<%=orderDTO.getOrderNumber()%>">
										<button type="submit" class="btn btn-primary">Print</button>
									</form>
								</td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
					<%
					} else if (order != null) {
					%>
					<div class="mt-4">
						<form action="OrderPDFServlet" method="get" target="_blank">
							<input type="hidden" name="orderNumber"
								value="<%=order.getOrderNumber()%>">
							<button type="submit" class="btn btn-primary">Print</button>
						</form>
						<h3>Order Details</h3>
						<table class="table table-bordered table-hover">
							<thead class="thead-light">
								<tr>
									<th>Order Number</th>
									<th>Total Amount</th>
									<th>Status</th>
									<th>Customer Name</th>
									<th>Customer Number</th>
									<th>Date and Time</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><%=order.getOrderNumber()%></td>
									<td><%=order.getTotalAmount()%></td>
									<td><%=order.getStatus()%></td>
									<td><%=order.getCustomerName()%></td>
									<td><%=order.getCustomerNumber()%></td>
									<%
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
									String formattedDate = sdf.format(order.getOCreateDateTime());
									%>
									<td><%=formattedDate%></td>

								</tr>
							</tbody>
						</table>

						<h4>Order Items</h4>
						<table class="table table-bordered table-hover">
							<thead class="thead-light">
								<tr>
									<th>Item ID</th>
									<th>Item Name</th>
									<th>Quantity</th>
									<th>Price at Order</th>
								</tr>
							</thead>
							<tbody>
								<%
								String[] itemIds = order.getOrderItemIds();
								String[] itemNames = order.getOrderItemNames();
								String[] quantities = order.getQuantities();
								String[] prices = order.getPriceAtOrder();

								for (int j = 0; j < itemIds.length; j++) {
								%>
								<tr>
									<td><%=itemIds[j]%></td>
									<td><%=itemNames[j]%></td>
									<td><%=quantities[j]%></td>
									<td><%=prices[j]%></td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>

					</div>
					<%
					} else {
					%>
					<p>No orders found for this user.</p>
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
