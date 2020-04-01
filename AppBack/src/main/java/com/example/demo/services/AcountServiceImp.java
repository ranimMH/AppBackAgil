package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.AppRoleRepository;
import com.example.demo.dao.AppUserRepository;
import com.example.demo.entities.AppRole;
import com.example.demo.entities.AppUser;

@Service
@Transactional
public class AcountServiceImp implements AcountService {
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AppRoleRepository appRoleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public AcountServiceImp() {
		super();
	}

	public AcountServiceImp(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.appUserRepository = appUserRepository;
		this.appRoleRepository = appRoleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public AppUser saveUser(String username, String password, String confirmedPassword) {

		AppUser user = appUserRepository.findByUserName(username);
		if (user != null)
			throw new RuntimeException("user already exist");
		if (!password.equals(confirmedPassword))
			throw new RuntimeException("please confirm your pass");
		AppUser appUser = new AppUser();
		appUser.setUserName(username);
		System.out.println("bCryptPasswordEncoder********************" + bCryptPasswordEncoder.encode(password));
		appUser.setPassword(bCryptPasswordEncoder.encode(password));
		appUser.setActived(true);
		appUserRepository.save(appUser);
		addRoleToUser(username, "USER");
		return appUser;
	}

	@Override
	public AppRole save(AppRole role) {
		System.out.println("role+++++++++" + role.getId() + "name" + role.getRoleName());
		return appRoleRepository.save(role);
	}

	@Override
	public AppUser loadUserByUserName(String username) {
		// TODO Auto-generated method stub
		return appUserRepository.findByUserName(username);
	}

	@Override
	public void addRoleToUser(String username, String rolename) {
		AppUser appUser = appUserRepository.findByUserName(username);
		AppRole appRole = appRoleRepository.findByRoleName(rolename);
		appUser.getRoles().add(appRole);

	}

}
