package com.role.base.auth.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.role.base.auth.entity.MyUserDetails;
import com.role.base.auth.entity.User;
import com.role.base.auth.repository.UserRepo;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	public static final int MAX_FAILED_ATTEMPS=3;
	public static final int LOCK_TIME_DURATION=1 * 60 *1000;
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
	
	public User getUserByUserName(String userName) {
		return repo.getUserByUserName(userName);
	}

	public void increaseFailedAttemp(User user) {
		int failedAttemps= user.getFailedAttemp()+1;
		repo.updateFailedAttemp(failedAttemps, user.getUserName());
	}

	public void lock(User user) {
		user.setAccountNonLock(false);
		user.setLockTime(new Date());
		repo.save(user);
		
	}
	
	public boolean unlock(User user) {
		long locTimeInMilliSecond=user.getLockTime().getTime();
		long currentTimeMilliSecond=System.currentTimeMillis();
		
		if(locTimeInMilliSecond+LOCK_TIME_DURATION < currentTimeMilliSecond) {
			user.setAccountNonLock(true);
			user.setFailedAttemp(0);
			user.setLockTime(null); 
			repo.save(user);
			return true;
		}
		
		return false;
	}

	public void resetFailedAttemp(String userName) {
		repo.updateFailedAttemp(0, userName);
		
	}
	

}
