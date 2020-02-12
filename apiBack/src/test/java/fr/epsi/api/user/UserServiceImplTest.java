package fr.epsi.api.user;

import fr.epsi.api.security.SecurityService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
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
        
//        @Test
//	void testLogin() {		
//		//Arrange
//		List<User> users = new ArrayList<User>();
//		Mockito.doReturn(users).when(userRepository).findAll();
//		//Act
//		Iterable<User> result = sut.findAll();
//		//Assert
//		Assertions.assertEquals(users, result, "No user");
//                
//                void login(pseudo, password)
//	}

        
        
        @Test
        void testSave(){
            
            ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
            //Arrange
            Mockito.doReturn(null).when(userRepository).save(ac.capture());
            //Mockito.doReturn(null).when(securityService).save(ac.capture());
            
            //Act
            sut.save("pseudo","password");
            
            //Assert
            Mockito.verify(sut).save("pseudo", "password");
            Assertions.assertEquals(ac.getValue().getPseudo(), "pseudo");
            Assertions.assertEquals(ac.getValue().getPassword(), "password");
        }
}
