<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.amstech.retail.dto.*" %>

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
        }
        .sidebar {
            width: 250px;
            height: 100vh;
            position: fixed;
            top: 0;
            left: 0;
            background-color: #343a40;
            padding-top: 20px;
        }
        .sidebar a, .sidebar form {
            display: block;
            width: 100%;
        }
        .sidebar a, .sidebar button {
            padding: 15px;
            text-align: center;
            text-decoration: none;
            font-size: 18px;
            color: white;
            border: none;
            background: none;
        }
        .sidebar a:hover, .sidebar button:hover {
            background-color: #575d63;
        }
        .sidebar .btn {
            width: 100%;
            padding: 0;
        }
        .main-content {
            margin-left: 250px;
            padding: 20px;
            width: 100%;
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
        <form action="user" method="get">
            <input type="hidden" name="task" value="findById">
            <input type="hidden" name="id" value="<%=auth.getId() %>">
            <button type="submit" class="btn btn-primary">Edit Profile</button>
        </form>
        <a href="item.jsp">Add Item</a>
        <form action="items" method="get">
            <input type="hidden" name="task" value="findAllItems">
            <input type="hidden" name="id" value="<%=auth.getId()%>">
            <button type="submit" class="btn btn-link text-white">Find All Items</button>
        </form>
        <a href="query.jsp">Add Query</a>
    </div>

    <div class="main-content">
        <div class="container">
            <div class="row">
                <div class="col">
                    <h2>Welcome to the Dashboard</h2>
                    <p>Select an option from the sidebar to proceed.</p>
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
