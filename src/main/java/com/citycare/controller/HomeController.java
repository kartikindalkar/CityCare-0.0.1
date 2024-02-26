package com.citycare.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.citycare.entity.User;
import com.citycare.repository.ImageUploadRepository;
import com.citycare.repository.UserRepo;
import com.citycare.services.UserService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class HomeController {
	
	@Autowired
	private ImageUploadRepository imageUploadRepository;
	
	@Autowired
	private BCryptPasswordEncoder PasswordEncoder;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private UserRepo userRepo;
	
	
	@GetMapping("/")
	public String index(Principal p,Model m){
		if (p != null) {
            String email = p.getName();
            User user = userRepo.findByEmail(email);
            m.addAttribute("user", user);
        }
        return "index";
	}
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup(){
		return "signup";
	}
	
	@GetMapping("/post-ads")
	public String posts(){
		return "post-ads";
	}
	
	@GetMapping("/ads-details")
	public String postsDetails(){
		return "ads-details";
	}
	
	
	
	@GetMapping("/category")
	public String category(){
		return "category";
	}
	
	@GetMapping("/account-home")
	public String userAccount(Principal p,Model m){
		String email = p.getName();
		User user = userRepo.findByEmail(email);
		m.addAttribute("user",user);
		return "account-home";
	}
	
	@GetMapping("/account-myads")
	public String userAds(){
		return "account-myads";
	}
	@GetMapping("/account-message-inbox")
	public String userMessageInbox(){
		return "account-message-inbox";
	}
	
	
	@PostMapping("/saveUser")
	public String saveUser(@RequestParam("profileImg") MultipartFile profileImg,
			@RequestParam("idProofImg") MultipartFile idProofImg,@ModelAttribute User user) {
		
		String password = PasswordEncoder.encode(user.getPassword());
		//System.out.println(password);
		user.setPassword(password);
		
		// Generate a UUID for uniqueness
        String uuid = UUID.randomUUID().toString();

        // Assuming user.getId() returns the user ID
        String userId = String.valueOf(user.getId());

        // Generate unique filenames for profile image and ID proof image
        String profileImgFilename = userId + "_" + uuid;
        String idProofImgFilename = userId + "_" + uuid;
     
        
		            
     	user.setProfileImg(profileImgFilename+profileImg.getOriginalFilename());
		user.setIdProofImg(idProofImgFilename+idProofImg.getOriginalFilename());

		User uploadImg = imageUploadRepository.save(user);
		
		if(uploadImg != null) {
			
			saveImages(user);
			
		}
		
		return "redirect:/login";
	}

	private void saveImages(User user) {
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
