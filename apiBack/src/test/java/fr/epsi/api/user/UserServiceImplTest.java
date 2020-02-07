package fr.epsi.api.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

//	@Test
//	void testSave() throws UnsupportedEncodingException {
//		User user = new User();
//		user.setPseudo("Alec");
//		user.setPassword("azerty");
//		//Arrange
//		Mockito.doNothing().when(userRepository).save(Mockito.any(User.class));
//
//		sut.save(user.getPseudo(), user.getPassword());
//
//		Mockito.verify(userRepository, Mockito.times(1)).save(user);
//
//
//	}

}
