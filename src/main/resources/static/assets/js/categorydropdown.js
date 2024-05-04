const categoryOptions = {
	Vehicles: ["Exotic car rental services", "Bike tour guides", "Boat or yacht charter companies", "Bicycle rental services", "Car rental services", "Car Repair services", "other"],
	House: ["Vacation rental owners (Airbnb hosts)", "Property managers", "Bed and breakfast owners", "Home renovation specialists", "Personal chefs or private cooks", "Event decorators or stylists", "Wedding planners or coordinators", "Urban gardeners (offering gardening workshops or consultations)", "Professional organizers (home or office)", "Handmade artisanal soap or skincare product sellers", "Wedding dress or formal wear rental shops", "Other"],
	Children: ["Childcare providers", "Pet sitters", "Pet groomers or trainers", "Other"],
	Multimedia: ["Photographers", "DJs", "Music teachers (piano, guitar, violin, etc.)", "Freelance writers or content creators", "Graphic designers or illustrators", "Freelance event photographers or videographers", "other"],
	Services: ["Tour guides", "Language tutors or coaches", "Fitness instructors or yoga teachers (online or in-person)", "DIY workshop instructors (e.g., woodworking, pottery)", "Language exchange program organizers", "Wellness coaches or holistic healers", "Specialty travel planners (destination weddings, adventure trips)", "Online course creators or instructors", "Online course platforms (hosting multiple instructors)", "Subscription box creators (selling curated products)", "Freelance event photographers or videographers", "Professional organizers (home or office)", "Niche hobby instructors (model-making, birdwatching, etc.)", "Party or event entertainers (magicians, clowns, etc.)", "Caterers", "DJs", "Consultants (management, finance, marketing, etc.)", "Event planners", "Virtual assistants", "Travel consultants or agents", "Personal trainers", "Makeup artists", "Car owners (renting out vehicles)", "Farmers (selling produce)", "Gardeners (landscaping, gardening consultations)", "Carpenters or tradespeople", "Artisans or craftsmen (pottery, jewelry, furniture)", "Retailers (selling products)", "Art studios (offering classes)", "Art galleries (selling artwork)", "Freelancers (various fields)", "Coaches or mentors (business, life, etc.)", "Other"],
	Extra: ["Other"]
};

// Function to update city options based on selected country
function updateCityOptions() {
	const CategorySelect = document.getElementById("CategorySelect");
	const SubCategorySelect = document.getElementById("SubCategorySelect");
	const selectedCountry = CategorySelect.value;

	// Clear city dropdown
	SubCategorySelect.innerHTML = '<option value="">Select Category</option>';

	// Populate city dropdown based on selected country
	if (selectedCountry && categoryOptions[selectedCountry]) {
		categoryOptions[selectedCountry].forEach(city => {
			const option = document.createElement("option");
			option.text = city;
			option.value = city;
			SubCategorySelect.appendChild(option);
		});
	}
}