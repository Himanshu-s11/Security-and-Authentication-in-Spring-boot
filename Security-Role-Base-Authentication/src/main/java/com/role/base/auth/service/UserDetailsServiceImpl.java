package com.role.base.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.role.base.auth.entity.MyUserDetails;
import com.role.base.auth.entity.User;
import com.role.base.auth.repository.UserRepo;


public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= repo.getUserByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new MyUserDetails(user);
	}

}
