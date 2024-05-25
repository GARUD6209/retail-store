package com.amstech.retail.service;

import com.amstech.retail.dao.QueryDAO;
import com.amstech.retail.dto.QueryDTO;

public class QueryService {
	private QueryDAO queryDAO;

	 public QueryService(QueryDAO queryDAO) {
		 this.queryDAO=queryDAO;
		 
		 
	 }
	 
	 public int save(QueryDTO queryDTO) throws Exception {
		return queryDAO.save(queryDTO);
	 }
	 
}
