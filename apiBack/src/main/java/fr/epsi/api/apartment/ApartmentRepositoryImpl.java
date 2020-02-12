package fr.epsi.api.apartment;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Repository;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.WebResource.Builder;

@Repository
public class ApartmentRepositoryImpl implements ApartmentRepository {

	@Override
	public void findApartments() {
		ClientConfig clientConfig = new DefaultClientConfig();

		// Create Client based on Config
		Client client = Client.create(clientConfig);

		WebResource webResource = client.resource(
				"https://www.bienici.com/realEstateAds.json);//?filters=%7B%22size%22%3A24%2C%22from%22%3A0%2C%22filterType%22%3A%22buy%22%2C%22propertyType%22%3A%5B%22house%22%2C%22flat%22%5D%2C%22maxPrice%22%3A200000%2C%22page%22%3A1%2C%22resultsPerPage%22%3A24%2C%22maxAuthorizedResults%22%3A2400%2C%22sortBy%22%3A%22relevance%22%2C%22sortOrder%22%3A%22desc%22%2C%22onTheMarket%22%3A%5Btrue%5D%2C%22showAllModels%22%3Afalse%2C%22zoneIdsByTypes%22%3A%7B%22zoneIds%22%3A%5B%22-28722%22%5D%7D%7D&extensionType=extendedIfNoResult&leadingCount=2");

		Builder builder = webResource.accept(MediaType.APPLICATION_JSON) //
				.header("content-type", MediaType.APPLICATION_JSON);

		ClientResponse response = builder.get(ClientResponse.class);

		// Status 200 is successful.
		if (response.getStatus() != 200) {
			System.out.println("Failed with HTTP Error code: " + response.getStatus());
			String error = response.getEntity(String.class);
			System.out.println("Error: " + error);
			return;
		}

		Apartments employee = (Apartments) response.getEntity(Apartments.class);

		System.out.println(employee.getTotal());
	}

	/**
	 * 
	 * @param typeAct buy
	 * @return
	 * @throws Exception
	 * @throws UniformInterfaceException
	 * @throws ClientHandlerException
	 */
	public List<Apartment> findApartments(String typeAct, int maxPrice)
			throws ClientHandlerException, UniformInterfaceException, Exception {
		ClientConfig clientConfig = new DefaultClientConfig();
		Client client = Client.create(clientConfig);

		WebResource webResource = client.resource(
				"https://www.bienici.com/realEstateAds.json);//?filters=%7B%22size%22%3A24%2C%22from%22%3A0%2C%22filterType%22%3A%22"
						+ typeAct + "%22%2C%22propertyType%22%3A%5B%22house%22%2C%22flat%22%5D%2C%22maxPrice%22%3A"
						+ maxPrice
						+ "%2C%22page%22%3A1%2C%22resultsPerPage%22%3A24%2C%22maxAuthorizedResults%22%3A2400%2C%22sortBy%22%3A%22relevance%22%2C%22sortOrder%22%3A%22desc%22%2C%22onTheMarket%22%3A%5Btrue%5D%2C%22showAllModels%22%3Afalse%2C%22zoneIdsByTypes%22%3A%7B%22zoneIds%22%3A%5B%22-28722%22%5D%7D%7D&extensionType=extendedIfNoResult&leadingCount=2");

		Builder builder = webResource.accept(MediaType.APPLICATION_JSON).header("content-type",
				MediaType.APPLICATION_JSON);
		ClientResponse response = builder.get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new Exception(response.getEntity(String.class));

		}

		Apartments resultSearch = (Apartments) response.getEntity(Apartments.class);
		return resultSearch.getRealEstateAds();
	}
}
