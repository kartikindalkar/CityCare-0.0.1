package com.citycare.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.citycare.entity.User;
import com.citycare.repository.UserRepo;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		
		User user = userRepo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("User name not found");
		}else {
			return new UserConfig(user);
		}
	}

}
