package com.terri.service;

import java.util.List;

import com.terri.model.User;

public interface IUserService {

	public abstract List<User> findAllUsers();

	public abstract boolean isUserExist(User user);

	public abstract void saveUser(User user);

	public abstract User findById(int id);

	public abstract void updateUser(User currentUser);

	public abstract void deleteAllUsers();

	public abstract void deleteUserById(int id);

}