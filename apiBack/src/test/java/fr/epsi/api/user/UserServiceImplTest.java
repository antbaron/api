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
//import sun.plugin.liveconnect.SecurityContextHelper;

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

	@Test
	void testLogin() {
		User user = new User();
		user.setPseudo("David");
		user.setPassword("123soleil");
		Optional<User> userO = Optional.of(user);
		Mockito.doReturn(userO).when(userRepository).findById(ArgumentMatchers.eq("David"));
		Mockito.doReturn("password").when(securityService).decryptPassword(
				ArgumentMatchers.eq("123soleil"),
				anyString()
		);
		sut.login("David","password");
		Mockito.verify(securityService).decryptPassword(ArgumentMatchers.eq("123soleil"),anyString());
	}

	@Test
	void testSave() throws UnsupportedEncodingException {
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		Mockito.doReturn("123soleil").when(securityService).encryptPassword(anyString(),anyString());
		Mockito.doReturn(null).when(userRepository).save(userCaptor.capture());
		sut.save("David","password");
		User userSaved = userCaptor.getValue();
		User user = new User();
		user.setPseudo("David");
		user.setPassword("123soleil");
		Assertions.assertEquals("David",userSaved.getPseudo());
		Assertions.assertEquals("123soleil",userSaved.getPassword());
	}

	@Test
	void testFind(){
		User user = new User();
		user.setPseudo("David");
		user.setPassword("123soleil");
		Optional<User> userO = Optional.of(user);
		Mockito.doReturn(userO).when(userRepository).findById(ArgumentMatchers.eq("David"));
		User userReturned = sut.find("David");
		Assertions.assertEquals(user,userReturned);
	}

}
