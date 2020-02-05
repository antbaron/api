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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserServiceImplTest {

	@Mock
	UserRepository userRepository;
	
        @Mock
        SecurityService securityService;
        
        //@Mock
        //Optional opt;
        
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
	void testLogin() {
            
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
	void testLoginFail() {
            Assertions.assertThrows(Exception.class,()->{sut.login("dqae", "ded");});
	}
        
        
        @Test
	void testSave() {	
        
            ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
            Mockito.verify(sut).save("login", "password");
            Mockito.doReturn("ok").when(userRepository).save(ac.capture());
                    
            sut.save("login", "password");
            //ac.getValue();
		Assertions.assertEquals(users, result, "No user");
	}
        
        @Test
	void testFind() {		
		//Arrange
		User user = Mockito.spy(new User());
                String pseudo = "test";
                user.setPseudo(pseudo);
		Mockito.doReturn(Optional.of(user)).when(userRepository).findById(pseudo).get();

		//Act
		User result = sut.find(pseudo);
		//Assert
		Assertions.assertEquals(user, result, "User test returned");
	}
}
