<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Sign Up</title>
<style>
*, *:before, *:after {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
}

body {
    background-color: #080710;
    margin: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
}

h2 {
    text-align: center;
    font-size: 32px;
    font-weight: 500;
    color: #ffffff;
    margin-bottom: 20px;
}

form {
    margin-top: 20px;
    height: auto;
    width: 100%;
    max-width: 500px;
    background-color: rgba(255, 255, 255, 0.13);
    position: relative;
    border-radius: 10px;
    backdrop-filter: blur(10px);
    border: 2px solid rgba(255, 255, 255, 0.1);
    box-shadow: 0 0 40px rgba(8, 7, 16, 0.6);
    padding: 50px 35px;
}

form * {
    font-family: 'Poppins', sans-serif;
    color: #ffffff;
    letter-spacing: 0.5px;
    outline: none;
    border: none;
}

form h3 {
    font-size: 32px;
    font-weight: 500;
    line-height: 42px;
    text-align: center;
}

label {
    display: block;
    margin-top: 30px;
    font-size: 16px;
    font-weight: 500;
}

input {
    display: block;
    height: 50px;
    width: 100%;
    background-color: rgba(255, 255, 255, 0.07);
    border-radius: 3px;
    padding: 0 10px;
    margin-top: 8px;
    font-size: 14px;
    font-weight: 300;
}

::placeholder {
    color: #e5e5e5;
}

button {
    margin-top: 50px;
    width: 100%;
    background-color: #ffffff;
    color: #080710;
    padding: 15px 0;
    font-size: 18px;
    font-weight: 600;
    border-radius: 5px;
    cursor: pointer;
}

input[type="submit"]:hover {
    background-color: #e5e5e5;
}

.form-link {
    text-align: center;
    margin-top: 20px;
    color: #ffffff;
}

.form-link a {
    color: #23a2f6;
    text-decoration: none;
}

/* Styles for the radio buttons */
.role-options input[type="radio"] {
    display: none;
}

/* Styles for the labels */
.role-options label {
    display: inline-block;
    padding: 8px 20px;
    font-family: Arial, sans-serif;
    font-size: 16px;
    cursor: pointer;
    border: 2px solid #ccc;
    border-radius: 5px;
    margin-right: 10px;
    margin-bottom: 10px;
}

/* Styles for the labels when the radio button is checked */
.role-options input[type="radio"]:checked+label {
    background-color: #007bff;
    color: #fff;
    border-color: #007bff;
}



@media (max-width: 768px) {
    body {
        margin: 2%;
    }

    form {
        padding: 20px 20px;
        width: 90%;
    }

    h2 {
        font-size: 24px;
    }

    button {
        margin-top: 30px;
        padding: 10px 0;
        font-size: 16px;
    }


}

@media (max-width: 480px) {
    h2 {
        font-size: 20px;
    }

    label {
        font-size: 14px;
    }

    input {
        height: 40px;
        font-size: 12px;
    }

    button {
        font-size: 14px;
    }


}

</style>
</head>
<body>

	<div class="background">
		<div class="shape"></div>
		<div class="shape"></div>
	</div>

	<form action="user" method="post">
	
	 <input type="hidden" name = "task" value="signup" />
	
	<h2>User Information Form</h2>
  
       <input type="text" id="city-id" name="city-id"  value="1" placeholder="City" required><br><br>

        <input type="text" id="name" name="name" placeholder="Name" required><br><br>

        <input type="text" id="mobile-number" name="mobile-number" placeholder="Mobile Number" required><br><br>

        <input type="text" id="gst-number" name="gst-number" placeholder="GST Number" required><br><br>

        <input type="email" id="email" name="email" placeholder="Email" required><br><br>

        <input type="text" id="address" name="address" placeholder="Address" required><br><br>

        <input type="password" id="password" name="password" placeholder="Password" required><br><br>

        <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm Password" required><br><br>

        <input type="submit" value="Submit">
    </form>


</body>
</html>
