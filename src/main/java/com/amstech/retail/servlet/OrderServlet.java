package com.amstech.retail.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.amstech.retail.dao.OrderDAO;
import com.amstech.retail.dto.OrderDTO;
import com.amstech.retail.service.OrderService;
import com.amstech.retail.util.DBUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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
	               
	           }else if ("searchOrderByOrderNumber".equalsIgnoreCase(task)) {
	               System.out.println("Calling searchOrderByOrderNumber");
	               searchOrderByOrderNumber(request, response);
	           }  else {
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
	                
//	                generateOrderPDF(orderDTO, response);
	                
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
	    
//	    private void generateOrderPDF(OrderDTO orderDTO, HttpServletResponse response) throws IOException, DocumentException {
//	    	 // Create a document
//	        Document document = new Document();
//	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	        PdfWriter writer = PdfWriter.getInstance(document, baos);
//	        document.open();
//
//	        // Add title
//	        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
//	        Paragraph title = new Paragraph("Order Invoice", titleFont);
//	        title.setAlignment(Element.ALIGN_CENTER);
//	        document.add(title);
//	        document.add(Chunk.NEWLINE);
//
//	        // Add order details
//	        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
//	        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
//
//	        PdfPTable orderDetailsTable = new PdfPTable(2);
//	        orderDetailsTable.setWidthPercentage(100);
//	        orderDetailsTable.setSpacingBefore(10f);
//	        orderDetailsTable.setSpacingAfter(10f);
//	        orderDetailsTable.setWidths(new float[]{1f, 2f});
//	        
//	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
//
//	        addTableRow(orderDetailsTable, "Order Number:", orderDTO.getOrderNumber(), labelFont, valueFont);
//	        addTableRow(orderDetailsTable, "Date and Time", sdf.format(new Date()), labelFont, valueFont);
//	        addTableRow(orderDetailsTable, "Customer Name:", orderDTO.getCustomerName(), labelFont, valueFont);
//	        addTableRow(orderDetailsTable, "Customer Number:", orderDTO.getCustomerNumber(), labelFont, valueFont);
//	        addTableRow(orderDetailsTable, "Payment Status:", orderDTO.getStatus(), labelFont, valueFont);
//	        addTableRow(orderDetailsTable, "Total Amount:", String.format("\u20B9%.2f", orderDTO.getTotalAmount()), labelFont, valueFont);
//	        
//
//
//	        document.add(orderDetailsTable);
//
//	        // Add items header
//	        Paragraph itemsHeader = new Paragraph("Order Items", labelFont);
//	        document.add(itemsHeader);
//	        document.add(Chunk.NEWLINE);
//
//	        // Add items table
//	        PdfPTable itemsTable = new PdfPTable(3);
//	        itemsTable.setWidthPercentage(100);
//	        itemsTable.setSpacingBefore(10f);
//	        itemsTable.setSpacingAfter(10f);
//	        itemsTable.setWidths(new float[]{3f, 1f, 1f});
//
//	        addTableHeader(itemsTable, "Item Name", labelFont);
//	        addTableHeader(itemsTable, "Quantity", labelFont);
//	        addTableHeader(itemsTable, "Price at Order", labelFont);
//
//	        for (int i = 0; i < orderDTO.getOrderItemIds().length; i++) {
//	            addTableRow(itemsTable, orderDTO.getOrderItemNames()[i], orderDTO.getQuantities()[i], orderDTO.getPriceAtOrder()[i], valueFont);
//	        }
//
//	        document.add(itemsTable);
//
//	        document.close();
//
//	        // Set response headers
//	        response.setContentType("application/pdf");
//	        response.setHeader("Content-Disposition", "attachment; filename=/"+ orderDTO.getOrderNumber() +"/.pdf");
//	        response.setContentLength(baos.size());
//
//	        // Write PDF to response
//	        OutputStream os = response.getOutputStream();
//	        baos.writeTo(os);
//	        os.flush();
//	        os.close();
//	        
//	    }
//	    private void addTableHeader(PdfPTable table, String header, Font font) {
//	        PdfPCell cell = new PdfPCell(new Phrase(header, font));
//	        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        cell.setPadding(5);
//	        table.addCell(cell);
//	    }
//
//	    private void addTableRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
//	        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
//	        labelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	        labelCell.setPadding(5);
//	        table.addCell(labelCell);
//
//	        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
//	        valueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	        valueCell.setPadding(5);
//	        table.addCell(valueCell);
//	    }
//
//	    private void addTableRow(PdfPTable table, String itemName, String quantity, String price, Font font) {
//	        PdfPCell itemNameCell = new PdfPCell(new Phrase(itemName, font));
//	        itemNameCell.setPadding(5);
//	        table.addCell(itemNameCell);
//
//	        PdfPCell quantityCell = new PdfPCell(new Phrase(quantity, font));
//	        quantityCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        quantityCell.setPadding(5);
//	        table.addCell(quantityCell);
//
//	        PdfPCell priceCell = new PdfPCell(new Phrase(price, font));
//	        priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	        priceCell.setPadding(5);
//	        table.addCell(priceCell);
//	    }
	    
	    private void searchOrderByOrderNumber(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
