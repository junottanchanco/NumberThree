package com.cd.belt3.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cd.belt3.models.Role;

public interface RoleRepo extends CrudRepository<Role, Long>{
	List<Role> findAll();
	List<Role> findByName(String name);
}