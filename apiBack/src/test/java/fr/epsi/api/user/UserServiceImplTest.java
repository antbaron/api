package fr.epsi.api.user;

import fr.epsi.api.security.SecurityService;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

	@Mock
	SecurityService securityService;

	@InjectMocks
	public UserServiceImpl subject;

	@Test
	void findAll() {
		List<User> users = new ArrayList<>();
		Mockito.when(userRepository.findAll()).thenReturn(users);

		Iterable<User> result = subject.findAll();

		Assertions.assertEquals(users, result);
	}

	@Test
	void login() {
		String pseudo = "Pseudo";
		String password = "Password";
		User user = new User();
		user.setPseudo(pseudo);
		user.setPassword(password);

		Mockito.when(userRepository.findById(pseudo)).thenReturn(Optional.of(user));
		Mockito.when(securityService.decryptPassword(password, "My_S3cr3t")).thenReturn(password);

		subject.login(pseudo, password);

		Assertions.assertEquals(user.getPseudo(), "Pseudo");
		Assertions.assertEquals(user.getPassword(), "Password");
	}

	@Test
	void loginFail() {
		String pseudo = "Pseudo";
		String password = "Password";
		User user = new User();
		user.setPseudo(pseudo);

		Mockito.when(userRepository.findById(pseudo)).thenReturn(Optional.of(user));

		try {
			subject.login(pseudo, password);
			Assertions.fail();
		} catch (Exception e) {
			Assertions.assertEquals(e.getMessage(), "Not connected");
		}
		Assertions.assertThrows(Exception.class, ()-> {subject.login(pseudo, password); });
	}

	@Test
	void save() throws UnsupportedEncodingException {
		String pseudo = "Pseudo";
		String password = "Password";
		User user = new User();
		user.setPseudo(pseudo);
		user.setPassword(password);

		ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
		Mockito.when(userRepository.save(ac.capture())).thenReturn(null);
		Mockito.when(securityService.encryptPassword(password, "My_S3cr3t")).thenReturn(password);
		subject.save(pseudo,password);
		User user2 = ac.getValue();

		Assertions.assertEquals(user.getPseudo(), user2.getPseudo());
		Assertions.assertEquals(user.getPassword(), user2.getPassword());
	}

	@Test
	void saveFail() throws UnsupportedEncodingException {
		String pseudo = "Pseudo";
		String password = "";
		User user = new User();
		user.setPseudo(pseudo);
		user.setPassword(password);

		Mockito.when(securityService.encryptPassword(password, "My_S3cr3t")).thenThrow(new UnsupportedEncodingException("Erreur d'encryptage"));

		try {
			subject.save(pseudo, password);
			Assertions.fail();
		} catch (UnsupportedEncodingException e) {
			Assertions.assertEquals(e.getMessage(), "Erreur d'encryptage");
		}
		Assertions.assertThrows(UnsupportedEncodingException.class, ()-> {subject.save(pseudo, password); });
	}

	@Test
	void find() {
		String pseudo = "Pseudo";
		User user = new User();
		user.setPseudo(pseudo);
		Mockito.when(userRepository.findById(pseudo)).thenReturn(Optional.of(user));

		User result = subject.find(pseudo);

		Assertions.assertEquals(user, result);
	}

	/* Test pour le return userRepository.findById(pseudo).get();
	@Test
	void findNoValue() {
		String pseudo = "Pseudo";
		User user = new User();
		user.setPseudo(pseudo);
		Mockito.when(userRepository.findById(pseudo)).thenReturn(Optional.empty());
		try {
			subject.find(pseudo);
			Assertions.fail("Pas de pseudo return de la methode findById()");
		} catch (Exception e) {
			Assertions.assertEquals(e.getMessage(), "No value present");
		}
	}
	*/

}
