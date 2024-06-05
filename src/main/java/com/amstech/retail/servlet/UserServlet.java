package com.amstech.retail.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import com.amstech.retail.dao.UserDAO;
import com.amstech.retail.dto.UserDTO;
import com.amstech.retail.service.UserService;
import com.amstech.retail.util.DBUtil;

@WebServlet("/user")

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private DBUtil dbUtil;
	private UserService userService;

	public UserServlet() {
		super();
		this.dbUtil = new DBUtil();
		this.userDAO = new UserDAO(dbUtil);
		this.userService = new UserService(userDAO);

	}

	public void init(ServletConfig config) throws ServletException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String task = request.getParameter("task");
		System.out.println(task);

		if (task.equalsIgnoreCase("findById") || task.equalsIgnoreCase("itemAddById")
				|| task.equalsIgnoreCase("queryAddById")) {
			findById(request, response);
		} else {
			System.out.println("method not found doget");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String task = request.getParameter("task");
		System.out.println(task);

		if (task.equalsIgnoreCase("login")) {
			findByUsernameAndPassword(request, response);
		} else if (task.equalsIgnoreCase("signup")) {
			save(request, response);
		} else if (task.equalsIgnoreCase("updateById")) {
			update(request, response);
		} else {
			System.out.println("method not found");
		}
	}

	public void destroy() {

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
				request.setAttribute("message", "confirm password is not same as password");
				request.setAttribute("redirectURL", "signup.jsp");

				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
//			response.sendRedirect("signup.jsp");	

			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Server Failed " + e.getMessage());
			request.setAttribute("redirectURL", "profileupdate.jsp");

			dispatcher.forward(request, response);
		}

	}

	private String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(12));
	}

//	update user set name = ?, mobile_number = ?, email = ?, gst_number = ?, address = ?,update_datetime = now() where id = ?";

	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {

			UserDTO userDTO = new UserDTO();
			System.out.println("update method invoked");
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println("updateById Id : " + id);
			userDTO.setId(id);

			userDTO.setName(request.getParameter("name"));
			userDTO.setMobile_number(request.getParameter("mobile-number"));
			userDTO.setEmail(request.getParameter("email"));
			userDTO.setGstNumber(request.getParameter("gst-number"));
			userDTO.setAddress(request.getParameter("address"));

			int count = userService.update(userDTO);

			if (count > 0) {
				System.out.println("User updated successfully");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "success");
				request.setAttribute("message", "User updated successfully");
				request.setAttribute("redirectURL", "home.jsp");

				dispatcher.forward(request, response);

			} else {
				System.out.println("Failed to create user data..");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "Failed to update user data..");
				request.setAttribute("redirectURL", "profileupdate.jsp");

				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Server Failed " + e.getMessage());
			request.setAttribute("redirectURL", "profileupdate.jsp");

			dispatcher.forward(request, response);
		}
	}

	public void findById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));

			UserDTO userDTO = userService.findById(id);

			if (userDTO != null) {

				System.out.println("user id :" + userDTO.getId());
				System.out.println("user id :" + userDTO.getName());
				System.out.println("user id :" + userDTO.getAddress());
				System.out.println("user id :" + userDTO.getEmail());
				System.out.println("user id :" + userDTO.getGstNumber());
				System.out.println("user found successfully");

				
					RequestDispatcher dispatcher = request.getRequestDispatcher("updateStore.jsp");

					request.setAttribute("userDTOEdit", userDTO);

					dispatcher.forward(request, response);
//				} else if (request.getParameter("task").equalsIgnoreCase("itemAddById")) {
//					RequestDispatcher dispatcher = request.getRequestDispatcher("item.jsp");
//					request.setAttribute("userDTOEdit", userDTO);
//
//					dispatcher.forward(request, response);
//
//				} else if (request.getParameter("task").equalsIgnoreCase("queryAddById")) {
//					RequestDispatcher dispatcher = request.getRequestDispatcher("query.jsp");
//					request.setAttribute("userDTOEdit", userDTO);
//
//					dispatcher.forward(request, response);
//
//				}

			}

			else {
				System.out.println("cannot find user.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "invalid id");
				request.setAttribute("redirectURL", "home.jsp");

				dispatcher.forward(request, response);

			}

		} catch (Exception e) {
			e.printStackTrace();

			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "failed to find user by id exception to: " + e.getMessage());
			request.setAttribute("redirectURL", "home.jsp");

			dispatcher.forward(request, response);

		}
	}

	public void findByUsernameAndPassword(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			UserDTO userDTO = userService.findByUsernameAndPassword(username);
			HttpSession session = request.getSession();

			if (userDTO != null && checkPassword(password, userDTO.getPassword())) {
				 System.out.println("login success");

				    session.setAttribute("auth", userDTO);

				    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
				    response.setHeader("Pragma", "no-cache");
				    response.setDateHeader("Expires", 0);

				    RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
				    request.setAttribute("loginUserDTO", userDTO);
				    dispatcher.forward(request, response);

			} else {
				System.out.println("cannot find user.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "invalid user");
				request.setAttribute("redirectURL", "login.jsp");

				dispatcher.forward(request, response);

			}

		} catch (Exception e) {
			e.printStackTrace();
//		response.sendRedirect("login.jsp");
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "failed to Login due to: " + e.getMessage());
			request.setAttribute("redirectURL", "login.jsp");

			dispatcher.forward(request, response);

		}
	}

	private boolean checkPassword(String password, String storedHashedPassword) {

		return BCrypt.checkpw(password, storedHashedPassword);

	}

}
