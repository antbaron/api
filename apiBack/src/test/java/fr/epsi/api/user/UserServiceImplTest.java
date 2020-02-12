package fr.epsi.api.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.h2.command.dml.MergeUsing.When;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.epsi.api.security.SecurityService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserServiceImplTest {

	@Mock
	UserRepository userRepository;
	
	@Mock
	SecurityService securityService;
	
	private static final String SECRET_KEY = "My_S3cr3t";
	@InjectMocks
	public UserServiceImpl sut;
	

	@Captor
	ArgumentCaptor <User> ac;
	
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
	void testFindByID() {
		
	}
	@Test
	void testSaveAnUser() throws UnsupportedEncodingException{
		Mockito.doReturn(null).when(userRepository).save(ac.capture());
		String pseudo = "Bob";
		String password = "bobpassword";
		String encriptPassword="";
		Mockito.doReturn(encriptPassword).when(securityService).encryptPassword(password, SECRET_KEY);
		
			sut.save(pseudo, password);
		
		Assertions.assertEquals(ac.getValue().getPseudo(),pseudo,"pseudo OK");
		Assertions.assertEquals(ac.getValue().getPassword(),encriptPassword, "encript OK");
	}
	@Test
	void testSaveUserAlreadyExist() throws UnsupportedEncodingException {
		//Arrange
		Mockito.doReturn(null).when(userRepository).save2(ac.capture());
		String pseudo = "Bob";
		String password = "bobpassword";
		String encriptPassword="";
		Mockito.doReturn(encriptPassword).when(securityService).encryptPassword(password, SECRET_KEY);
		//Act
		sut.save2(pseudo, password);
		//Assert			
		Mockito.verify(userRepository, Mockito.never()).save2(ac.getValue());
	}
	@Test
	void testSaveUserNoPseudo() {
		
	}

	@Test
	void testSaveUserNoPassword() {
		
	}
	@Test
	void testSaveUserNoPs() {
		
	}

}
