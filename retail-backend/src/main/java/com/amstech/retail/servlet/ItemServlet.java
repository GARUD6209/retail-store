package com.amstech.retail.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.amstech.retail.dao.ItemDAO;
import com.amstech.retail.dto.ItemDTO;
import com.amstech.retail.service.ItemService;

import com.amstech.retail.util.DBUtil;


@WebServlet("/item")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ItemDAO itemDAO;
	private DBUtil dbUtil;
	private ItemService itemService;

	public ItemServlet() {

		this.dbUtil = new DBUtil();
		this.itemDAO = new ItemDAO(dbUtil);
		this.itemService = new ItemService(itemDAO);
	}

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void save(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		try {
			int storeInfoId = Integer.parseInt(request.getParameter("storeInfoID"));
			String name = request.getParameter("name");
			double current_price = Integer.parseInt(request.getParameter("current-price"));

			String description = request.getParameter("description");
			
			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setStoreInfoId(storeInfoId);
			itemDTO.setName(name);
			itemDTO.setCurrent_price(current_price);
			itemDTO.setDescription(description);
			
			int count = itemService.save(itemDTO);
			if (count > 0) {
				System.out.println("item created successfully");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "success");
				request.setAttribute("message", "item created successfully");
				request.setAttribute("redirectURL", "item.jsp");

				dispatcher.forward(request, response);
			}
			else {
			
				System.out.println("Failed to create item data..");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "Failed to create create data..");
				request.setAttribute("redirectURL", "item.jsp");

				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to create item data..");
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Failed to create item data.." +e.getMessage());
			request.setAttribute("redirectURL", "item.jsp");

			dispatcher.forward(request, response);

		}
	}
	

	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {

			ItemDTO itemDTO = new ItemDTO();

			itemDTO.setName(request.getParameter("name"));
		    itemDTO.setDescription(request.getParameter("description"));
		   
		    itemDTO.setCurrent_price(Integer.parseInt(request.getParameter("current-price")));
			int count = itemService.save(itemDTO);

			if (count > 0) {
				System.out.println("item updated successfully");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "success");
				request.setAttribute("message", "item updated successfully");
				request.setAttribute("redirectURL", "item.jsp");

				dispatcher.forward(request, response);

			} else {
				System.out.println("Failed to create item data..");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "Failed to update item data..");
				request.setAttribute("redirectURL", "item.jsp");

				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Server Failed " + e.getMessage());
			request.setAttribute("redirectURL", "item.jsp");

			dispatcher.forward(request, response);
		}
	}

}