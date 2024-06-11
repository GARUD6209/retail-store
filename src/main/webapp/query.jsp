<%@ page import="com.amstech.retail.dto.UserDTO"%>
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
<title>Add Query</title>
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
							<h2>Add Query</h2>
							<form action="query" method="post" class="mt-4">
								<input type="hidden" name="task" value="addItem"> <input
									type="hidden" id="storeInfoId" name="storeInfoId"
									value="<%=auth.getId()%>" required>
								<div class="form-group">
									<label for="name">Name</label> <input type="text"
										class="form-control" id="name" name="name" placeholder="Name"
										required>
								</div>
								<div class="form-group">
									<label for="number">Mobile Number</label> <input type="text"
										class="form-control" id="number" name="mobile-number"
										placeholder="Mobile Number" required>
								</div>
								<div class="form-group">
									<label for="description">Description</label>
									<textarea class="form-control" id="description"
										name="description" placeholder="Description" required></textarea>
								</div>
								<button type="submit" class="btn btn-success">Submit</button>
							</form>
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
