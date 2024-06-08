<%@ page import="com.amstech.retail.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Billing Site</title>
  <!-- Bootstrap CSS -->
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <style>
    
  <style>
    /* CSS Styles */
    body {
      font-family: 'Montserrat', sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f0f2f5;
      color: #333;
    }

    header {
      background-color: #1a1a2e;
      padding: 20px 40px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      position: fixed;
      width: 100%;
      top: 0;
      z-index: 1000;
    }

    .logo {
      font-size: 24px;
      font-weight: bold;
      color: #fff;
    }

    .nav-links {
      display: flex;
    }

    .nav-links a {
      color: #fff;
      text-decoration: none;
      font-size: 16px;
      margin-left: 20px;
      transition: color 0.3s ease;
    }

    .nav-links a:hover {
      color: #ff8c42;
    }

    .hero {
      background-image: url("assets/paper-surrounded-finance-element.jpg");
      background-size: cover;
      background-position: center;
      padding: 150px 20px;
      text-align: center;
      color: #fff;
      position: relative;
      height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .hero::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.5);
      z-index: 1;
    }

    .hero-content {
      position: relative;
      z-index: 2;
      max-width: 800px;
    }

    .hero h1 {
      font-size: 60px;
      margin-bottom: 20px;
      text-transform: uppercase;
      letter-spacing: 3px;
    }

    .hero p {
      font-size: 24px;
      margin-bottom: 40px;
      line-height: 1.6;
    }

    .cta-buttons a {
      background-color: #ff8c42;
      color: #fff;
      padding: 15px 40px;
      text-decoration: none;
      font-size: 18px;
      border-radius: 5px;
      margin: 0 15px;
      transition: background-color 0.3s ease, color 0.3s ease;
    }

    .cta-buttons a:hover {
      background-color: #fff;
      color: #ff8c42;
      border: 2px solid #ff8c42;
    }

    footer {
      background-color: #1a1a2e;
      color: #fff;
      padding: 20px 0;
      text-align: center;
      font-size: 14px;
      width: 100%;
    }

    .content {
      padding: 20px;
      margin-top: 100px;
    }

    .features {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  margin-top: 40px;
}

.feature {
  background: #f9f9f9; /* Light gray background */
  padding: 20px;
  margin: 10px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  flex-basis: calc(30% - 20px); /* Adjusted width with margin */
  text-align: center;
  transition: transform 0.3s ease, background-color 0.3s ease; /* Added background color transition */
}

.feature:hover {
  transform: translateY(-5px);
  background-color: #e0e0e0; /* Lighter gray background on hover */
}

.feature h3 {
  margin-bottom: 15px;
  font-size: 24px;
  color: #333; /* Darker text color */
}

.feature p {
  font-size: 16px;
  color: #666;
}


    @media (max-width: 768px) {
      .hero h1 {
        font-size: 40px;
      }

      .hero p {
        font-size: 18px;
      }

      .cta-buttons a {
        font-size: 16px;
        padding: 10px 20px;
        margin: 0 10px;
      }

      .feature {
        flex-basis: 100%;
        margin: 10px 0;
      }
      
    }
  </style>
</head>
<body>
  <header>
    <div class="logo">RConsole</div>
    <div class="nav-links">
      <a href="login.jsp">Login</a>
      <a href="signup.jsp">Signup</a>
    </div>
  </header>

  <div class="hero">
    <div class="hero-content">
      <h1 style="color: black;">Welcome to RConsole</h1>
      <p style="color: black;">Streamline your billing process with our powerful tools. Efficiently manage all your bills in one place.</p>
      <div class="cta-buttons">
        <a href="login.jsp">Login</a>
        <a href="signup.jsp">Sign Up</a>
      </div>
    </div>
  </div>

  <div class="content">
    <section class="features">
      <div class="feature">
        <h3>Track Your Bills</h3>
        <p>Keep an eye on all your bills and their due dates, so you never miss a payment.</p>
      </div>
      <div class="feature">
        <h3>Easy Payments</h3>
        <p>Pay your bills quickly and securely with our integrated payment options.</p>
      </div>
      <div class="feature">
        <h3>Detailed Reports</h3>
        <p>Generate detailed reports to get insights into your spending patterns.</p>
      </div>
    </section>
  </div>

  <footer>
    <p>&copy; 2024 Amstech. All rights reserved.</p>
  </footer>

  <!-- Bootstrap JS, Popper.js, and jQuery -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
