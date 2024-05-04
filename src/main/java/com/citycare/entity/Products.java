package com.citycare.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@NoArgsConstructor
@ToString
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long adsId;
	private String categoryads;
	private String subcategoryads;
	private String addType;
	private String adTitle;
	private String descAd;
	private String adCity;
	private long adPincode;
	private long price;
	private String uniqueID;
	private int enabled = 1;
	private int removed;

	@CreationTimestamp
	private Timestamp creationDate;
	private Timestamp updDate;
	private Timestamp referenceDate;
	
	@ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "products_adsId"))
    @Column(name = "image")
    private List<String> images; // Store image paths

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	// Your custom method to generate a unique identifier
	private String generateUniqueId() {
		Date currentDate = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("MMyyyy");
		String yearMonth = sdf.format(currentDate);
		return System.currentTimeMillis() + yearMonth;

	}	
	/*
	public long getAdPincode() {
		return adPincode;
	}

	public void setAdPincode(long adPincode) {
		this.adPincode = adPincode;
	}

	public String getAdCity() {
		return adCity;
	}
	
	public void setAdCity(String adCity) {
		this.adCity = adCity;
	}
	
	public String getCategoryads() {
		return categoryads;
	}

	public void setCategoryads(String categoryads) {
		this.categoryads = categoryads;
	}

	public String getSubcategoryads() {
		return subcategoryads;
	}

	public void setSubcategoryads(String subcategoryads) {
		this.subcategoryads = subcategoryads;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getRemoved() {
		return removed;
	}

	public void setRemoved(int removed) {
		this.removed = removed;
	}

	public String getUniqueId() {
		return uniqueID;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueID = uniqueId;
	}

	public long getAdsId() {
		return adsId;
	}

	public void setAdsId(long adsId) {
		this.adsId = adsId;
	}

	public String getCategory() {
		return categoryads;
	}

	public void setCategory(String categoryads) {
		this.categoryads = categoryads;
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}

	public String getDescAd() {
		return descAd;
	}

	public void setDescAd(String descAd) {
		this.descAd=descAd;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Timestamp updDate) {
		this.updDate = updDate;
	}

	public Timestamp getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(Timestamp referenceDate) {
		this.referenceDate = referenceDate;
	}
	
	

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		this.uniqueID = generateUniqueId();
	}

	public Products() {
		super();
	}

	public Products(long adsId, String categoryads, String subcategoryads, String addType, String adCity,long adPincode, String adTitle,
			String descAd, long price, String uniqueID, int enabled, int removed, Timestamp creationDate,
			Timestamp updDate, Timestamp referenceDate, User user) {
		super();
		this.adsId = adsId;
		this.categoryads = categoryads;
		this.subcategoryads = subcategoryads;
		this.addType = addType;
		this.adTitle = adTitle;
		this.descAd = descAd;
		this.price = price;
		this.uniqueID = uniqueID;
		this.enabled = enabled;
		this.removed = removed;
		this.creationDate = creationDate;
		this.updDate = updDate;
		this.referenceDate = referenceDate;
		this.user = user;
		this.adCity = adCity;
		this.adPincode = adPincode;
	}

	@Override
	public String toString() {
		return "Products [adsId=" + adsId + ", categoryads=" + categoryads + ", subcategoryads=" + subcategoryads
				+ ", addType=" + addType + ", adTitle=" + adTitle + ", descAd=" + descAd + ", adCity=" + adCity
				+ ", adPincode=" + adPincode + ", price=" + price + ", uniqueID=" + uniqueID + ", enabled=" + enabled
				+ ", removed=" + removed + ", creationDate=" + creationDate + ", updDate=" + updDate
				+ ", referenceDate=" + referenceDate + ", images=" + images + ", user=" + user + "]";
	}


	*/


}
