package fr.epsi.api.user;

import fr.epsi.api.security.SecurityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void testFindAll() {
        //Arrange
        List<User> users = new ArrayList<>();
        Mockito.doReturn(users).when(userRepository).findAll();

        //Act
        Iterable<User> result = sut.findAll();

        //Assert
        Assertions.assertEquals(users, result, "No user");
    }

    @Test
    public void testFind_userExist() {
        //Arrange
        User user = new User();
        user.setPseudo("Test");
        user.setPassword("pas123");
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());

        //Act
        User result = sut.find("Test");

        //Assert
        Assertions.assertEquals(user, result, "User found");
    }

    @Test
    public void testFind_userNotExist() {
        //Arrange
        Mockito.doReturn(Optional.empty()).when(userRepository).findById("Test");

        //Act
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> sut.find("Test"));

        //Assert
        Assertions.assertEquals(exception.getMessage(), "User with pseudo Test not found");
    }

    @Test
    public void testSave() throws UnsupportedEncodingException {
        //Arrange
        User user = new User();
        user.setPseudo("Test");
        user.setPassword("pas123");
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.doReturn(user).when(userRepository).save(argumentCaptor.capture());
        Mockito.doReturn("pasCrypt")
                .when(securityService)
                .encryptPassword(ArgumentMatchers.eq("pas123"), anyString());

        //Act
        sut.save(user.getPseudo(), user.getPassword());

        //Assert
        Mockito.verify(userRepository).save(argumentCaptor.getValue());
        Assertions.assertEquals("Test", argumentCaptor.getValue().getPseudo());
        Assertions.assertEquals("pasCrypt", argumentCaptor.getValue().getPassword());
    }

    @Test
    public void testLoginOk() {
        //Arrange
        User user = new User();
        user.setPseudo("Test");
        user.setPassword("pasCrypt");
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());

        Mockito.doReturn("pas123")
                .when(securityService)
                .decryptPassword(ArgumentMatchers.eq("pasCrypt"), anyString());

        //Act
        sut.login("Test", "pas123");

        //Assert
        Mockito.verify(securityService).decryptPassword(ArgumentMatchers.eq("pasCrypt"), anyString());
    }

    @Test
    public void testLoginKo() {
        //Arrange
        User user = new User();
        user.setPseudo("Test");
        user.setPassword("pasCrypt");
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());

        Mockito.doReturn("pas123")
                .when(securityService)
                .decryptPassword(ArgumentMatchers.eq("pasCrypt"), anyString());

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.login("Test", "password"));

        //Assert
        Mockito.verify(securityService).decryptPassword(ArgumentMatchers.eq("pasCrypt"), anyString());
    }

}
