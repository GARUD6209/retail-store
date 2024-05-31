package com.amstech.retail.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.amstech.retail.dao.OrderItemDAO;
import com.amstech.retail.dto.OrderItemDTO;
import com.amstech.retail.service.OrderItemService;
import com.amstech.retail.util.DBUtil;

@WebServlet("/orderItem")


public class OrderItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DBUtil dbutil;
	private OrderItemService orderItemService;
	private OrderItemDAO orderItemDAO;

	public OrderItemServlet() {
		super();
		this.dbutil = new DBUtil();
		this.orderItemDAO = new OrderItemDAO(dbutil);
		this.orderItemService = new OrderItemService(orderItemDAO);

	}

	public void init(ServletConfig config) throws ServletException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		save(request, response);
	}


	public void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			OrderItemDTO orderItemDTO = new OrderItemDTO();
			orderItemDTO.setOrderDetailId(Integer.parseInt(request.getParameter("order-detail-id")));
			orderItemDTO.setItemId(Integer.parseInt(request.getParameter("item-id")));
			orderItemDTO.setPriceAtOrder(Integer.parseInt(request.getParameter("price-at-order")));
			

			int count = orderItemService.save(orderItemDTO);
			if (count > 0) {
				System.out.println("order Item inserted successfully");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "success");
				request.setAttribute("message", "order item generated successfully");
				request.setAttribute("redirectURL", "home.jsp");

				dispatcher.forward(request, response);

			} else {
				System.out.println("Failed to generate order item data..");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "Failed to generate order item data..");
				request.setAttribute("redirectURL", "order-item.jsp");

				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
//		response.sendRedirect("signup.jsp");	

			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Server Failed " + e.getMessage());
			request.setAttribute("redirectURL", "order-item.jsp");

			dispatcher.forward(request, response);
		}
	}
	public void destroy() {

	}


}
