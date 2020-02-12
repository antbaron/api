package fr.epsi.api.profitability;

import org.springframework.stereotype.Service;

@Service
public class ProfitabilityServiceImpl implements ProfitabilityService {

	@Override
	public float findGrossRentability(GrossRentability param) {
		float annualRent = annualRent(param.getMensualRent());
		float percent = annualRent / param.getBuyPrice();
		return percent * 100;
	}

	@Override
	public Float findNetRentability(NetRentability param) {
		float annualRent = annualRent(param.getMensualRent());
		float income = annualRent - param.getCharge() - param.getWork();
		float percent = income / (param.getBuyPrice() + param.getCreditCost());
		return percent * 100;
	}

	private float annualRent(int mensualRent) {
		return mensualRent * 12f;
	}

}
