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

<%@ include file="csslinks.jsp"%>


</head>
<body>
	<nav class="navbar navbar-expand-md  fixed-top"></nav>
	 <div class="container-fluid">
        <div class="row">
        <%@ include file="sidebar.jsp"%>
            <div class="col-md-6">
                <div class="card mt-5">
                    <div class="card-header text-center">
                        <h3>Reset Password</h3>
                    </div>
                    <div class="card-body">
                        <form action="reset-password" method="post">
                            <input type="hidden" name="task" value="updatePassword" />

                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email" required>
                            </div>

                            <div class="form-group">
                                <label for="otp">OTP</label>
                                <input type="text" class="form-control" id="otp" name="otp" placeholder="Enter OTP" required>
                            </div>

                            <div class="form-group">
                                <label for="newPassword">New Password</label>
                                <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="Enter new password" required>
                            </div>

                            <button type="submit" class="btn btn-primary btn-block">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
	
</body>
</html>
