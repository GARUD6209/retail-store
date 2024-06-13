package com.amstech.retail.auth;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.amstech.retail.dao.UserDAO;
import com.amstech.retail.service.UserService;
import com.amstech.retail.util.DBUtil;
import com.amstech.retail.util.MailUtil;
import com.amstech.retail.util.OTPUtil;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private DBUtil dbUtil;
	private UserService userService;
	private MailUtil mailUtil;

	public ResetPasswordServlet() {
		this.dbUtil = new DBUtil();
		this.userDAO = new UserDAO(dbUtil);
		this.userService = new UserService(userDAO);

			this.mailUtil = new MailUtil();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String task = request.getParameter("task");
	        System.out.println(task);
		
		if (task.equalsIgnoreCase("requestPasswordReset")) {
            requestPasswordReset(request, response);
        } else if (task.equalsIgnoreCase("updatePassword")) {
            updatePassword(request, response);
        } else {
            System.out.println("method not found in reset password ");
        }
	}
	
	 public void requestPasswordReset(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        try {
	            String email = request.getParameter("email");
	            System.out.println("In reset password method to send password : "+email);

	            // Generate OTP
	            String otp = OTPUtil.generateOTP();
	            System.out.println("Generated OTP: " + otp);

	            // Save OTP to database
	            userService.saveOTP(email, otp);

	            // Send OTP to user's email
	            String subject = "Password Reset Request for RConsole";
	            String messageContent = "Your OTP for password reset is: " + otp;
	            mailUtil.sendEmail(email, subject, messageContent);
	            System.out.println("OTP email sent successfully");

	            RequestDispatcher dispatcher = request.getRequestDispatcher("otpform.jsp");
	            request.setAttribute("status", "success");
	            request.setAttribute("message", "OTP sent to your email");
	            dispatcher.forward(request, response);
	        } catch (Exception e) {
	            e.printStackTrace();
	            RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
	            request.setAttribute("status", "error");
	            request.setAttribute("message", "Server Failed " + e.getMessage());
	            dispatcher.forward(request, response);
	        }
	    }

	    public void updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        try {
	            String email = request.getParameter("email");
	            String otp = request.getParameter("otp");
	            String newPassword = request.getParameter("newPassword");

	            int count = userService.updatePassword(email, otp, newPassword);

	            if (count > 0) {
	                System.out.println("Password updated successfully");
	                RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
	                request.setAttribute("status", "success");
	                request.setAttribute("message", "Password updated successfully");
	                request.setAttribute("redirectURL", "login.jsp");

	                dispatcher.forward(request, response);
	            } else {
	                System.out.println("Failed to update password");
	                RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
	                request.setAttribute("status", "error");
	                request.setAttribute("message", "Failed to update password");
	                request.setAttribute("redirectURL", "home.jsp");

	                dispatcher.forward(request, response);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
	            request.setAttribute("status", "error");
	            request.setAttribute("message", "Server Failed " + e.getMessage());
	            request.setAttribute("redirectURL", "home.jsp");

	            dispatcher.forward(request, response);
	        }
	    }

}
