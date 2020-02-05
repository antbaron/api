package fr.epsi.api.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.epsi.api.security.SecurityService;
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
	void testFind() {
		//Arrange
		String pseudo = "John";
		User user = new User();
		user.setPseudo(pseudo);
		Mockito.doReturn(Optional.of(user)).when(userRepository).findById(pseudo);
		//Act
		User result = sut.find(pseudo);
		//Assert
		Assertions.assertEquals(user, result, "No user");
	}

	// TO CORRECT
	@Test
	void testNotFind() {
		//Arrange
		String pseudo = "John";
		User user = new User();
		user.setPseudo(pseudo);
		Mockito.doReturn(Optional.empty()).when(userRepository).findById(pseudo);
		//Act
		User result = sut.find(pseudo);
		//Assert
		Assertions.assertEquals(user, result, "No user");
	}

	@Test
	void testSave() throws UnsupportedEncodingException {
		//Arrange
		String pseudo = "John";
		String password = "encryptedPassword";
		User user = new User();
		user.setPseudo(pseudo);
		user.setPassword(password);

		ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
		Mockito.doReturn(null).when(userRepository).save(ac.capture());
		Mockito.doReturn(password).when(securityService).encryptPassword("password", "My_S3cr3t");

		//Act
		sut.save(pseudo,"password");
		User user2 = ac.getValue();

		//Assert
		Assertions.assertEquals(user.getPassword(), user2.getPassword(), "No password");
		Assertions.assertEquals(user.getPseudo(), user2.getPseudo(), "No pseudo");
	}

	// TO DO
	@Test
	void testLogin() {
		//Arrange
		String pseudo = "John";
		User user = new User();
		user.setPseudo(pseudo);
		Mockito.doReturn(Optional.empty()).when(userRepository).findById(pseudo);
		//Act
		User result = sut.find(pseudo);
		//Assert
		Assertions.assertEquals(user, result, "No user");
	}
}



