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

import static javax.crypto.Cipher.SECRET_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserServiceImplTest {

	private final String SECRET_KEY = "My_S3cr3t";

	@Mock
	UserRepository userRepository;

	@Mock
	SecurityService securityServiceAC;

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
		assertEquals(users, result, "No user");
	}

	@Test
	void testSave() throws UnsupportedEncodingException {
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

		//Arrange
		Mockito.doReturn(null).when(userRepository).save(userCaptor.capture());
		Mockito.doReturn("encryptPasswordString").when(securityServiceAC).encryptPassword("pass", this.SECRET_KEY);

		//Act
		sut.save("pseudo", "pass");

		//Assert
		Mockito.verify(userRepository, Mockito.times(1)).save(userCaptor.capture());
		Mockito.verify(securityServiceAC, Mockito.times(1)).encryptPassword("pass", SECRET_KEY);
		assertEquals(userCaptor.getValue().getPseudo(), "pseudo");
		assertEquals(userCaptor.getValue().getPassword(), "encryptPasswordString");
		//assertThrow(Exception.class, () -> { sut.maMethode() });
	}


	@Captor // Si on met l'annotation, no need .forClass(User.class)
	ArgumentCaptor<User> userAC;

	@Test //PAS FINI
	void testLogin() {
		//Arrange //no need to mock user car l'objet est simple
		User user1 = new User();
		user1.setPseudo("myPseudo");
		user1.setPassword("myPass");

		Mockito.doReturn(Optional.of(user1))
						.when(userRepository)
						.findById("myPseudo");

		Mockito.doReturn("decryptPasswordString")
						.when(securityServiceAC)
						.decryptPassword(this.SECRET_KEY, "myPass");

		//on mock que ce qui est dans la classe mais qui crée externally
		// (donc pas .login() par exemple puisqu'il fait partie de la classe qu'on teste)
//		Mockito.verify(userRepository, Mockito.times(1)).
		//TODO Faire la suite assertEquals();
	}

	@Test
	void testFind() {
		//Arrange
		User user1 = Mockito.mock(User.class);
		//Mockito.when(user1.getPseudo()).thenReturn("Bob");
		Mockito.doReturn(Optional.of(user1)).when(userRepository).findById("Bob");

		//Act
		User user2 = sut.find("Bob");

		//Assert
		Mockito.verify(userRepository, Mockito.times((1))).findById("Bob"); //Verifie si une méthode a bien été appelée
		assertEquals(user1, user2, "User 1 ≠ User 2");
	}

	@Test
	void testFindNull() {
		//Arrange
		Mockito.doReturn(Optional.empty()).when(userRepository).findById(null);

		//Act
		User user2 = sut.find(null);

		//Assert
		Mockito.verify(userRepository, Mockito.times((1))).findById(null); //Verifie si une méthode a bien été appelée
		assertEquals(null, user2, "User 1 ≠ User 2");
	}

}
