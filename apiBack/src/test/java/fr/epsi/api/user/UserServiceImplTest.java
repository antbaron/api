package fr.epsi.api.user;

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
	void testFIndById() {		
		//Arrange
                User user = new User();
                
                user.setPseudo("pseudo");

		Mockito.doReturn(Optional.of(user)).when(userRepository).findById("pseudo");
		//Act
		User result = sut.find("pseudo");
		//Assert
		Assertions.assertEquals(user, result, "No user");
	}
        
        @Test
        void testIfFIndByIdReturnNull() {		
		//Arrange
		Mockito.doReturn(Optional.empty()).when(userRepository).findById("pseudoe");
		//Act
		User result = sut.find("pseudoe");
		//Assert
		Assertions.assertEquals(null, result, "No user");
	}

}