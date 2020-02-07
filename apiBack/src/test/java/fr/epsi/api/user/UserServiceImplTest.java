package fr.epsi.api.user;

import fr.epsi.api.security.SecurityService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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
        void testFindUser() {
        //Arrange
        User user = new User();
        user.setPseudo("Test");
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());
        //Act
        User result = sut.find("Test");

        //Assert
        Assertions.assertEquals(user, result, "User found");
        }
        @Test
        void testLogin(){
        //Arrange
        User user = new User();
        user.setPseudo("test");
        user.setPassword("pwd");
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());

        Mockito.doReturn("password")
                .when(securityService)
                .decryptPassword(ArgumentMatchers.eq("pwd"), anyString());

        //Act
        sut.login("test", "password");
        //Assert
        Mockito.verify(securityService).decryptPassword(ArgumentMatchers.eq("pwd"), anyString());

        }
        
        @Test
        void testSave(){
            //Arrange
            //Act
            //Assert
        }
        
        @Test
        void testFind(){
            //Arrange
            //Act
            //Assert
        }

}
