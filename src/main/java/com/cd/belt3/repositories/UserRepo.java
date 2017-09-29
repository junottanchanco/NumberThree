package com.cd.belt3.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cd.belt3.models.User;

public interface UserRepo extends CrudRepository<User, Long>{
	User findByEmail(String email);
}