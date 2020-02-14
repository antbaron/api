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
    void testLogin() {
        //Arrange
        User user = new User();
        user.setPseudo("mougui");
        user.setPassword("157268426");
        Optional<User> userO = Optional.of(user);
        Mockito.doReturn(userO).when(userRepository).findById(ArgumentMatchers.eq("mougui"));
        Mockito.doReturn("password").when(securityService).decryptPassword(ArgumentMatchers.eq("157268426"), anyString());

        //Act
        sut.login("mougui", "password");

        //Assert
        Mockito.verify(securityService).decryptPassword(ArgumentMatchers.eq("157268426"), anyString());
    }

    @Test
    void testSave() throws UnsupportedEncodingException {
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.doReturn("157268426").when(securityService).encryptPassword(anyString(), anyString());
        Mockito.doReturn(null).when(userRepository).save(userCaptor.capture());
        sut.save("mougui", "157268426");
        Mockito.verify(securityService,Mockito.times(1)).encryptPassword("157268426", "My_S3cr3t");
        Mockito.verify(userRepository).save(userCaptor.getValue());
        User userSaved = userCaptor.getValue();
        User user = new User();
        user.setPseudo("mougui");
        user.setPassword("157268426");
        Assertions.assertEquals("mougui", userSaved.getPseudo());
        Assertions.assertEquals("157268426", userSaved.getPassword());
    }
    
    @Test
    void testFind() {
        //Arrange
        User user = new User();
        user.setPseudo("mougui");
        user.setPassword("157268426");
        Optional<User> userO = Optional.of(user);
        Mockito.doReturn(userO).when(userRepository).findById(ArgumentMatchers.eq("mougui"));
        //Assert
        User userReturned = sut.find("mougui");
        //Act
        Assertions.assertEquals(user, userReturned);
    }
}
