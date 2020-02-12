package fr.epsi.api.apartment;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Apartments {

	private int total;
	
	private List<Apartment> realEstateAds;
	
	private List<Apartment> leadingAds;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Apartment> getRealEstateAds() {
		return realEstateAds;
	}

	public void setRealEstateAds(List<Apartment> realEstateAds) {
		this.realEstateAds = realEstateAds;
	}
	
	public List<Apartment> getLeadingAds() {
		return leadingAds;
	}

	public void setLeadingAds(List<Apartment> leadingAds) {
		this.leadingAds = leadingAds;
	}
	
}
