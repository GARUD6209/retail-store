package com.amstech.retail.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.amstech.retail.dao.OrderDAO;
import com.amstech.retail.dto.OrderDTO;
import com.amstech.retail.service.OrderService;
import com.amstech.retail.util.DBUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@WebServlet("/OrderPDFServlet")
public class OrderPDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DBUtil dbUtil;
    private OrderDAO orderDAO;
    private OrderService orderService;

    public OrderPDFServlet() {
        this.dbUtil = new DBUtil();
        this.orderDAO = new OrderDAO(dbUtil);
        this.orderService = new OrderService(orderDAO);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderNumber = request.getParameter("orderNumber");

        try {
            OrderDTO order = orderService.findOrderDetailsByOrderNumber(orderNumber);

            if (order != null) {
                generateOrderPDF(order, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
            }
        } catch (Exception e) {
            throw new ServletException("Error generating PDF", e);
        }
    }

    private void generateOrderPDF(OrderDTO orderDTO, HttpServletResponse response) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Order Invoice", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        // Add order details
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        PdfPTable orderDetailsTable = new PdfPTable(2);
        orderDetailsTable.setWidthPercentage(100);
        orderDetailsTable.setSpacingBefore(10f);
        orderDetailsTable.setSpacingAfter(10f);
        orderDetailsTable.setWidths(new float[]{1f, 2f});

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

        addTableRow(orderDetailsTable, "Order Number:", orderDTO.getOrderNumber(), labelFont, valueFont);
        addTableRow(orderDetailsTable, "Date and Time", sdf.format(new Date()), labelFont, valueFont);
        addTableRow(orderDetailsTable, "Customer Name:", orderDTO.getCustomerName(), labelFont, valueFont);
        addTableRow(orderDetailsTable, "Customer Number:", orderDTO.getCustomerNumber(), labelFont, valueFont);
        addTableRow(orderDetailsTable, "Payment Status:", orderDTO.getStatus(), labelFont, valueFont);
        addTableRow(orderDetailsTable, "Total Amount:", String.format("\u20B9%.2f", orderDTO.getTotalAmount()), labelFont, valueFont);

        document.add(orderDetailsTable);

        // Add items header
        Paragraph itemsHeader = new Paragraph("Order Items", labelFont);
        document.add(itemsHeader);
        document.add(Chunk.NEWLINE);

        // Add items table
        PdfPTable itemsTable = new PdfPTable(3);
        itemsTable.setWidthPercentage(100);
        itemsTable.setSpacingBefore(10f);
        itemsTable.setSpacingAfter(10f);
        itemsTable.setWidths(new float[]{3f, 1f, 1f});

        addTableHeader(itemsTable, "Item Name", labelFont);
        addTableHeader(itemsTable, "Quantity", labelFont);
        addTableHeader(itemsTable, "Cost", labelFont);

        for (int i = 0; i < orderDTO.getOrderItemIds().length; i++) {
            addTableRow(itemsTable, orderDTO.getOrderItemNames()[i], orderDTO.getQuantities()[i], orderDTO.getPriceAtOrder()[i], valueFont);
        }

        document.add(itemsTable);

        document.close();

        // Set response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"Order_" + orderDTO.getOrderNumber() + ".pdf\"");
        response.setContentLength(baos.size());

        // Write PDF to response
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();
    }

    private void addTableHeader(PdfPTable table, String header, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(header, font));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void addTableRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        labelCell.setPadding(5);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        valueCell.setPadding(5);
        table.addCell(valueCell);
    }

    private void addTableRow(PdfPTable table, String itemName, String quantity, String price, Font font) {
        PdfPCell itemNameCell = new PdfPCell(new Phrase(itemName, font));
        itemNameCell.setPadding(5);
        table.addCell(itemNameCell);

        PdfPCell quantityCell = new PdfPCell(new Phrase(quantity, font));
        quantityCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        quantityCell.setPadding(5);
        table.addCell(quantityCell);

        PdfPCell priceCell = new PdfPCell(new Phrase(price, font));
        priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        priceCell.setPadding(5);
        table.addCell(priceCell);
    }
}
