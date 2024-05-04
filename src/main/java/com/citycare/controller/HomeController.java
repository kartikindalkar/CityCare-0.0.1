package com.citycare.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.citycare.entity.Products;
import com.citycare.entity.User;
import com.citycare.repository.ImageUploadRepository;
import com.citycare.repository.ProductsRepo;
import com.citycare.repository.UserRepo;
import com.citycare.services.ProductService;
import com.citycare.services.UserService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
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
	private ProductService productService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ProductsRepo productRepo;
	
	private static final String UPLOAD_DIRECTORY = "src/main/resources/static/userImages";
	
	
	@GetMapping("/index")	
	public String index(@AuthenticationPrincipal UserDetails userDetails,Model m){
		if (userDetails != null) {
            String email = userDetails.getUsername();
            User user = userRepo.findByEmail(email);
            m.addAttribute("user", user);
        }
		
		if (userDetails == null) {
            
           return "index";
        }
		 return "index";
	}
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	@GetMapping("/logout")
	public String logout(){
		return "index";
	}
	
	@GetMapping("/signup")
	public String signup(){
		return "signup";
	}
	
	@GetMapping("/post-ads")
	public String posts(Principal p,Model m){		
		String email = p.getName();
		User user = userRepo.findByEmail(email);
		
		m.addAttribute("user",user);
		return "post-ads";
	}
	
	
	
	
	
	@GetMapping("/category")
	public String category(Principal p,Model m){
		
		String email = p.getName();
		User user = userRepo.findByEmail(email);
		
		m.addAttribute("user",user);
		
//		List<Products> allproducts = productRepo.findAll();
//		//Collections.shuffle(allproducts, new Random());
//		
//		m.addAttribute("productslist", allproducts);

		
//		return "category";
		
		return findPaginated(0, m);
	}
	
	@GetMapping("/page/{pageno}")
	public String findPaginated(@PathVariable int pageno,Model m) {
		
		Page<Products> productslist =   productService.getProductsByPaginate(pageno, 5);
		m.addAttribute("productslist",productslist);
		m.addAttribute("currentPage",pageno);
		m.addAttribute("totalPages",productslist.getTotalPages());
		m.addAttribute("totalItems",productslist.getTotalElements());
		
		return "category";
	}
	
	@GetMapping("/account-home")
	public String userAccount(Principal p,Model m){
		String email = p.getName();
		User user = userRepo.findByEmail(email);
		m.addAttribute("user",user);
		return "account-home";
	}
	
	
	
	@GetMapping("/account-message-inbox")
	public String userMessageInbox(){
		return "account-message-inbox";
	}
	
	
	@PostMapping("/saveUser")
	public String saveUser(@RequestParam("profileImg") MultipartFile profileImg,
			@RequestParam("idProofImg") MultipartFile idProofImg, @ModelAttribute User user) {
		 
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
			
			try {
	            // Get the filename
	            String profileImgfilename = profileImgFilename+profileImg.getOriginalFilename();
	            String idProofImgfilename = idProofImgFilename+idProofImg.getOriginalFilename();
	            
	            // Create the directory if it doesn't exist
	            File directory = new File(UPLOAD_DIRECTORY);
	            if (!directory.exists()) {
	                directory.mkdirs();
	            }

	            Path profileImgfilePath = Paths.get(directory.getAbsolutePath(), profileImgfilename);
	            Files.write(profileImgfilePath, profileImg.getBytes());

	            // Save the file to the uploads directory
	            Path idProofImgfilePath = Paths.get(directory.getAbsolutePath(), idProofImgfilename);
	            Files.write(idProofImgfilePath, idProofImg.getBytes());

	        } catch (IOException e) {
	            e.printStackTrace();
//	            return "Failed to upload file";
	        }
			
		}
		
		return "redirect:/login";
	}

//	private void saveImages(User user) {
//		try {
//		File saveFile = new ClassPathResource("static/UserProfile").getFile();
//		//System.out.println("Absolute Path: " + saveFile.getAbsolutePath());
//
//        if (!saveFile.exists()) {
//            boolean created = saveFile.mkdirs();
//            if (!created) {
//                System.err.println("Failed to create directory: " + saveFile.getAbsolutePath());
//                return;
//            }
//        }
//
//        
//        
//        //Use the correct file names from user entity
//        Path profileImgPath = Paths.get(saveFile.getAbsolutePath() + File.separator + user.getProfileImgPath());
//        Path idProofImgPath = Paths.get(saveFile.getAbsolutePath() + File.separator + user.getIdProofImgPath());
//
//        Files.copy(user.getProfileImg().getInputStream(), profileImgPath, StandardCopyOption.REPLACE_EXISTING);
//        Files.copy(user.getIdProofImg().getInputStream(), idProofImgPath, StandardCopyOption.REPLACE_EXISTING);
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        System.err.println("Failed to save images: " + e.getMessage());
//    }
//	}
	
	 @PostMapping("/account-home/{id}/edit")
	    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User updatedUser) {
	        userservice.updateUser(id, updatedUser);
	        
	        System.out.println(id);
	        return "redirect:/account-home"; // Redirect to user details page after update
	    }

	
}
