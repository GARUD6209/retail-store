package com.amstech.retail.service;

import com.amstech.retail.dao.UserDAO;
import com.amstech.retail.dto.UserDTO;

public class UserService {
	private UserDAO userDAO;

	public UserService(UserDAO userDAO) {

		this.userDAO = userDAO;
	}

	public int save(UserDTO userDTO) throws Exception {
		// pre process

		int count = userDAO.save(userDTO);

		// post process
		return count;
	}

	public int update(UserDTO userDTO) throws Exception {

		return userDAO.update(userDTO);
	}
	public UserDTO findById(int id) throws Exception {

		return userDAO.findById(id);

	}

	public UserDTO findByUsernameAndPassword(String username) throws Exception {

		return userDAO.findByUsernameAndPassword(username);

	}

}
