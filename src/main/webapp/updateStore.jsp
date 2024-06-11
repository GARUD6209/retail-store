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
<%@ include file="csslinks.jsp"%>

</head>
<body>
	<nav class="navbar navbar-expand-md  fixed-top">
		
	</nav>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="sidebar.jsp"%>

			<div class="main-content fixed-top">
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
