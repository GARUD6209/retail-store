<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Request Otp</title>
<%@ include file="csslinks.jsp" %>
</head>
<body>
<div class="container">
        <div class="row">
       
            <div class="col-md-12">
                <div class="card mt-5">
                    <div class="card-header text-center">
                        <h3>Reset Password</h3>
                    </div>
                    <div class="card-body">
                        <form action="reset-password" method="post">
                            <input type="hidden" name="task" value="requestPasswordResetFromLogin" />

                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email" required>
                            </div>

                            

                            <button type="submit" class="btn btn-primary btn-block">request otp</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>