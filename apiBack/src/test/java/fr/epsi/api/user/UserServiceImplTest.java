package fr.epsi.api.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
	void testLogin() {
		//Arrange
		//Act
		//Assert
	}

	@Test
	void testSave() {
		//Arrange
		//Act
		//Assert
	}

	@Test
	void testFindOK() {
		//Arrange
		String str = "testPseudo";
		User user = new User();
		user.setPseudo(str);
		Mockito.doReturn(Optional.of(user)).when(userRepository).findById(str);
		//Act
		User result = null;
		try {
			result = sut.find(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Assert
		Assertions.assertEquals(user, result, String.format("User with pseudo : %s",str));
	}

	@Test
	void testFindNOTFOUND() {
		//Arrange
		String str = "testPseudo";
		Mockito.doReturn(null).when(userRepository).findById(str);
		//Act
		final User[] result = {null};
		Exception exception = assertThrows(Exception.class, () -> {
			result[0] = sut.find(str);
		});
		//Assert
	}

}
