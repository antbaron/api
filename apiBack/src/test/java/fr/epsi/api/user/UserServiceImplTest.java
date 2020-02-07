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
			Mockito.doReturn("Password").when(securityService).encryptPassword(ArgumentMatchers.eq("Password"), anyString());
            //Act
            sut.save("Sam", "Password");
            //Assert
            Mockito.verify(userRepository, Mockito.times(1)).save(ac.getValue());
            Assertions.assertEquals("Sam", ac.getValue().getPseudo());
            Assertions.assertEquals("Password", ac.getValue().getPassword());
		
	}
	
	@Test
	void testFind() {

       /* ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
        Mockito.doReturn(null).when(userRepository).save(ac.capture());
		Mockito.doReturn("Password").when(securityService).encryptPassword(ArgumentMatchers.eq("Password"), anyString());
		
		sut.find("antbaron");
		
		Mockito.verify(mock)*/
	}
	
	
	

}







