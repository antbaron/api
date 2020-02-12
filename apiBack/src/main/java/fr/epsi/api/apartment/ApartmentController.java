package fr.epsi.api.apartment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("apartments")
public class ApartmentController {

	@Autowired
	private ApartmentService searchService;

	@GetMapping("")
	public List<Apartment> findApartments(@RequestParam("typeAct") String typeAct,
			@RequestParam("maxPrice") Integer maxPrice) throws Exception {
		return searchService.findApartments(typeAct, maxPrice);
	}

}
