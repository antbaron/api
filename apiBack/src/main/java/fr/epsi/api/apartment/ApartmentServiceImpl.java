package fr.epsi.api.apartment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

@Service
public class ApartmentServiceImpl implements ApartmentService {

	@Autowired
	private ApartmentRepository apartmentRepository;

	@Override
	public void findApartments() {
		apartmentRepository.findApartments();
		
	}

	@Override
	public List<Apartment> findApartments(String typeAct, int maxPrice)
			throws ClientHandlerException, UniformInterfaceException, Exception {
		return apartmentRepository.findApartments(typeAct, maxPrice);
	}
}
