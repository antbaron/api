package fr.epsi.api.user;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import fr.epsi.api.security.SecurityService;
import static org.mockito.ArgumentMatchers.anyString;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserServiceImplTest {

	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	public UserServiceImpl sut;
	

	private static final String SECRET_KEY = "My_S3cr3t";
	
	@Mock
	public SecurityService securityService;
	
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
	void testSave() throws UnsupportedEncodingException {
			//Arrange
            ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
            Mockito.doReturn(null).when(userRepository).save(ac.capture());
			Mockito.doReturn("Password").when(securityService).encryptPassword("UserPassword", SECRET_KEY);
            //Act
            sut.save("UserTest", "UserPassword");
            //Assert
            Mockito.verify(securityService,Mockito.times(1)).encryptPassword("UserPassword", SECRET_KEY);
            Mockito.verify(userRepository, Mockito.times(1)).save(ac.getValue());
            Assertions.assertEquals("UserTest", ac.getValue().getPseudo());
            Assertions.assertEquals("Password", ac.getValue().getPassword());
		
	}
	
	@Test
	void testFind() {

       	
		
		
	}
	
	
	@Test
	void testLogin() {
		
		
	}
	
	
	

}







