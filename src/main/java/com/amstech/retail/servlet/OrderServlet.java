package com.amstech.retail.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	   String task = request.getParameter("task");
	           if ("findAllOrdersByStoreId".equalsIgnoreCase(task)) {
	        	      System.out.println("Calling findAllOrdersByStoreId");
	               findAllOrdersByStoreId(request, response);
	           } else {
	               System.out.println("Method not found");
	           }
	    }

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String task = request.getParameter("task");
	        if ("createOrder".equalsIgnoreCase(task)) {
	            saveOrder(request, response);
	        } else {
	            System.out.println("Method not found");
	        }
	    }

	    public void saveOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        try {
	            // Retrieve form parameters
	            String customerName = request.getParameter("customerName");
	            String customerNumber = request.getParameter("customerNumber");
	            String paymentStatus = request.getParameter("paymentStatus");
	            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
	            String[] itemIds = request.getParameterValues("itemIds");
	            String[] quantities = new String[itemIds.length];
	            String[] priceAtOrder = request.getParameterValues("price-at-order");

	            for (int i = 0; i < itemIds.length; i++) {
	                quantities[i] = request.getParameter("quantities_" + itemIds[i]);
	            }

	            // Create OrderDTO object
	            OrderDTO orderDTO = new OrderDTO();
	            orderDTO.setCustomerName(customerName);
	            orderDTO.setCustomerNumber(customerNumber);
	            orderDTO.setStatus(paymentStatus);
	            orderDTO.setTotalAmount(totalAmount);
	            orderDTO.setPriceAtOrder(priceAtOrder);
	            orderDTO.setOrderItemIds(itemIds);
	            orderDTO.setQuantities(quantities);

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
	                request.setAttribute("redirectURL", "createOrder.jsp");
	                dispatcher.forward(request, response);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
	            request.setAttribute("status", "error");
	            request.setAttribute("message", "Server error: " + e.getMessage());
	            request.setAttribute("redirectURL", "createOrder.jsp");
	            dispatcher.forward(request, response);
	        }
	    }
	    
	    private void findAllOrdersByStoreId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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



}
