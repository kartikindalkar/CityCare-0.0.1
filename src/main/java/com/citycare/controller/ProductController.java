package com.citycare.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.citycare.entity.Products;
import com.citycare.entity.User;
import com.citycare.repository.ProductsRepo;
import com.citycare.repository.UserRepo;
import com.citycare.services.ProductUsers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {

	@Autowired
	ProductsRepo prdRepos;
	
	@Autowired
	ProductUsers prodUser;

	@Autowired
	private UserRepo userRepo;
	private static final String UPLOAD_DIRECTORY = "src/main/resources/static/productImages";

	@PostMapping("/saveProducts")
	public String saveProducts(@ModelAttribute Products products, HttpSession session, Model m,
			HttpServletRequest request, @RequestParam("ProductImagesfiles") MultipartFile[] files) {

		List<String> imagePaths = new ArrayList<>();
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				try {
					// Create the upload directory if it doesn't exist
					File directory = new File(UPLOAD_DIRECTORY);
					if (!directory.exists()) {
						directory.mkdirs();
					}

					// Get the file name
					String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

					// Save the file to the server
					byte[] bytes = file.getBytes();
					Path path = Paths.get(UPLOAD_DIRECTORY + File.separator + fileName);
					Files.write(path, bytes);

					// Add file path to the list
					imagePaths.add(fileName); // You may store the full path if required
				} catch (IOException e) {
					e.printStackTrace();
//                    redirectAttributes.addFlashAttribute("error", "Failed to upload " + file.getOriginalFilename());
					return "redirect:/products";
				}
			}
		}
		products.setImages(imagePaths);

		Products saveProducts = prdRepos.save(products);

		if (saveProducts != null) {
			// System.err.println("Save Success");

			session.setAttribute("msg", "Products Added successfully");
		} else {
			// System.out.println("Error in Server");
			session.setAttribute("msg", "Products not Added successfully");

		}

		return "redirect:/account-home";
	}

	@GetMapping("/ads-details/{id}")
	public String postsDetails(@PathVariable Long id, Principal p, Model m) {
		String email = p.getName();
		User user = userRepo.findByEmail(email);

		
		Products product = prodUser.getProductById(id);
        User produser = product.getUser();
     
        
        m.addAttribute("products", product);
        m.addAttribute("productuser", produser);
        m.addAttribute("user", user);
		
		return "ads-details";
	}

	@GetMapping("/account-myads")
	public String accountMyAds(Principal p, Model m) {
		String email = p.getName();
		User user = userRepo.findByEmail(email);

		List<Products> products = prdRepos.findByUser(user);
		
		m.addAttribute("user", user);
		m.addAttribute("products", products);
		return "account-myads";
	}

	@GetMapping("/account-show-ad/{id}")
	public String accountMyAdUser(@PathVariable Long id,Principal p, Model m) {
		// Find the product by its ID
	    Products ad = prdRepos.findById(id).orElse(null);   
	   
	    
	    // Check if the product exists
	    if (ad == null) {
	        // Handle the scenario where the product is not found
	        return "error"; // You can customize this to display a proper error page
	    }
	    
	    // Get the email of the logged-in user
	    String email = p.getName();
	    
	    // Find the user by email
	    User user = userRepo.findByEmail(email);
	    
	    // Check if the user exists
	    if (user == null) {
	        // Handle the scenario where the user is not found
	        return "error"; // You can customize this to display a proper error page
	    }
	    
	    // Add user and product to the model
	    m.addAttribute("user", user);
//	    m.addAttribute("userproduct", user);
	    m.addAttribute("product", ad);
	    
	    // Return the view name
	    return "account-show-ad";
	}
	
	@GetMapping("/seller-profile/{id}")
	public String sellerProfile(Principal p, Model m) {
		String email = p.getName();
		User user = userRepo.findByEmail(email);

		// Assuming there's a One-to-Many relationship between User and Product
		// Retrieve products associated with the user
		
//		List<Products> products = prdRepos.findByUser(user);
		
		m.addAttribute("userproduser", user);
//		m.addAttribute("products", products);
		return "seller-profile";
	}
	
	
}
