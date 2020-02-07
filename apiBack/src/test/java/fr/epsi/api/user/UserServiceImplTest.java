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
	
	/*
	 * @Test void testFind() { //Arrange User user = new User();
	 * Mockito.doReturn(user).when(userRepository).findAll(); //Act Iterable<User>
	 * result = sut.findAll(); //Assert Assertions.assertEquals(users, result,
	 * "No user"); }
	 */
	
	//Mockito.verify(userRepository,Mockito.times(1).save(...));
	//ArgumentCaptor<User> ac = ArgumentCaptor.forClass(user.class);
	//Mockito.doReturn(null).when(userRepository).save(ac.capture());
	//ac.getValue()
	//Optional<User> ouser
	//if(ouser.isPresent())
	//Optional.of(new USer())
	//Optional.empty();
}
