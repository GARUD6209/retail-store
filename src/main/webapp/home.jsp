<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.amstech.retail.dto.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Dashboard</title>
<!-- Bootstrap CSS -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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
        <a href="home.jsp">Home</a>
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
        <a href="item.jsp">Add Item</a>
        <form action="items" method="get">
            <input type="hidden" name="task" value="findAllItems">
            <input type="hidden" name="id" value="<%=auth.getId()%>">
            <button type="submit" class="btn text-white">Find All Items</button>
        </form>
        <a href="query.jsp">Add Query</a>
    </div>
	<div class="main-content">
		<div class="container">
			<div class="row">
				<div class="col">
					<h2>Welcome, <%=auth.getName()%></h2>
					<form action="items" method="get">
						<input type="hidden" name="task" value="findAllItemsToCreateOrder"> 
						<input type="hidden" name="id" value="<%=auth.getId()%>">
						<button style="background-color: black" type="submit" class="btn text-white">Create An Order</button>
					</form>

					<%
					List<ItemDTO> itemDTOList = (List) request.getAttribute("itemDTOListForOrder");
					%>

					<%
					if (itemDTOList != null && !itemDTOList.isEmpty()) {
					%>
					<form action="order" method="post" class="mt-4">
					 <input type="hidden" name ="task" value="createOrder">
						<table class="table table-bordered table-hover">
							<thead class="thead-light">
								<tr>
									<th>Select</th>
									<th>Name</th>
									<th>Price</th>
									<th>Description</th>
									<th>Quantity</th>
								</tr>
							</thead>
							<tbody>
								<%
								for (ItemDTO itemDTO : itemDTOList) {
								%>
								<tr>
									<td><input type="checkbox" name="itemIds" value="<%=itemDTO.getId()%>" class="item-checkbox"></td>
									<td><%=itemDTO.getName()%></td>
									<td id="price_<%=itemDTO.getId()%>"><input type="text" name="price-at-order" value="<%=itemDTO.getCurrent_price()%>" >
									</td>
									<td><%=itemDTO.getDescription()%></td>
									<td><input type="number" name="quantities_<%=itemDTO.getId()%>" min="1" max="99" value="1" class="item-quantity"></td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
						<h4>Order Summary</h4>
						<div class="form-group">
							<label for="customerName">Customer Name</label>
							<input type="text" id="customerName" name="customerName" class="form-control" required>
						</div>
						<div class="form-group">
							<label for="customerNumber">Customer Number</label>
							<input type="text" id="customerNumber" name="customerNumber" class="form-control" required>
						</div>
						<div class="form-group">
							<label for="paymentStatus">Payment Status</label>
							<select id="paymentStatus" name="paymentStatus" class="form-control" required>
								<option value="Paid">Paid</option>
								<option value="Pending">Pending</option>
							</select>
						</div>
						<div class="form-group">
							<label for="totalAmount">Total Amount</label>
							<input type="text" id="totalAmount" class="form-control" readonly>
							<input type="hidden" id="totalAmountInput" name="totalAmount">
						</div>
						<button type="submit" class="btn btn-success">Create Order</button>
					</form>
					<%
					} else {
					%>
					<p>No items found.</p>
					<%
					}
					%>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS, Popper.js, and jQuery -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

	<!-- Custom Script -->
	<script>
	document.addEventListener('DOMContentLoaded', () => {
		function calculateTotal() {
			const items = document.querySelectorAll('input[name="itemIds"]:checked');
			let totalAmount = 0;

			items.forEach(item => {
				const itemId = item.value;
				const quantityElement = document.querySelector(`input[name="quantities_${itemId}"]`);
				const priceElement = document.querySelector(`#price_${itemId}`);

				if (quantityElement && priceElement) {
					const quantity = parseInt(quantityElement.value, 10);
					const price = parseFloat(priceElement.textContent);
					totalAmount += quantity * price;
				}
			});

			document.getElementById('totalAmount').value = totalAmount.toFixed(2);
			document.getElementById('totalAmountInput').value = totalAmount.toFixed(2);
		}

		const checkboxes = document.querySelectorAll('.item-checkbox');
		const quantityInputs = document.querySelectorAll('.item-quantity');

		checkboxes.forEach(checkbox => {
			checkbox.addEventListener('change', calculateTotal);
		});

		quantityInputs.forEach(input => {
			input.addEventListener('input', calculateTotal);
		});

		// Initial calculation
		calculateTotal();
	});
	</script>
</body>
</html>
