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

	@Test testFind(){
		ArgumentCaptor<user> ac= new User();
		Mockito.doReturn(user).when(userRepository).find(ac.captured);
		sut.find();
		ac.getValue();
		Assertions.assertEquals(user, result, "No user");
	}

	@Test save(){
		User user = mock(User.class);
		ArgumentCaptor<setPseudo> valueCapture = ArgumentCaptor.forClass(setPseudo.class);
		ArgumentCaptor<setPassword> valueCapture = ArgumentCaptor.forClass(setPassword.class);
		Mockito.doReturn(user).when(userRepository).save(user.captured);
		sut.save("test", "test");
  		assertEquals("captured", valueCapture.getValue());
	}
}
