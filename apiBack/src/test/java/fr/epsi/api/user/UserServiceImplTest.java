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
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

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
	void testFindById(){
		//Création de d'un user
		User user = new User();
		user.setPseudo("Alec");
		user.setPassword("azerty");

		//Arrange
		Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());
		//Act
		User result = sut.find(user.getPseudo());
		//Assert
		Assertions.assertEquals(user, result, "User found");
	}

	@Test
	void testSave() throws UnsupportedEncodingException {
		//Arrange
		ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
		Mockito.doReturn(null).when(userRepository).save(ac.capture());
		Mockito.doReturn("NewPassword").when(securityService).encryptPassword(ArgumentMatchers.eq("NewPassword"), anyString());
		//Act
		sut.save("NewPseudo", "NewPassword");
		//Assert
		Mockito.verify(userRepository, Mockito.times(1)).save(ac.getValue());
		Assertions.assertEquals("NewPseudo", ac.getValue().getPseudo());
		Assertions.assertEquals("NewPassword", ac.getValue().getPassword());
	}


}
