package com.terri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terri.inter.IUserOperation;
import com.terri.model.User;
import com.terri.service.IUserService;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserOperation userMapper;

	/* (非 Javadoc) 
	* <p>Title: findAllUsers</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.terri.service.IUserService#findAllUsers() 
	*/
	@Override
	public List<User> findAllUsers() {
		return userMapper.selectUsers("%");
	}

	/* (非 Javadoc) 
	* <p>Title: isUserExist</p> 
	* <p>Description: </p> 
	* @param user
	* @return 
	* @see com.terri.service.IUserService#isUserExist(com.terri.model.User) 
	*/
	@Override
	public boolean isUserExist(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (非 Javadoc) 
	* <p>Title: saveUser</p> 
	* <p>Description: </p> 
	* @param user 
	* @see com.terri.service.IUserService#saveUser(com.terri.model.User) 
	*/
	@Override
	public void saveUser(User user) {
		userMapper.addUser(user);
	}

	/* (非 Javadoc) 
	* <p>Title: findById</p> 
	* <p>Description: </p> 
	* @param id
	* @return 
	* @see com.terri.service.IUserService#findById(long) 
	*/
	@Override
	public User findById(int id) {
		return userMapper.selectUserByID(id);
	}

	/* (非 Javadoc) 
	* <p>Title: updateUser</p> 
	* <p>Description: </p> 
	* @param currentUser 
	* @see com.terri.service.IUserService#updateUser(com.terri.model.User) 
	*/
	@Override
	public void updateUser(User currentUser) {
	   userMapper.updateUser(currentUser);
		
	}

	/* (非 Javadoc) 
	* <p>Title: deleteAllUsers</p> 
	* <p>Description: </p>  
	* @see com.terri.service.IUserService#deleteAllUsers() 
	*/
	@Override
	public void deleteAllUsers() {
		
	}

	@Override
	public void deleteUserById(int id) {
		userMapper.deleteUser(id);
		
	}

}
