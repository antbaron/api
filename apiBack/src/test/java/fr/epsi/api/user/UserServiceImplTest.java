package fr.epsi.api.user;

import fr.epsi.api.security.SecurityService;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testlogin() {
        //Arrange
        User user = new User();
        String pseudo = "123";
        String password = "password";
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(pseudo);
        Mockito.doReturn(password).when(securityService).decryptPassword(user.getPassword(), "My_S3cr3t");
        //ACT
        sut.login(pseudo, password);

        Mockito.verify(userRepository, Mockito.times(1)).findById(pseudo);
        Mockito.verify(securityService, Mockito.times(1)).decryptPassword(user.getPassword(), "My_S3cr3t");

    }


    @Test
    void testFindByID() {
        User user = new User();
        Mockito.doReturn(user).when(userRepository).findById("1");
        User result = sut.find("1");
        Assertions.assertEquals(user, result);

    }

    @Test
    void testSave() throws UnsupportedEncodingException {
        // Arrange
        ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
        Mockito.doReturn("PassEncrypt").when(securityService).encryptPassword("pass", "My_S3cr3t");
        Mockito.doReturn(null).when(userRepository).save(ac.capture());
        
        // Act
        sut.save("pseudo", "pass");
        
        //Assert
        Mockito.verify(securityService, Mockito.times(1)).encryptPassword("pass", "My_S3cr3t");
        Mockito.verify(userRepository).save(ac.getValue());
        assertEquals(ac.getValue().getPseudo(), "pseudo");
        
    }

}
