package fr.epsi.api.apartment;

import java.util.List;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

public interface ApartmentService {

	void findApartments();
	List<Apartment> findApartments(String typeAct, int maxPrice)
			throws ClientHandlerException, UniformInterfaceException, Exception;
}
