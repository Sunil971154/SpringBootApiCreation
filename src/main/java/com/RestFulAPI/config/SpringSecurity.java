package com.RestFulAPI.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import JwtFilter.JwtFilter;

@EnableWebSecurity

public class SpringSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	@Lazy
    private JwtFilter jwtFilter;

	@Override 
	protected void configure(HttpSecurity http) throws Exception {

		http
				// 🔒 CSRF (Cross-Site Request Forgery) को disable किया गया क्योंकि हम REST API
				// बना रहे हैं
				.csrf().disable()

				// 🌐 URL access control: किस URL को secure करना है और किसे नहीं
				.authorizeRequests()
				// 🔐 "/journal/**" वाला हर URL authenticated (login किया हुआ) user ही access कर
				// सकता है
				.antMatchers("/journal/**", "/user/**").authenticated().antMatchers("/admin/**").hasRole("ADMIN")

				// 🌍 बाकी सभी URL (जैसे "/", "/home", etc.) को बिना login के access किया जा
				// सकता है
				.anyRequest().permitAll();
				http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();
				http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}
	
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
		
	}
	

	@Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }
	
	
	
	

}
