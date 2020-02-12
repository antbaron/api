package fr.epsi.api.profitability;

public class NetRentability extends GrossRentability{

	private int work;
	private int charge;
	private int creditCost;
	public int getWork() {
		return work;
	}
	public void setWork(int work) {
		this.work = work;
	}
	public int getCharge() {
		return charge;
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	public int getCreditCost() {
		return creditCost;
	}
	public void setCreditCost(int creditCost) {
		this.creditCost = creditCost;
	}
	
	
}
