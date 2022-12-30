package com.role.base.auth.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.role.base.auth.entity.MyUserDetails;
import com.role.base.auth.entity.User;
import com.role.base.auth.service.UserDetailsServiceImpl;

@Component
public class CustomLoginSuccessHandeler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private UserDetailsServiceImpl detailsServiceImpl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		MyUserDetails detailsServic = (MyUserDetails) authentication.getPrincipal();
		
		User user= detailsServic.getUser();
		if(user.getFailedAttemp() > 0) {
			detailsServiceImpl.resetFailedAttemp(user.getUserName());
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
