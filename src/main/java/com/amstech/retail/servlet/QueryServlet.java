package com.amstech.retail.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.amstech.retail.dao.QueryDAO;
import com.amstech.retail.dto.QueryDTO;

import com.amstech.retail.service.QueryService;
import com.amstech.retail.util.DBUtil;

@WebServlet("/query")

public class QueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBUtil dbUtil;
	private QueryDAO queryDAO;

	private QueryService queryService;
       
 
    public QueryServlet() {
       super();
    	this.dbUtil= new DBUtil();
    	this.queryDAO=new QueryDAO(dbUtil);    			
    	this.queryService=new QueryService(queryDAO);
       
    }


	public void init(ServletConfig config) throws ServletException {
		
	}


 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
	 
	 
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String task = request.getParameter("task");
		System.out.println(task);
		if (task.equalsIgnoreCase("task")) {
			save(request, response);
		
	}
		else {
			System.out.println("Method not found ");
		}
	}
	
	public void destroy() {
		
	}
	
	public void save(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		try {
			
		
			
				QueryDTO queryDTO = new QueryDTO();
				queryDTO.setStoreInfoID(Integer.parseInt(request.getParameter("storeInfoId")));
				queryDTO.setName(request.getParameter("name"));
				queryDTO.setMobileNumber(request.getParameter("mobile-number"));
				queryDTO.setDescription(request.getParameter("description"));			
			
				int count = queryService.save(queryDTO);
				if (count > 0) {
					System.out.println("query generated successfully");
					RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
					request.setAttribute("status", "success");
					request.setAttribute("message", "query generated successfully");
					request.setAttribute("redirectURL", "home.jsp");

					dispatcher.forward(request, response);

		
			}else {
				System.out.println("Failed to generate query data..");
				RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
				request.setAttribute("status", "error");
				request.setAttribute("message", "Failed to generate query data..");
				request.setAttribute("redirectURL", "query.jsp");

				dispatcher.forward(request, response);
			}

		

		

		} catch (Exception e) {
			e.printStackTrace();
//			response.sendRedirect("signup.jsp");	

			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			request.setAttribute("status", "error");
			request.setAttribute("message", "Server Failed " + e.getMessage());
			request.setAttribute("redirectURL", "query.jsp");

			dispatcher.forward(request, response);
		}

	}
	
	
	

}
