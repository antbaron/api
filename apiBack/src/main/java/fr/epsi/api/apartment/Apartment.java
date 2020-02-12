package fr.epsi.api.apartment;

public class Apartment {

	private int price;
	private int surfaceArea;
	private int roomsQuantity;
	private int yearOfConstruction;
	private String title;
	private String description;
	private String city;
	private String postalCode;
	private String energyClassification;
	private String greenhouseGazClassification;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSurfaceArea() {
		return surfaceArea;
	}

	public void setSurfaceArea(int surfaceArea) {
		this.surfaceArea = surfaceArea;
	}

	public int getRoomsQuantity() {
		return roomsQuantity;
	}

	public void setRoomsQuantity(int roomsQuantity) {
		this.roomsQuantity = roomsQuantity;
	}

	public int getYearOfConstruction() {
		return yearOfConstruction;
	}

	public void setYearOfConstruction(int yearOfConstruction) {
		this.yearOfConstruction = yearOfConstruction;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getEnergyClassification() {
		return energyClassification;
	}

	public void setEnergyClassification(String energyClassification) {
		this.energyClassification = energyClassification;
	}

	public String getGreenhouseGazClassification() {
		return greenhouseGazClassification;
	}

	public void setGreenhouseGazClassification(String greenhouseGazClassification) {
		this.greenhouseGazClassification = greenhouseGazClassification;
	}

}
