package com.citycare.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.citycare.entity.User;
import com.citycare.repository.UserRepo;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.io.ClassPathResource;

import com.citycare.entity.User;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	private Timestamp UpdDate;
	
	@Override
	public User saveUser(User user) {
		User newUser = userRepo.save(user);
		return newUser;
	}

	@Override
	public void removeSessionMessage() {
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("msg");
	}
	
	
	@Override
	public void updateUser(Long id, User updatedUser) {
		
	
//		User existingUser = userRepo.findById(id);
		User existingUser  = userRepo.findById(id);
		if(existingUser!=null) {			
			existingUser.setFirstName(updatedUser.getFirstName());
			existingUser.setLastName(updatedUser.getLastName());
			existingUser.setAddress(updatedUser.getAddress());
			existingUser.setContact(updatedUser.getContact());		
			existingUser.setCountry(updatedUser.getCountry());
			existingUser.setCity(updatedUser.getCity());
			existingUser.setPincode(updatedUser.getPincode());
			existingUser.setState(updatedUser.getState());
			existingUser.setRole(updatedUser.getRole());
			existingUser.setUpdDate(updatedUser.getUpdDate());
			 
			userRepo.save(existingUser);
			
			System.out.println("Employee Details against Id " + id +" updated");
			
		}else {
			System.out.println("Employee Details does not exist for " + id);
		}	
//        existingUser.setFirstName(updatedUser.getFirstName());
//        existingUser.setLastName(updatedUser.getLastName());
//        existingUser.setEmail(updatedUser.getEmail());
//        existingUser.setPassword(updatedUser.getPassword());
//        System.out.println(existingUser.getFirstName());
//         userRepo.save(existingUser);
	}
	
	public String UpdDate() {
    	LocalDateTime currentDateTime = LocalDateTime.now();

        // Format the timestamp using a specific pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = currentDateTime.format(formatter);
        return formattedTimestamp;
    }
	

	public void saveImages(User user) {
		try {
		File saveFile = new ClassPathResource("static/UserProfile").getFile();
		//System.out.println("Absolute Path: " + saveFile.getAbsolutePath());

        if (!saveFile.exists()) {
            boolean created = saveFile.mkdirs();
            if (!created) {
                System.err.println("Failed to create directory: " + saveFile.getAbsolutePath());
                return;
            }
        }

        
        
        //Use the correct file names from user entity
        Path profileImgPath = Paths.get(saveFile.getAbsolutePath() + File.separator + user.getProfileImgPath());
        Path idProofImgPath = Paths.get(saveFile.getAbsolutePath() + File.separator + user.getIdProofImgPath());

        Files.copy(user.getProfileImg().getInputStream(), profileImgPath, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(user.getIdProofImg().getInputStream(), idProofImgPath, StandardCopyOption.REPLACE_EXISTING);

    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Failed to save images: " + e.getMessage());
    }
	}

	
	
	
}
