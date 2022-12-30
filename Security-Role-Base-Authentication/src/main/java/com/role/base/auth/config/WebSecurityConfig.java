package com.role.base.auth.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.role.base.auth.service.UserDetailsServiceImpl;



@Configuration 
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
       	return new BCryptPasswordEncoder(); 
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
	//	   .antMatchers("/").permitAll() to permit view access to all but need login for action as mentioned below
		    .antMatchers("/delete/**").hasAuthority("ADMIN")//for role based login
		    .antMatchers("/edit/**").hasAnyAuthority("ADMIN","EDITOR")
		   // .antMatchers("/login").permitAll()
		    .anyRequest().authenticated()
		    .and()
		    .formLogin()
		      .permitAll()
		      .loginPage("/login")
		      .failureHandler(customeLoginFailer)
		      .successHandler(customLoginSuccessHandeler)
		      .usernameParameter("email")
		     // .defaultSuccessUrl("/login_success")
		      //.failureUrl("/")
		     // .successForwardUrl("/login_success_handeler")
		      //.failureForwardUrl(null)
//		      .successHandler(new AuthenticationSuccessHandler() {
//				
//				@Override
//				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//						Authentication authentication) throws IOException, ServletException {
//					response.sendRedirect("/");
//					
//				}
//			})
		    .and()
		    .logout().permitAll()
		    .and()
		    .rememberMe().tokenRepository(persistentTokenRepository())
		    .and()
		    .exceptionHandling().accessDeniedPage("/403");
		    
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}
	
	@Autowired
	private CustomeLoginFailer customeLoginFailer;
	
	@Autowired
	private CustomLoginSuccessHandeler customLoginSuccessHandeler;
	
}
