package com.cd.belt3.services;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cd.belt3.models.User;
import com.cd.belt3.repositories.RoleRepo;
import com.cd.belt3.repositories.UserRepo;

@Service
public class UserService {
	private UserRepo userRepo;
	private RoleRepo roleRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserService(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	//1 Saves a client with only the user role.
	 public void saveWithUserRole(User user) {
	        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	        user.setRoles(roleRepo.findByName("ROLE_USER"));
	        userRepo.save(user);
	    }
	//2 Saves a client with only the admin role.
	public void saveUserWithAdminRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepo.findByName("ROLE_ADMIN"));
		userRepo.save(user);
	}
	
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	public ArrayList<User> findAll(){
		return (ArrayList<User>) userRepo.findAll();
	}
	
	public void updateUserDate(Long id, User user) {
		user.setUpdatedAt(new Date());
		userRepo.save(user);	
    }
	
	public void updateUser(User user) {
		userRepo.save(user);
	}
	
	public void updateUserWithAdminRole(User user) {
		user.setRoles(roleRepo.findByName("ROLE_ADMIN"));
		userRepo.save(user);
	}
	
	public void updateUserWithUserRole(User user) {
		user.setRoles(roleRepo.findByName("ROLE_USER"));
		userRepo.save(user);
	}
	
	public void deleteFriendById(Long friend_id) {
		userRepo.delete(friend_id);
	}
	
	public User findUserById(Long id) {
		return userRepo.findOne(id);
	}
}
