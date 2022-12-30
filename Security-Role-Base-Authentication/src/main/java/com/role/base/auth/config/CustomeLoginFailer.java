package com.role.base.auth.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.role.base.auth.entity.User;
import com.role.base.auth.service.UserDetailsServiceImpl;

@Component
public class CustomeLoginFailer extends SimpleUrlAuthenticationFailureHandler{

	@Autowired
	private UserDetailsServiceImpl detailsServiceImpl;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String userName=request.getParameter("email");
		User user= detailsServiceImpl.getUserByUserName(userName);
		if(user!=null) {
			if(user.isEnabled() && user.isAccountNonLock()) {
				if(user.getFailedAttemp() < UserDetailsServiceImpl.MAX_FAILED_ATTEMPS-1) {
					detailsServiceImpl.increaseFailedAttemp(user);
				}else {
				detailsServiceImpl.lock(user);
				exception= new LockedException("your accoct thas been locked due to 3 unsuccessful attempt");
				}
			}else if(!user.isAccountNonLock()) {
				if(detailsServiceImpl.unlock(user)) {
					exception= new LockedException("your account has been unlocked");
				}
			}
		}
		super.setDefaultFailureUrl("/login?error");
		super.onAuthenticationFailure(request, response, exception);
	}
}
