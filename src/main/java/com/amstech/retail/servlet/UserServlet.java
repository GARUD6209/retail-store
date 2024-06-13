package com.amstech.retail.servlet;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;



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


		if (task.equalsIgnoreCase("updateById")) {
			update(request, response);
		} else {
			System.out.println("method not found");
		}
	}

	public void destroy() {

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



}
