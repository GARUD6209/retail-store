package com.amstech.retail.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LogoutServlet() {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session != null) {
			try {
				System.out.println("logout working .....");
				// Invalidate the session to log out the user
				session.invalidate();
				// Redirect the user to the login page
				response.sendRedirect("login.jsp");
			} catch (Exception e) {
				// Log the error and handle it accordingly
				e.printStackTrace();
				// Forward the request to an error page or display an error message
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		}else {
			response.sendRedirect("login.jsp");
		}

	}

}
