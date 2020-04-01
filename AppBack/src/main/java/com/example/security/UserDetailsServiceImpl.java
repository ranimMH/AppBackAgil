package com.example.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.AppUser;
import com.example.demo.services.AcountService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AcountService acountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = acountService.loadUserByUserName(username);
		System.out.println("appUser"+appUser);
		if (appUser == null)
			throw new UsernameNotFoundException("user not found ");
		Collection<GrantedAuthority> authorities = new ArrayList<>();

		appUser.getRoles().forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
		});
		return new User(appUser.getUserName(), appUser.getPassword(), authorities);
	}

}
