package com.citycare.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;




@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
@ToString
@Table(name="citycare_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String creationDate;
	private String updDate;
	private Timestamp referenceDate;
	private String firstName;
	private String lastName;
	private long contact;
	private String gender;
	private String address;
	private String city;
	private String state;
	private String country;
	private String pincode;
	private String email;
	private String password;
	private String uniqueID;
	private String role;
	@Transient // Mark the field as transient to exclude it from JPA persistence
    private MultipartFile profileImg;
    @Transient
    private MultipartFile idProofImg;
	private String profileImgPath;	
	private String idProofImgPath;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Products> products;

	@Override
	public String toString() {
		return "User [id=" + id + ", creationDate=" + creationDate + ", updDate=" + updDate + ", referenceDate="
				+ referenceDate + ", firstName=" + firstName + ", lastName=" + lastName + ", contact=" + contact
				+ ", gender=" + gender + ", address=" + address + ", city=" + city + ", state=" + state + ", country="
				+ country + ", pincode=" + pincode + ", profileImg=" + profileImg + ", idProofImg=" + idProofImg
				+ ", profileImgPath=" + profileImgPath + ", email=" + email + ", password=" + password + ", uniqueID="
				+ uniqueID + ", idProofImgPath=" + idProofImgPath + ", role=" + role + "]";
	}
	
	

	public List<Products> getProducts() {
		return products;
	}



	public void setProducts(List<Products> products) {
		this.products = products;
	}



	public MultipartFile getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(MultipartFile profileImg) {
		this.profileImg = profileImg;
	}



	public MultipartFile getIdProofImg() {
		return idProofImg;
	}



	public void setIdProofImg(MultipartFile idProofImg) {
		this.idProofImg = idProofImg;
	}



	public String getIdProofImgPath() {
		return idProofImgPath;
	}



	public void setIdProofImgPath(String idProofImgPath) {
		this.idProofImgPath = idProofImgPath;
	}



	public void setProfileImgPath(String profileImgPath) {
		this.profileImgPath = profileImgPath;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProfileImgPath() {
		return profileImgPath;
	}

	public void setProfileImg(String profileImgPath) {
		this.profileImgPath = profileImgPath;
	}

	public String getIdProofImgaPath() {
		return idProofImgPath;
	}

	public void setIdProofImg(String idProofImgPath) {
		this.idProofImgPath = idProofImgPath;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getUpdDate() {
		return updDate;
	}

	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	public Timestamp getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(Timestamp referenceDate) {
		this.referenceDate = referenceDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User() {
        // Generate and set your custom unique identifier
        this.uniqueID = generateUniqueId();
        this.creationDate = CreationDate();
        this.updDate= UpdatedDate();
    }

    // Getter and setter for uniqueId

    public String getUniqueId() {
        return uniqueID;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueID = uniqueId;
    }
    
    // Your custom method to generate a unique identifier
    private String generateUniqueId() {
    	Date currentDate = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("MMyyyy");
        String yearMonth = sdf.format(currentDate);
        return System.currentTimeMillis()+yearMonth;
        
    }
	
    public String CreationDate() {
    	LocalDateTime currentDateTime = LocalDateTime.now();

        // Format the timestamp using a specific pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = currentDateTime.format(formatter);
        return formattedTimestamp;
    }

    public String UpdatedDate() {
    	LocalDateTime currentDateTime = LocalDateTime.now();

        // Format the timestamp using a specific pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = currentDateTime.format(formatter);
        return formattedTimestamp;
    }
	
	
	
}
