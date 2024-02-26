package com.citycare.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.citycare.entity.User;


public interface UserService {

	
	public User saveUser(User user);
	
	public void removeSessionMessage();
}
