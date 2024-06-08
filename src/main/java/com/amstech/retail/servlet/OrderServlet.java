package com.amstech.retail.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.amstech.retail.dao.OrderDAO;
import com.amstech.retail.dto.OrderDTO;
import com.amstech.retail.service.OrderService;
import com.amstech.retail.util.DBUtil;

@WebServlet("/order")

public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBUtil dbUtil;
	private OrderDAO orderDAO;
	private OrderService orderService;

	public OrderServlet() {
		super();
		this.dbUtil = new DBUtil();
		this.orderDAO = new OrderDAO(dbUtil);
		this.orderService = new OrderService(orderDAO);
	}

	public void init(ServletConfig config) throws ServletException {
		// Initialization code if needed
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String task = request.getParameter("task");
		if ("findAllOrdersByStoreId".equalsIgnoreCase(task)) {
			System.out.println("Calling findAllOrdersByStoreId");
			findAllOrdersByStoreId(request, response);

		} else if ("searchOrderByOrderNumber".equalsIgnoreCase(task)) {
			System.out.println("Calling searchOrderByOrderNumber");
			searchOrderByOrderNumber(request, response);
		} else if ("findOrdersByDateRange".equalsIgnoreCase(task)) {
            System.out.println("Calling findOrdersByDateRange");
            findOrdersByDateRange(request, response);
        }else {
			System.out.println("Method not found");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String task = request.getParameter("task");
		if ("createOrder".equalsIgnoreCase(task)) {
			saveOrder(request, response);
		} else {
			System.out.println("Method not found");
		}
	}

	public void saveOrder(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {

			String customerName = request.getParameter("customerName");
			String customerNumber = request.getParameter("customerNumber");
			String paymentStatus = request.getParameter("paymentStatus");
			double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
			String[] itemIds = request.getParameterValues("itemIds");
			String[] quantities = new String[itemIds.length];
			String[] priceAtOrder = request.getParameterValues("price-at-order");
			String[] itemName = request.getParameterValues("item-name");

			for (int i = 0; i < itemIds.length; i++) {
				quantities[i] = request.getParameter("quantities_" + itemIds[i]);
			}

			// Generate unique order number using current date and time
			String orderNumber = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

			// Create OrderDTO object
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setCustomerName(customerName);
			orderDTO.setCustomerNumber(customerNumber);
			orderDTO.setStatus(paymentStatus);
			orderDTO.setTotalAmount(totalAmount);
			orderDTO.setPriceAtOrder(priceAtOrder);
			orderDTO.setOrderItemIds(itemIds);
			orderDTO.setQuantities(quantities);
			orderDTO.setOrderItemNames(itemName);
			orderDTO.setOrderNumber(orderNumber);

			// Save order using OrderService
			int count = orderService.save(orderDTO);

			if (count > 0) {

				System.out.println("Order created successfully");

			

				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "success");
				request.setAttribute("message", "Order created successfully");
				request.setAttribute("redirectURL", "home.jsp");
				dispatcher.forward(request, response);
			} else {
				System.out.println("Failed to create order");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "Failed to create order");
				request.setAttribute("redirectURL", "home.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Server error: " + e.getMessage());
			request.setAttribute("redirectURL", "home.jsp");
			dispatcher.forward(request, response);
		}
	}



	private void searchOrderByOrderNumber(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String orderNumber = request.getParameter("orderNumber");
			OrderDTO order = orderService.findOrderDetailsByOrderNumber(orderNumber);

			if (order != null) {
				request.setAttribute("order", order);
				RequestDispatcher dispatcher = request.getRequestDispatcher("orderHistory.jsp");
				dispatcher.forward(request, response);
			} else {
				System.out.println("Order not found for order number: " + orderNumber);
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "info");
				request.setAttribute("message", "No order found with this order number.");
				request.setAttribute("redirectURL", "orderHistory.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Server error: " + e.getMessage());
			request.setAttribute("redirectURL", "orderHistory.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void findAllOrdersByStoreId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int storeId = Integer.parseInt(request.getParameter("id"));
			List<OrderDTO> orders = orderService.findAllOrdersByStoreId(storeId);

			if (orders != null && !orders.isEmpty()) {
				request.setAttribute("orders", orders); // Consistent attribute name
				RequestDispatcher dispatcher = request.getRequestDispatcher("orderHistory.jsp");
				dispatcher.forward(request, response);
			} else {
				System.out.println("No orders found for store ID: " + storeId);
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "info");
				request.setAttribute("message", "No orders found for this store.");
				request.setAttribute("redirectURL", "home.jsp");
				dispatcher.forward(request, response);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Invalid store ID: " + e.getMessage());
			request.setAttribute("redirectURL", "home.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Server error: " + e.getMessage());
			request.setAttribute("redirectURL", "home.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	 private void findOrdersByDateRange(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        try {
	            String startDateStr = request.getParameter("startDate");
	            String endDateStr = request.getParameter("endDate");
	            Date startDate = parseDate(startDateStr);
	            Date endDate = parseDate(endDateStr);
	            List<OrderDTO> orders = orderService.findOrdersByDateRange(new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));

	            if (orders != null && !orders.isEmpty()) {
	                request.setAttribute("orders", orders);
	                RequestDispatcher dispatcher = request.getRequestDispatcher("orderHistory.jsp");
	                dispatcher.forward(request, response);
	            } else {
	                System.out.println("No orders found for the date range: " + startDateStr + " to " + endDateStr);
	                RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
	                request.setAttribute("status", "info");
	                request.setAttribute("message", "No orders found for the specified date range.");
	                request.setAttribute("redirectURL", "home.jsp");
	                dispatcher.forward(request, response);
	            }
	        } catch (ParseException e) {
	            e.printStackTrace();
	            RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
	            request.setAttribute("status", "error");
	            request.setAttribute("message", "Invalid date format: " + e.getMessage());
	            request.setAttribute("redirectURL", "home.jsp");
	            dispatcher.forward(request, response);
	        } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	            RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
	            request.setAttribute("status", "error");
	            request.setAttribute("message", "Invalid date range: " + e.getMessage());
	            request.setAttribute("redirectURL", "home.jsp");
	            dispatcher.forward(request, response);
	        } catch (Exception e) {
	            e.printStackTrace();
	            RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
	            request.setAttribute("status", "error");
	            request.setAttribute("message", "Server error: " + e.getMessage());
	            request.setAttribute("redirectURL", "home.jsp");
	            dispatcher.forward(request, response);
	        }
	    }

	    private Date parseDate(String dateStr) throws ParseException {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        return sdf.parse(dateStr);
	    }

}
