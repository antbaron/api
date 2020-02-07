package fr.epsi.api.user;

import fr.epsi.api.security.SecurityService;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.Optional.empty;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
        void testFindUserOk() {
        //Arrange
        User user = new User();
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById("test");
        //Act
        User result = sut.find("test");

        //Assert
        Assertions.assertEquals(user, result, "User found");
        }
        
        @Test
        void testFindUserKo() {
        //Arrange
        Mockito.doReturn(null).when(userRepository).findById("test");
        //Act
        User result = sut.find("test");
        //Assert
        Assertions.assertEquals(null, result, "User not found");
        }
        
        @Test
        void testLoginOk(){
        //Arrange
        User user = new User();
        user.setPassword("pwd");
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById("test");
        
        Mockito.doReturn("password")
                .when(securityService)
                .decryptPassword(ArgumentMatchers.eq("pwd"), anyString());

        //Act
        sut.login("test","password");
        //Assert
        Mockito.verify(securityService).decryptPassword(ArgumentMatchers.eq("pwd"), anyString());
        }
        
        @Test
        void testSave() throws UnsupportedEncodingException{
          User user = new User();
          user.setPseudo("test");
          user.setPassword("pwd");

          ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
          Mockito.doReturn(user).when(userRepository).save(argumentCaptor.capture());
          Mockito.doReturn("pwdSecure")
                          .when(securityService)
                          .encryptPassword(ArgumentMatchers.eq("pwd"),anyString());

          sut.save(user.getPseudo(),user.getPassword());

          Mockito.verify(userRepository).save(argumentCaptor.getValue());

          Assertions.assertEquals(argumentCaptor.getValue().getPseudo(),"test");
          Assertions.assertEquals(argumentCaptor.getValue().getPassword(),"pwdSecure");
        }
}
