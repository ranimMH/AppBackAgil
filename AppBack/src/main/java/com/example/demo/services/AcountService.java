package com.example.demo.services;

import com.example.demo.entities.AppRole;
import com.example.demo.entities.AppUser;

public interface AcountService {
	public AppUser saveUser(String username, String password, String confirmedPassword);

	public AppRole save(AppRole role);

	public AppUser loadUserByUserName(String username);

	public void addRoleToUser(String username, String rolename);
}
