package fr.epsi.api.profitability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("profitabilities")
public class ProfitabilityController {

	@Autowired
	private ProfitabilityService profitabilityService;

	@PostMapping("gross")
	Float findGrossRentability(@RequestBody GrossRentability param) {
		return profitabilityService.findGrossRentability(param);
	}

	@PostMapping("net")
	Float findNetRentability(@RequestBody NetRentability param) {
		return profitabilityService.findNetRentability(param);
	}
}
