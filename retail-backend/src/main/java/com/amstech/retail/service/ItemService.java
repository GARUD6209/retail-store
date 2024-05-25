package com.amstech.retail.service;

import com.amstech.retail.dao.ItemDAO;
import com.amstech.retail.dao.UserDAO;
import com.amstech.retail.dto.ItemDTO;
import com.amstech.retail.dto.UserDTO;

public class ItemService {

	private ItemDAO itemDAO;
	
	public ItemService(ItemDAO itemDAO) {
 		this.itemDAO=itemDAO;
	}
	
	public int save(ItemDTO itemDTO) throws Exception {
		// pre process

		int count = itemDAO.save(itemDTO);

		// post process
		return count;
	}
	public int update(ItemDTO itemDTO) throws Exception {

		return itemDAO.update(itemDTO);
	}

	
}
