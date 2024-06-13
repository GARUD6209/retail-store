<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Sign Up</title>
<style>
body {
	margin: 30;
	padding: 30;
	font-family: Roboto, sans-serif;
	background-repeat: no-repeat;
	background-size: cover;
	background: linear-gradient(120deg, #007bff, #d0314c);
	height: 100vh;
	overflow-x: hidden;
	overflow-y: auto;
	display: flex;
	justify-content: center;
	align-items: center;
}

.center {
	position: absolute;
	top: 60%;
	left: 50%;
	transform: translate(-50%, -50%);
	width: 25vw;
	background: white;
	border-radius: 10px;
	padding: 20px 20px;
}

.center h1 {
	text-align: center;
	padding: 0 0 20px 0;
	border-bottom: 1px solid silver;
}

.center form {
	padding: 0 30px;
	box-sizing: border-box;
}

form .txt_field {
	position: relative;
	border-bottom: 2px solid #adadad;
	margin: 20px 0;
}

.txt_field input, .txt_field select {
	width: 100%;
	padding: 0 5px;
	height: 40px;
	font-size: 16px;
	border: none;
	background: none;
	outline: none;
}

.txt_field label {
	position: absolute;
	top: 50%;
	left: 5px;
	color: #adadad;
	transform: translateY(-50%);
	font-size: 16px;
	pointer-events: none;
}

.txt_field span::before {
	content: '';
	position: absolute;
	top: 40px;
	left: 0;
	width: 0px;
	height: 2px;
	background: #2691d9;
	transition: .5s;
}

.txt_field input:focus ~ label, .txt_field input:valid ~ label,
	.txt_field select:focus ~ label, .txt_field select:valid ~ label {
	top: -5px;
	color: #2691d9;
}

.txt_field input:focus ~ span::before, .txt_field input:valid ~ span::before,
	.txt_field select:focus ~ span::before, .txt_field select:valid ~ span::before
	{
	width: 100%;
}

input[type="Submit"] {
	width: 100%;
	height: 50px;
	border: 1px solid;
	border-radius: 25px;
	font-size: 18px;
	font-weight: 700;
	cursor: pointer;
}

input[type="Submit"]:hover {
	background: #2691d9;
	color: #e9f4fb;
	transition: .5s;
}

.signup_link {
	margin: 20px 0;
	text-align: center;
	font-size: 16px;
	color: #666666;
}

.signup_link a {
	color: #2691d9;
	text-decoration: none;
}

.signup_link a:hover {
	text-decoration: underline;
}

.HomeAbout {
	width: 100vw;
	height: 25vh;
}
</style>
</head>
<body>
	<div class="container">
		<div class="center">
			<h1>Store Registration</h1>
			<form action="signup" method="post">
				<!-- <input type="hidden" name="task" value="signup" /> -->

				<div class="txt_field">
					<select name="city-id" required>
						<option value="" disabled selected>Select City</option>
						<option value="1">Indore</option>
						<option value="2">Bhopal</option>
						<option value="3">Jabalpur</option>
						<option value="4">Delhi</option>
						<option value="5">Mumbai</option>
					</select> <span></span>

				</div>

				<div class="txt_field">
					<input type="text" name="name" required> <span></span> <label>Store
						Name</label>
				</div>
				<div class="txt_field">
					<input type="text" name="mobile-number" required> <span></span>
					<label>Mobile Number</label>
				</div>
				<div class="txt_field">
					<input type="email" name="email" required> <span></span> <label>Email
						Address</label>
				</div>
				<div class="txt_field">
					<input type="text" name="gst-number" required> <span></span>
					<label>GST Number</label>
				</div>
				<div class="txt_field">
					<input type="text" name="address" required> <span></span> <label>Address</label>
				</div>
				<div class="txt_field">
					<input type="password" name="password" required> <span></span>
					<label>Password</label>
				</div>
				<div class="txt_field">
					<input type="password" name="confirm-password" required> <span></span>
					<label>Confirm Password</label>
				</div>
				<input name="submit" type="Submit" value="Register">
				<div class="signup_link">
					Already have an account? <a href="login.jsp">Login Here</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
