package fr.epsi.api.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.epsi.api.security.SecurityService;
import jdk.tools.jlink.internal.TaskHelper;
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

	@Mock
	SecurityService securityService;
	
	@Test
	void testFindAll() {		
		//Arrange
		List<User> users = new ArrayList<>();
		Mockito.doReturn(users).when(userRepository).findAll();
		//Act
		Iterable<User> result = sut.findAll();
		//Assert
		Assertions.assertEquals(users, result, "No user");
	}

	@Test
	void testLogin(){
		String pseudo = "test";
		String password = "test";
		User user = Mockito.spy(new User());
		user.setPassword(password);
		user.setPseudo(pseudo);

		Optional opt = Mockito.spy(Optional.of(user));

		Mockito.doReturn(opt).when(userRepository).findById(pseudo);
		Mockito.doReturn(true).when(opt).isPresent();

		sut.login(pseudo, password);

		Mockito.verify(securityService).decryptPassword(password, "My_S3cr3t");
//          Mockito.verify(password).equals();
		Mockito.verify(opt).isPresent();
	}

	@Test
	void testSave() throws UnsupportedEncodingException {
		//Arrange
		ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
		//On peut mettre ce qu'on veut dans doReturn car la methode est void
		Mockito.doReturn("ok").when(userRepository).save(ac.capture());

		//Act
		sut.save("pseudo","pass");

		//Assert
		Mockito.verify(sut).save("login","password");
		Assertions.assertEquals(ac.getValue().getPseudo(),"pseudo");

	}

	@Test
	void testFind(){

	}

}
