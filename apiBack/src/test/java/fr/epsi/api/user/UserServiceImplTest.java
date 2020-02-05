package fr.epsi.api.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

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

import fr.epsi.api.security.SecurityService;
import net.bytebuddy.asm.Advice.Argument;

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
       void TestSave() throws UnsupportedEncodingException{
		//Arrange
                
                sut.save("Nicolas", "Nicolas");
                List<User> users = new ArrayList<User>();
		Mockito.doReturn(users).when(userRepository).findAll();
		//Act
		Iterable<User> result = sut.findAll();
		//Assert
                System.out.println("le tableau : " +result);
//		Assertions.assertEquals(users, result, "No user");
       }

	@Test
	void testLogin() {		
		//Arrange
//		User user = new User();
//		String pseudo = "test123";
//		String password = "test321";
//		user.setPassword(password);
//		user.setPseudo(pseudo);
//		Mockito.doReturn(user).when(userRepository).login(pseudo, password);
//		//Act
//		Iterable<User> result = sut.findAll();
//		//Assert
//		Assertions.assertEquals(users, result, "No user");
	}
	@Test
	void save() throws UnsupportedEncodingException {
		
		User useru = new User();
		
		useru.setPassword("test321");
		useru.setPseudo("test123");
		
		ArgumentCaptor<User> acu = ArgumentCaptor.forClass(User.class);
		
		Mockito.doReturn(null).when(userRepository).save(acu.capture());
		
		Mockito.doReturn("test321").when(securityService).encryptPassword("test321", "My_S3cr3t");
		
		sut.save("test123", "test321" );
				
		Assertions.assertEquals(useru.getPassword(), acu.getValue().getPassword());
		
	}
	
	@Test
	void testFindById() {		
		//Arrange
		User useru = new User();
		
		Optional<User> user = Optional.of(useru);
		Mockito.doReturn(user).when(userRepository).findById("1");
		
		User result = sut.find("1");
		Assertions.assertEquals(result,  useru);
	
	}
}
