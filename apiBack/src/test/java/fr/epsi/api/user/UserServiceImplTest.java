package fr.epsi.api.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.epsi.api.security.SecurityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.anyString;

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
	void testLogin(){
		User user = new User();
		user.setPseudo("test");
		user.setPassword("pwdEncrypted");

		Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());
		Mockito.doReturn("testpwd")
				.when(securityService)
				.decryptPassword(ArgumentMatchers.eq("pwdEncrypted"),anyString());

		sut.login("test","testpwd");

		Mockito.verify(securityService).decryptPassword(ArgumentMatchers.eq("pwdEncrypted"),anyString());
	}

	@Test
	void testSave() throws UnsupportedEncodingException {
		User user = new User();
		user.setPseudo("test");
		user.setPassword("testpwd");

		ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
		Mockito.doReturn(user).when(userRepository).save(argumentCaptor.capture());
		Mockito.doReturn("pwdEncrypted")
				.when(securityService)
				.encryptPassword(ArgumentMatchers.eq("testpwd"),anyString());

		sut.save(user.getPseudo(),user.getPassword());

		Mockito.verify(userRepository).save(argumentCaptor.getValue());

		Assertions.assertEquals(argumentCaptor.getValue().getPseudo(),"test");
		Assertions.assertEquals(argumentCaptor.getValue().getPassword(),"pwdEncrypted");
	}

	@Test
	void testFind(){
		User user = new User();
		user.setPseudo("test");
		user.setPassword("testpwd");

		Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());

		User result = sut.find("test");

		Assertions.assertEquals(user,result,"founded");
	}
	@Test
	void testFindNotFound(){

		Mockito.doReturn(Optional.empty()).when(userRepository).findById("notTest");

		Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> sut.find("notTest") );
		Assertions.assertEquals(exception.getMessage(),"User not found");
	}

}
