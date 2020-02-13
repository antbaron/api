package fr.epsi.api.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

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

import fr.epsi.api.security.SecurityService;
import java.util.NoSuchElementException;
import javax.persistence.EntityNotFoundException;
import net.bytebuddy.asm.Advice.Argument;
import org.mockito.ArgumentMatchers;
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

        User user = new User();
        user.setPseudo("test123");
        user.setPassword("test321");

        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());
        Mockito.doReturn("passwd")
                .when(securityService)
                .decryptPassword(ArgumentMatchers.eq("test321"), anyString());

        sut.login("test123", "passwd");

        Mockito.verify(securityService).decryptPassword(ArgumentMatchers.eq("test321"), anyString());
    }

    @Test
    void testSave() throws UnsupportedEncodingException {

        User useru = new User();

        useru.setPassword("test321");
        useru.setPseudo("test123");

        ArgumentCaptor<User> acu = ArgumentCaptor.forClass(User.class);

        Mockito.doReturn(null).when(userRepository).save(acu.capture());

        Mockito.doReturn("test321").when(securityService).encryptPassword("test321", "My_S3cr3t");

        sut.save("test123", "test321");

        Assertions.assertEquals(useru.getPassword(), acu.getValue().getPassword());

    }

    @Test
    void testFindById() {
        //Arrange
        User useru = new User();

        Optional<User> user = Optional.of(useru);
        Mockito.doReturn(user).when(userRepository).findById("1");

        User result = sut.find("1");
        Assertions.assertEquals(result, useru);

    }

    @Test
    void testSaveException() throws UnsupportedEncodingException {
        User u = new User();
        u.setPseudo("test123");
        u.setPassword("");
        
        
        Mockito.when(securityService.encryptPassword("", "My_S3cr3t")).thenThrow(new UnsupportedEncodingException("Pass vide"));
        try {
            sut.save("test123", "");
            Assertions.fail("Test passant");
        } catch (UnsupportedEncodingException e) {
            Assertions.assertEquals(e.getMessage(), "Pass vide");
        }
        Assertions.assertThrows(UnsupportedEncodingException.class, () -> {
            sut.save("test123", "");
        });
    }

    @Test
    void testFindNoValue() {

        Mockito.doReturn(Optional.empty()).when(userRepository).findById("-1");

        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> sut.find("-1"));
        Assertions.assertEquals("No value present", exception.getMessage());
    }
}
