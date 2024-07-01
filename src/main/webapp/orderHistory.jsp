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
<%@ include file="csslinks.jsp"%>

</head>
<body>
	<%
	List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders");
	OrderDTO order = (OrderDTO) request.getAttribute("order");
	%>

	<nav class="navbar navbar-expand-md  fixed-top"></nav>

	<div class="container-fluid ">
		<div class="row">
			<%@ include file="sidebar.jsp"%>


			<div class="main-content">
				<div class="container-fluid">
					<div class="row">
						<div class="col">
							<h2>
								Welcome,
								<%=auth.getName()%></h2>
							<form action="order" method="get" class="form-inline">
								<input type="hidden" name="task"
									value="searchOrderByOrderNumber"> <input type="text"
									name="orderNumber" class="form-control mr-2"
									placeholder="Enter Order Number" required>
								<button type="submit" class="btn btn-primary">Search</button>
							</form>
							<form action="order" method="get" class="form-inline mt-3">
								<input type="hidden" name="task" value="findOrdersByDateRange">
								<input type="date" name="startDate" class="form-control mr-2"
									placeholder="Start Date" required> <input type="date"
									name="endDate" class="form-control mr-2" placeholder="End Date" required>
								<button type="submit" class="btn btn-primary">Find
									Orders</button>
							</form>

							<%
							if (orders != null && !orders.isEmpty()) {
							%>
							<table class="table table-bordered table-hover mt-4">
								<thead class="thead-light">
									<tr>
										<th>S.No</th>
										<!-- <th>order id</th> -->
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
									<%-- 	<td><%=orderDTO.getOrderId()%></td> --%>
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
												<button type="submit" class="btn btn-info">details</button>
											</form>
										</td>
										<td>
											<form action="OrderPDFServlet" method="get" target="_blank">
												<input type="hidden" name="orderNumber"
													value="<%=orderDTO.getOrderNumber()%>">
												<button type="submit" class="btn btn-print">Print</button>
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
							<form action="OrderPDFServlet" method="get" target="_blank">
								<input type="hidden" name="orderNumber"
									value="<%=order.getOrderNumber()%>">
								<button type="submit" class="btn btn-primary bg-secondary-hover">Print</button>
							</form>

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
