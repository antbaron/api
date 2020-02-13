package fr.epsi.api.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.epsi.api.security.SecurityService;
import fr.epsi.api.user.controller.UserDto;
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

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserServiceImplTest {

	private static final String SECRET_KEY = "My_S3cr3t";

	@Mock
	UserRepository userRepository;

	@Mock
	SecurityService securityService;

	@InjectMocks
	public UserServiceImpl sut;
	
	@Test
	void testFindAll() {
		List<User> test_users = new ArrayList<User>();
		Mockito.doReturn(test_users).when(userRepository).findAll();
		Iterable<User> result_list = sut.findAll();
		Assertions.assertEquals(test_users, result_list, "No user");
	}

	@Test
	void testLoginPassant() {
		User test_user = new User();
		test_user.setPseudo("user_test");
		test_user.setPassword("pwd");
		Mockito.doReturn(Optional.of(test_user)).when(userRepository).findById(test_user.getPseudo());
		Mockito.doReturn(test_user.getPassword()).when(securityService).decryptPassword(test_user.getPassword(), SECRET_KEY);
		sut.login(test_user.getPseudo(), test_user.getPassword());
		Mockito.verify(securityService).decryptPassword(test_user.getPassword(), SECRET_KEY);
	}

	@Test
	void testLoginNonPassant() {
		User test_user = new User();
		test_user.setPseudo("user_test");
		Mockito.when(userRepository.findById("user_test")).thenReturn(Optional.of(test_user));
		try {
			sut.login("user_test", "pwd");
			Assertions.fail("La méthode aurait du lever une exception");
		} catch (Exception e) {
			Assertions.assertEquals(e.getMessage(), "Not connected");
		}
		Assertions.assertThrows(Exception.class, ()-> {sut.login("user_test", "pwd"); });
	}

	@Test
	void testSave() throws UnsupportedEncodingException {
		User test_users = new User();
		test_users.setPseudo("user_test");
		test_users.setPassword("pwd");
		ArgumentCaptor<User> arg_captor_user = ArgumentCaptor.forClass(User.class);
		Mockito.doReturn("pwd").when(securityService).encryptPassword("pwd", SECRET_KEY);
		Mockito.doReturn(null).when(userRepository).save(arg_captor_user.capture());
		sut.save("user_test", "pwd");
		Assertions.assertEquals(arg_captor_user.getValue().getPassword(), test_users.getPassword(), "Saved User");
	}

	@Test
	void testSaveNonPassant() throws UnsupportedEncodingException {
		User test_user = new User();
		test_user.setPseudo("user_test");
		test_user.setPassword("");
		Mockito.when(securityService.encryptPassword("", SECRET_KEY)).thenThrow(new UnsupportedEncodingException("Erreur d'encryptage"));
		try {
			sut.save("user_test", "");
			Assertions.fail("La méthode aurait du lever une exception");
		} catch (UnsupportedEncodingException e) {
			Assertions.assertEquals(e.getMessage(), "Erreur d'encryptage");
		}
		Assertions.assertThrows(UnsupportedEncodingException.class, ()-> {sut.save("user_test", ""); });
	}

	@Test
	void testFinbyIdPassant(){
		User test_users = new User();
		Optional<User> user_optional = Optional.of(test_users);
		Mockito.doReturn(user_optional).when(userRepository).findById("1");
		User result = sut.find("1");
		Assertions.assertEquals(result, test_users);
	}

	@Test
	void testFindNonPassant() {
		User test_user = new User();
		test_user.setPseudo("test_user");
		Mockito.when(userRepository.findById("test_user")).thenReturn(Optional.empty());
		try {
			sut.find("test_user");
			Assertions.fail("Pas de pseudo correspondant");
		} catch (Exception e) {
			Assertions.assertEquals(e.getMessage(), "No value present");
		}
	}

}
