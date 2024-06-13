<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.amstech.retail.dto.*"%>

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
    <title>Dashboard</title>
  
     <%@ include file="csslinks.jsp" %>
    
 
</head>
<body>
	<nav class="navbar navbar-expand-md  fixed-top">
		
	</nav>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="sidebar.jsp"%>
			<div class="main-content ">
				<div class="container">
					<div class="row">
						<div class="col">
							<h2>
								Welcome,
								<%=auth.getName()%></h2>
							<form action="items" method="get">
								<input type="hidden" name="task"
									value="findAllItemsToCreateOrder"> <input type="hidden"
									name="id" value="<%=auth.getId()%>">
								<button type="submit" class="btn btn-info">Create An
									Order</button>
							</form>

							<%
                    List<ItemDTO> itemDTOList = (List) request.getAttribute("itemDTOListForOrder");
                    %>

							<%
                    if (itemDTOList != null && !itemDTOList.isEmpty() ) {
                    %>
							<form action="order" method="post" class="mt-4">
								<input type="hidden" name="task" value="createOrder">
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
										<% for (ItemDTO itemDTO : itemDTOList) { %>
										<tr>
											<td><input type="checkbox" name="itemIds"
												value="<%=itemDTO.getId()%>" class="item-checkbox">
											</td>
											<td><%=itemDTO.getName()%> <input type="hidden"
												name="item-name" value="<%=itemDTO.getName()%>"></td>
											<td id="price_<%=itemDTO.getId()%>"><input type="text"
												name="price-at-order"
												value="<%=itemDTO.getCurrent_price()%>"></td>
											<td><%=itemDTO.getDescription()%></td>
											<td><input type="number"
												name="quantities_<%=itemDTO.getId()%>" min="1" max="99"
												value="1" class="item-quantity"></td>
										</tr>
										<% } %>
									</tbody>
								</table>
								<h4>Order Summary</h4>
								<div class="form-group">
									<label for="customerName">Customer Name</label> <input
										type="text" id="customerName" name="customerName"
										class="form-control" required>
								</div>
								<div class="form-group">
									<label for="customerNumber">Customer Number</label> <input
										type="text" id="customerNumber" name="customerNumber"
										class="form-control" required>
								</div>
								<div class="form-group">
									<label for="paymentStatus">Payment Status</label> <select
										id="paymentStatus" name="paymentStatus" class="form-control"
										required>
										<option value="Paid">Paid</option>
										<option value="Pending">Pending</option>
									</select>
								</div>
								<div class="form-group">
									<label for="totalAmount">Total Amount</label> <input
										type="text" id="totalAmount" class="form-control" readonly>
									<input type="hidden" id="totalAmountInput" name="totalAmount">
								</div>
								<button type="submit" class="btn btn-success">Create
									Order</button>
							</form>
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
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <!-- Custom Script -->
    <script>
    document.addEventListener('DOMContentLoaded', () => {
        console.log('Script loaded and DOM fully parsed'); // Initial log to check script execution
        function calculateTotal() {
            console.log('calculateTotal function called'); // Log when the function is called
            const items = document.querySelectorAll('input[name="itemIds"]:checked');
            console.log('Number of checked items: ' + items.length); // Log number of checked items
            let totalAmount = 0;
            items.forEach(item => {
                const itemId = item.value;
                const quantityElement = document.querySelector('input[name="quantities_' + itemId + '"]');
                const priceElement = document.querySelector('#price_' + itemId + ' input[name="price-at-order"]');
                console.log('Processing item ID: ' + itemId); // Log item ID
                console.log('Found quantity element: ' + (quantityElement !== null)); // Log if quantity element is found
                console.log('Found price element: ' + (priceElement !== null)); // Log if price element is found
                if (quantityElement && priceElement) {
                    const quantity = parseInt(quantityElement.value, 10);
                    const price = parseFloat(priceElement.value);
                    console.log('Item ID: ' + itemId + ', Quantity: ' + quantity + ', Price: ' + price); // Log quantity and price
                    totalAmount += quantity * price;
                }
            });
            console.log('Total Amount: ' + totalAmount);
            document.getElementById('totalAmount').value = totalAmount.toFixed(2);
            document.getElementById('totalAmountInput').value = totalAmount.toFixed(2);
        }
        const checkboxes = document.querySelectorAll('.item-checkbox');
        const quantityInputs = document.querySelectorAll('.item-quantity');
        const priceInputs = document.querySelectorAll('input[name="price-at-order"]');
        checkboxes.forEach(checkbox => {
            checkbox.addEventListener('change', calculateTotal);
        });
        quantityInputs.forEach(input => {
            input.addEventListener('input', calculateTotal);
        });
        priceInputs.forEach(input => {
            input.addEventListener('input', calculateTotal);
        });
        // Initial calculation
        calculateTotal();
    });
    </script>

</body>
</html>
