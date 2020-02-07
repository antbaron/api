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
	void testlogin() {
		//Arrange
        User user = new User();
        String pseudo = "pseudo";
        String password ="password";
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(pseudo);
        Mockito.doReturn(password).when(securityService).decryptPassword(user.getPassword(),"My_S3cr3t");
        //ACT
        sut.login(pseudo, password);
        
        Mockito.verify(userRepository,Mockito.times(1)).findById(pseudo);
        Mockito.verify(securityService,Mockito.times(1)).decryptPassword(user.getPassword(),"My_S3cr3t");
        
	}
	
	@Test 
	void testSave() {
		User user = new user();
		ArgumentCaptor<User> acu = ArgumentCaptor.forClass(user.class);
		Mockito.doReturn("").when(securityService).encryptPassword("123", SECRET_KEY);
		sut.save("123", "password");
		Assertion.assertEquals(actu.getValue(), "Saved user");
		
	}
	
	@Test 
	void tesFindById() {
		User user = new user();
		
		Optional<User> _user = optional.of(user);
		Mockito.doReturn(_user).when(userRepository).findById("1");
		
		User result = sut.find("1");
		Assertion.assertEquals(result, user)
		
		
				
		
	}

}
