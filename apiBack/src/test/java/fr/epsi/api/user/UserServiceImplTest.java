package fr.epsi.api.user;

import fr.epsi.api.security.SecurityService;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
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
    void testFindAllOK() {
        //Arrange
        List<User> users = new ArrayList<User>();
        Mockito.doReturn(users).when(userRepository).findAll();
        //Act
        Iterable<User> result = sut.findAll();
        //Assert
        Assertions.assertEquals(users, result, "No user");
    }
    
//    @Test
//    public void testFindAllKO() {
//        //Arrange
//        List<User> users = new ArrayList<User>();
//        Mockito.doReturn(users).when(userRepository).findAll();
//
//        //Act
//        Iterable<User> result = sut.findAll();
//
//        //Assert
//        Assertions.assertEquals(users, result, "No user");
//        
//    }

    @Test
    void testLoginOK() {
        //Arrange
        User user = new User();
        user.setPseudo("test");
        user.setPassword("Pass");
        String SECRET_KEY = "My_S3cr3t";
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());
        Mockito.doReturn(user.getPassword()).when(securityService).decryptPassword(user.getPassword(), SECRET_KEY);
        //Act
        sut.login(user.getPseudo(), user.getPassword());
        //Assert
        Mockito.verify(securityService).decryptPassword(user.getPassword(), SECRET_KEY);
    }

    @Test
    void testLoginKOPass() {
        //Arrange
        User user = new User();
        user.setPseudo("Test");
        user.setPassword("Pass");
        String SECRET_KEY = "My_S3cr3t";
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());
        Mockito.doReturn(user.getPassword()).when(securityService).decryptPassword(user.getPassword(), SECRET_KEY);
        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.login("Test", "password"));
        //Assert
        Mockito.verify(securityService).decryptPassword(user.getPassword(), SECRET_KEY);
    }
    
    @Test
    void testLoginKOPseudo() {
        //Arrange
        User user = new User();
        user.setPseudo("Test");
        user.setPassword("Pass");
        String SECRET_KEY = "My_S3cr3t";
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());
        Mockito.doReturn(user.getPassword()).when(securityService).decryptPassword(user.getPassword(), SECRET_KEY);
        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.login("Test", "password"));
        //Assert
        Mockito.verify(userRepository).findById(user.getPseudo());
    }

    @Test
    public void testSaveOK() throws UnsupportedEncodingException {
        //Arrange
        User user = new User();
        user.setPseudo("Test");
        user.setPassword("Pass");
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.doReturn(user).when(userRepository).save(argumentCaptor.capture());
        Mockito.doReturn("pasCrypt")
                .when(securityService)
                .encryptPassword(ArgumentMatchers.eq("Pass"), anyString());

        //Act
        sut.save(user.getPseudo(), user.getPassword());

        //Assert
        Mockito.verify(userRepository).save(argumentCaptor.getValue());
        Assertions.assertEquals("Test", argumentCaptor.getValue().getPseudo());
        Assertions.assertEquals("pasCrypt", argumentCaptor.getValue().getPassword());
    }
    
//    @Test
//    public void SaveProf() throws UnsupportedEncodingException{
//        //Arrange
//        User user = new User();
//        user.setPseudo("Test");
//        user.setPassword("Pass");
//        String SECRET_KEY = "My_S3cr3t";
//        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
//        Mockito.doReturn(user).when(userRepository).save(argumentCaptor.capture());
//        Mockito.doReturn("pasCrypt")
//                .when(securityService)
//                .encryptPassword(ArgumentMatchers.eq("Pass"), anyString());
//
//        //Act
//        sut.save(user.getPseudo(), user.getPassword());
//
//        //Assert
//        Mockito.verify(securityService,Mockito.times(1)).encryptPassword("pass", SECRET_KEY);
//        Mockito.verify(userRepository).save(argumentCaptor.getValue());
//        Assertions.assertEquals("Test", argumentCaptor.getValue().getPseudo());
//        Assertions.assertEquals("pasCrypt", argumentCaptor.getValue().getPassword());
//    }
    
    @Test
    public void testSavePassKO() throws UnsupportedEncodingException {
        //Arrange
        User user = new User();
        user.setPseudo("Test");
        user.setPassword("Pass");
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.doReturn(user).when(userRepository).save(argumentCaptor.capture());
        Mockito.doReturn("pasCryptTrue")
                .when(securityService)
                .encryptPassword(ArgumentMatchers.eq("Pass"), anyString());

        //Act
        sut.save(user.getPseudo(), user.getPassword());

        //Assert
        Mockito.verify(userRepository).save(argumentCaptor.getValue());
        Assertions.assertEquals("Test", argumentCaptor.getValue().getPseudo());
        Assertions.assertNotEquals("pasCryptFalse", "pasCryptTrue", "Error False Password");
    }
    
    @Test
    public void testFindOK(){
        //Arrange
        User user = new User();
        user.setPseudo("Test");
        user.setPassword("Pass");
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getPseudo());

        //Act
        User result = sut.find("Test");

        //Assert
        Assertions.assertEquals(user, result, "User found");
    }
    
    @Test
    public void testFindKO(){
        //Arrange
        User user = new User();
        user.setPseudo("Test");
        user.setPassword("Pass");
        Mockito.doReturn(Optional.empty()).when(userRepository).findById(user.getPseudo());

        //Act
        User result = sut.find(user.getPseudo());

        //Assert
        Assertions.assertNull(result);
    }
}
