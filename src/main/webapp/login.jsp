<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>login page</title>

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
        top: 50%;
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
    .txt_field input {
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
    .txt_field input:focus ~ label,
    .txt_field input:valid ~ label {
        top: -5px;
        color: #2691d9;
    }
    .txt_field input:focus ~ span::before,
    .txt_field input:valid ~ span::before {
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
    <div class="center">
        <h1>Login</h1>
        <form action="user" method="post" >
         <input type="hidden" name = "task" value="login" />
            <div class="txt_field">
                <input type="text" id="username" name="username" required>
                <label for="mobile">Username</label>
                <span></span>
            </div>
            <div class="txt_field">
                <input type="password" id="password" name="password" required>
                <label for="password">Password</label>
                <span></span>
            </div>
            <input type="submit" value="Login">
        </form>
        
        <div class="signup_link">
            Don't have an account? <a href="signup.jsp">Register Here</a>
        </div>
    </div>
</body>













  
</html>

