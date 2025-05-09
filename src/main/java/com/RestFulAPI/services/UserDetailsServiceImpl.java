package com.RestFulAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.RestFulAPI.entity.User;
import com.RestFulAPI.repo.UserRepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByUserName(username);
		if (user != null) {

			UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
					.username(user.getUserName()).password(user.getPassword())
					.roles(user.getRoles().toArray(new String[0])).build();
			return userDetails;
		}

		return null;
	}

}
