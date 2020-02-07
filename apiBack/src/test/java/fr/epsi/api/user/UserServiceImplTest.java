package fr.epsi.api.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.epsi.api.security.SecurityService;
import fr.epsi.api.user.controller.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserServiceImplTest {

	@Mock
	UserRepository userRepository;

	@Mock
	SecurityService securityService;

	private static final String SECRET_KEY = "My_S3cr3t";

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
	void testLogin(){

	}

	@Test
	void testSave() throws UnsupportedEncodingException {
		//Arrange
		User u = new User();
		u.setPseudo("test"); u.setPassword("test");
		ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
		Mockito.doReturn("test").when(securityService).encryptPassword("test", SECRET_KEY);
		Mockito.doReturn(null).when(userRepository).save(ac.capture());

		//Act
		sut.save("test", "test");
		//Assert
		Assertions.assertEquals(ac.getValue().getPassword(), u.getPassword(), "Saved User");
	}

	@Test
	void testFinbyId(){
		//Arrange
		User u = new User();
		Optional<User> user = Optional.of(u);
		Mockito.doReturn(user).when(userRepository).findById("1");
		//Act
		User result = sut.find("1");
		//Assert
		Assertions.assertEquals(result, u, "Same user");
	}

}
