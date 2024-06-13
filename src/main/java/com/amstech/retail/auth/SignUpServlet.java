package com.amstech.retail.auth;

import jakarta.mail.MessagingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import com.amstech.retail.dao.UserDAO;
import com.amstech.retail.dto.UserDTO;
import com.amstech.retail.service.UserService;
import com.amstech.retail.util.DBUtil;
import com.amstech.retail.util.MailUtil;


@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private DBUtil dbUtil;
	private UserService userService;
	private MailUtil mailUtil;

	public SignUpServlet() {
		this.dbUtil = new DBUtil();
		this.userDAO = new UserDAO(dbUtil);
		this.userService = new UserService(userDAO);

			this.mailUtil = new MailUtil();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		save(request, response);
	}

	public void save(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			int cityID = Integer.parseInt(request.getParameter("city-id"));
			String name = request.getParameter("name");
			String mobileNumber = request.getParameter("mobile-number");
			String GSTNumber = request.getParameter("gst-number");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirm-password");

			if (password.equals(confirmPassword)) {
				String hashedPassword = hashPassword(confirmPassword);
				UserDTO userDTO = new UserDTO();
				userDTO.setCityID(cityID);
				userDTO.setName(name);
				userDTO.setMobile_number(mobileNumber);
				userDTO.setPassword(hashedPassword);
				userDTO.setGstNumber(GSTNumber);
				userDTO.setAddress(address);
				userDTO.setEmail(email);
				int count = userService.save(userDTO);
				if (count > 0) {
					System.out.println("User created successfully");

					// Send welcome email
					String subject = "Welcome to RConsole";
					String messageContent = "Dear " + name
							+ ",\n\nThank you for registering with us.\n\nBest regards,\nRConsole";
					try {
						mailUtil.sendEmail(email, subject, messageContent);
						System.out.println("Welcome email sent successfully");
					} catch (MessagingException e) {
						System.out.println("Failed to send welcome email");
						e.printStackTrace();
					}

					RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
					request.setAttribute("status", "success");
					request.setAttribute("message", "User created successfully");
					request.setAttribute("redirectURL", "login.jsp");
					dispatcher.forward(request, response);
				} else {
					System.out.println("Failed to create user data..");
					RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
					request.setAttribute("status", "error");
					request.setAttribute("message", "Failed to create user data..");
					request.setAttribute("redirectURL", "signup.jsp");
					dispatcher.forward(request, response);
				}
			} else {
				System.out.println("Failed to create user data..");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "Confirm password is not the same as password");
				request.setAttribute("redirectURL", "signup.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Server Failed " + e.getMessage());
			request.setAttribute("redirectURL", "signup.jsp");
			dispatcher.forward(request, response);
		}
	}

	private String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(12));
	}
}
