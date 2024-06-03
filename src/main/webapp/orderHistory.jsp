<%@ page import="com.amstech.retail.dto.UserDTO"%>
<%@ page import="com.amstech.retail.dto.OrderDTO"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Order History</title>
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
    List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders"); // Consistent attribute name
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
                    <%
                    if (orders != null && !orders.isEmpty()) {
                    %>
                    <table class="table table-bordered table-hover mt-4">
                        <thead class="thead-light">
                            <tr>
                                <th>Order ID</th>
                                <th>Total Amount</th>
                                <th>Status</th>
                                <th>Customer Name</th>
                                <th>Customer Number</th>
                                <th>Create Date</th>
                                <th>Update Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            for (OrderDTO orderDTO : orders) {
                            %>
                            <tr>
                                <td><%=orderDTO.getOrderId()%></td>
                                <td><%=orderDTO.getTotalAmount()%></td>
                                <td><%=orderDTO.getStatus()%></td>
                                <td><%=orderDTO.getCustomerName()%></td>
                                <td><%=orderDTO.getCustomerNumber()%></td>
                                <td><%=orderDTO.getOCreateDateTime()%></td>
                                <td><%=orderDTO.getOUpdateDateTime()%></td>
                            </tr>
                            <%
                            }
                            %>
                        </tbody>
                    </table>
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
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
