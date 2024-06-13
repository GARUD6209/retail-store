package com.amstech.retail.auth;

import jakarta.servlet.RequestDispatcher;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private DBUtil dbUtil;
	private UserService userService;
    
    public LoginServlet() {
    	this.dbUtil = new DBUtil();
		this.userDAO = new UserDAO(dbUtil);
		this.userService = new UserService(userDAO);
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		findByUsernameAndPassword(request, response);
		
		
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
