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

import com.amstech.retail.dao.ItemDAO;
import com.amstech.retail.dto.ItemDTO;
import com.amstech.retail.service.ItemService;

import com.amstech.retail.util.DBUtil;

@WebServlet("/items")
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

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String task = request.getParameter("task");
		System.out.println(task);

		if (task.equalsIgnoreCase("findItemById")) {
			findById(request, response);
		} else if (task.equalsIgnoreCase("findAllItems") || task.equalsIgnoreCase("findAllItemsToCreateOrder")) {
			findByStoreInfoId(request, response);
		} else if (task.equalsIgnoreCase("deleteItemById")) {
			deleteById(request, response);
		} else {
			System.out.println("method not found");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String task = request.getParameter("task");
		System.out.println(task);

		if (task.equalsIgnoreCase("addItem")) {
			save(request, response);

		} else if (task.equalsIgnoreCase("updateById")) {
			update(request, response);
		} else if (task.equalsIgnoreCase("deleteById")) {
			deleteById(request, response);
		}  else {
			System.out.println("method not found");
		}
		
	}

	

	public void destroy() {

	}

	public void save(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		try {
			int storeInfoId = Integer.parseInt(request.getParameter("storeInfoId"));
			String name = request.getParameter("name");
			double current_price = Double.parseDouble(request.getParameter("current-price"));

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
			} else {

				System.out.println("Failed to create item data..");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "Failed to create item data..");
				request.setAttribute("redirectURL", "item.jsp");

				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to create item data..");
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Failed to create item data.." + e.getMessage());
			request.setAttribute("redirectURL", "item.jsp");

			dispatcher.forward(request, response);

		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {

			ItemDTO itemDTO = new ItemDTO();

			itemDTO.setName(request.getParameter("name"));
			itemDTO.setDescription(request.getParameter("description"));
			itemDTO.setCurrent_price(Double.parseDouble(request.getParameter("current-price")));
			itemDTO.setId(Integer.parseInt(request.getParameter("itemId")));
			int count = itemService.update(itemDTO);

			if (count > 0) {
				System.out.println("item updated successfully");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "success");
				request.setAttribute("message", "item updated successfully");
				request.setAttribute("redirectURL", "item.jsp");

				dispatcher.forward(request, response);

			} else {
				System.out.println("Failed to update item data..");
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
			request.setAttribute("message", "Server Failed to update item due to" + e.getMessage());
			request.setAttribute("redirectURL", "item.jsp");

			dispatcher.forward(request, response);
		}
	}

	public void findById(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			ItemDTO itemDTO = itemService.findById(Integer.parseInt(request.getParameter("itemId")));

			if (itemDTO != null) {
				System.out.println("itemDTO OBJECT IS NOT EMPTY");

				RequestDispatcher dispatcher = request.getRequestDispatcher("item.jsp");

				request.setAttribute("editItemDTO", itemDTO);

				dispatcher.forward(request, response);

			} else {
				System.out.println("Failed to find item data..");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "Failed to find item data..");
				request.setAttribute("redirectURL", "item.jsp");

				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Server Failed to find item due to" + e.getMessage());
			request.setAttribute("redirectURL", "item.jsp");

			dispatcher.forward(request, response);
		}
	}

	public void findByStoreInfoId(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			String task = request.getParameter("task")  ;
			
			List<ItemDTO> itemDTOList = itemService.findByStoreInfoId(Integer.parseInt(request.getParameter("id")));

			if (!itemDTOList.isEmpty() && task.equalsIgnoreCase("findAllItems")) {
				System.out.println("itemDTO OBJECT IS NOT EMPTY");

				RequestDispatcher dispatcher = request.getRequestDispatcher("item.jsp");

				request.setAttribute("itemDTOList", itemDTOList);

				dispatcher.forward(request, response);

			} else if (!itemDTOList.isEmpty() && task.equalsIgnoreCase("findAllItemsToCreateOrder")) {
				System.out.println("itemDTOforCreatingodrer OBJECT IS NOT EMPTY");

				RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");

				request.setAttribute("itemDTOListForOrder", itemDTOList);
//				request.setAttribute("redirect","home.jsp");
				dispatcher.forward(request, response);

			}else {
				System.out.println("Failed to find itemList data..");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "Failed to find itemList data..");
				request.setAttribute("redirectURL", "item.jsp");

				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Server Failed to find itemList due to" + e.getMessage());
			request.setAttribute("redirectURL", "item.jsp");

			dispatcher.forward(request, response);
		}
	}
	
	public void deleteById(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		try {
			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setId(Integer.parseInt(request.getParameter("itemId")));
			int count = itemService.deleteById(itemDTO);
			if(count > 0) {
				System.out.println("Item deleted Successfully");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "success");
				request.setAttribute("message", "item deleted successfully" );
				request.setAttribute("redirectURL", "item.jsp");
				dispatcher.forward(request, response);
			}else {
				System.out.println("Failed to find itemList data..");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "Failed to delete item data..");
				request.setAttribute("redirectURL", "item.jsp");

				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Server Failed to delete item due to" + e.getMessage());
			request.setAttribute("redirectURL", "item.jsp");

			dispatcher.forward(request, response);
		}
	}

}