package com.amstech.retail.service;

import org.mindrot.jbcrypt.BCrypt;

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
	
	public int updatePassword(String email, String otp, String newPassword) throws Exception {
        // Verify OTP before updating password
        if (userDAO.verifyOTP(email, otp)) {
            String hashedPassword = hashPassword(newPassword);
            return userDAO.updatePassword(email, otp, hashedPassword);
        } else {
            throw new Exception("Invalid or expired OTP");
        }
    }

    public void saveOTP(String email, String otp) throws Exception {
        userDAO.saveOTP(email, otp);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

}
