package fr.epsi.api.user;

import fr.epsi.api.security.SecurityService;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.anyString;
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
        
        //Info Professeur:
        //  Mockito.verify(UserRepository, Mockito.times(1)).findAll()
        //  ArgumentCaptor<Integer> ac = ArgumentCaptor.forClass(Integer.class)
        //  Mockito.doReturn(...).when(mock).findById(ac.capture())
        //  ac.getValue()
	
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
        public void test_user_exist() {
            //Arrange
            User user = new User();
            user.setPseudo("allan");
            user.setPassword("bousquet");
            Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());

            //Act
            User result = sut.find("allan");

            //Assert
            Assertions.assertEquals(user, result, "Utilisateur existant");
        }
        
        @Test
        public void test_user_not_exist() {
            //Arrange
            Mockito.doReturn(Optional.empty()).when(userRepository).findById("bousquet");

            //Act
            Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> sut.find("bousquet"));

            //Assert
            Assertions.assertEquals("L'utilisateur bousquet n'existe pas", exception.getMessage());
        }
        
        @Test
	void test_save() throws UnsupportedEncodingException {
            //Arrange
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            User user = new User(); 
            user.setPseudo("allan");
            user.setPassword("bousquet");

            //Act
            Mockito.doReturn("save").when(securityService).encryptPassword(anyString(),anyString());
            Mockito.doReturn(null).when(userRepository).save(userCaptor.capture());
            User userSaved = userCaptor.getValue();
            sut.save("allan","bousquet");
            
            //
            Assertions.assertEquals("allan",userSaved.getPseudo());
            Assertions.assertEquals("bousquet",userSaved.getPassword());
	}
        
        
        
}
