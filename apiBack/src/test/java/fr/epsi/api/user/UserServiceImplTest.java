package fr.epsi.api.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.epsi.api.security.SecurityService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.support.SecurityContextProvider;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.ws.rs.core.SecurityContext;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserServiceImplTest {

	@Mock
	UserRepository userRepository;

	@Mock
	SecurityService securityService;
	
	@InjectMocks
	public UserServiceImpl sut;
	
	@Test
	void testFindAll() {		
		//Arrange
		List<User> users = new ArrayList<User>();
		Mockito.doReturn(users).when(userRepository).findAll();
		//Act
		Iterable<User> result = sut.findAll();
		//Assert
		Assertions.assertEquals(users, result, "No user");
	}

	//Test de la fonction login
	@Test
	void testLogin() {
		User user = new User();
		user.setPseudo("Taliraz");
		user.setPassword("crypt");
		Optional<User> userO = Optional.of(user);
		Mockito.doReturn(userO).when(userRepository).findById(ArgumentMatchers.eq("Taliraz"));
		Mockito.doReturn("password").when(securityService).decryptPassword(
				ArgumentMatchers.eq("crypt"),
				anyString()
		);
		sut.login("Taliraz","password");
		Mockito.verify(securityService).decryptPassword(ArgumentMatchers.eq("crypt"),anyString());
	}

	//Test de la fonction save
	@Test
	void testSave() throws UnsupportedEncodingException {
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		Mockito.doReturn("crypt").when(securityService).encryptPassword(anyString(),anyString());
		Mockito.doReturn(null).when(userRepository).save(userCaptor.capture());
		sut.save("Taliraz","password");
		User userSaved = userCaptor.getValue();
		User user = new User();
		user.setPseudo("Taliraz");
		user.setPassword("crypt");
		Assertions.assertEquals("Taliraz",userSaved.getPseudo());
		Assertions.assertEquals("crypt",userSaved.getPassword());
	}

	//Test de la fonction find
	@Test
	void testFind(){
		User user = new User();
		user.setPseudo("Taliraz");
		user.setPassword("crypt");
		Optional<User> userO = Optional.of(user);
		Mockito.doReturn(userO).when(userRepository).findById(ArgumentMatchers.eq("Taliraz"));
		User userReturned = sut.find("Taliraz");
		Assertions.assertEquals(user,userReturned);
	}

}
