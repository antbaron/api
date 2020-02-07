package fr.epsi.api.user;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserServiceImplTest 
{

	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	public UserServiceImpl sut;
	
	@Test
//	void testFindAll() 
//	{		
//		//Arrange
//		List<User> users = new ArrayList<User>();
//		Mockito.doReturn(users).when(userRepository).findAll();
//		//Act
//		Iterable<User> result = sut.findAll();
//		//Assert
//		Assertions.assertEquals(users, result, "No user");
//	}
	
	void testCreate() 
	{		
		//Arrange
		User userOne = new User();
		Mockito.doReturn(userOne).when(userRepository).save("ripocat", "admin");
		Mockito.doReturn(userOne).when(userRepository).findAll();
		//Act
		Iterable<User> result = sut.findAll();
		//Assert
		Assertions.assertEquals(userOne, result, "One user");
	}

}
