package com.amstech.retail.service;

import java.util.List;

import com.amstech.retail.dao.ItemDAO;

import com.amstech.retail.dto.ItemDTO;


public class ItemService {

	private ItemDAO itemDAO;

	public ItemService(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
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

	public ItemDTO findById(int id) throws Exception {

		return itemDAO.findByid(id);
	}

	public List<ItemDTO> findByStoreInfoId(int storeInfoId) throws Exception {

		return itemDAO.findByStoreInfoId(storeInfoId);
	}
	
	public int deleteById(ItemDTO itemDTO) throws Exception {
		return itemDAO.deleteById(itemDTO);
	}

}
