package fr.epsi.api.user;

import fr.epsi.api.security.SecurityService;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserServiceImplTest {

    // On dispose ainsi d'un "faux" repository. Il donnera les résultats que l'on veut, donc sans interroger la bdd
    @Mock
    UserRepository userRepository;

    @Mock
    SecurityService securityService;

    @InjectMocks
    public UserServiceImpl sut;

    @Test
    void test01FindAllWithNoUsers() {

        //Arrange
        List<User> users = new ArrayList<>();
        //        Lorsqu'on appelle la methode findAll, 
        //      on retournera le contenu de la vaiable users et pas le resultat de la bdd
        //      ici, le tableau de users est mocké et est vide
        Mockito.doReturn(users).when(userRepository).findAll();

        //Act
        Iterable<User> result = sut.findAll();

        //Assert
        Assertions.assertEquals(users, result, "users et result sont différents !");
    }

    @Test
    void test02FindAll() {

        //Arrange
        List<User> users = new ArrayList<>();

        User michel = new User();
        michel.setPseudo("Michel");
        michel.setPassword("admin");

        User daniel = new User();
        michel.setPseudo("Daniel");
        michel.setPassword("admin");

        User thierry = new User();
        michel.setPseudo("Thierry");
        michel.setPassword("admin");

        users.add(michel);
        users.add(daniel);
        users.add(thierry);
        //        Lorsqu'on appelle la methode findAll, 
        //      on retournera le contenu de la vaiable users et pas le resultat de la bdd
        //      ici, le tableau de users est mocké et est vide
        Mockito.doReturn(users).when(userRepository).findAll();

        //Act
        Iterable<User> result = sut.findAll();

        //Assert
        Assertions.assertEquals(users, result, "users et result sont différents !");
    }

    @Test
    void test03FindByPseudo() {
        //Arrange
        // le pseudo est l'id du user !
        User michel = new User();
        michel.setPseudo("Michel");
        michel.setPassword("admin");
        Mockito.doReturn(Optional.of(michel)).when(userRepository).findById(michel.getPseudo());
        //Act
        User result = sut.find(michel.getPseudo());
        //                Pour verifier une fonction void (sans retour):
        //        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        //                Arguments captor:
        //va capturer l'argument passé au mock.(on aurait aussi pu faire 'toEqual' qui compare les champs et PAS les adresses memoire)
        // exemple:
        /*
        ArgumentCaptor<Integer> ac = ArgumentCaptor.forClass(Integer.class);
        Mockito.doReturn(result).when(mock).findById(ac.capture());
        ac.getClass().pseudo == maValeur;
         */

        //Assert
        Assertions.assertEquals(michel, result, "Il y a un souci");
    }

    @Test
    void test04Login() {
        //Arrange
        String SECRET_KEY = "My_S3cr3t";
        User michel = new User();
        michel.setPseudo("Michel");
        michel.setPassword("admin");
        Mockito.doReturn(Optional.of(michel)).when(userRepository).findById("Michel");
        Mockito.doReturn("un truc").when(securityService).decryptPassword("admin", SECRET_KEY);

        //Act
        sut.login("Michel", "un truc");

        //Assert
        Mockito.verify(securityService, Mockito.times(1)).decryptPassword("admin", SECRET_KEY);
    }

    @Test
    void test05SaveUser() throws UnsupportedEncodingException {
        //        se servir du ArgumentCaptor car on a pas la main dessus sur le retour (on teste un void...)
        //Arrange
        User user = new User();
        user.setPseudo("Michel");
        user.setPassword("admin");
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.doReturn(user).when(userRepository).save(argumentCaptor.capture());
        Mockito.doReturn("pasCrypt")
                .when(securityService)
                .encryptPassword(ArgumentMatchers.eq("admin"), anyString());

        //Act
        sut.save(user.getPseudo(), user.getPassword());

        //Assert
        Mockito.verify(userRepository).save(argumentCaptor.getValue());
        Assertions.assertEquals("Michel", argumentCaptor.getValue().getPseudo());
        Assertions.assertEquals("pasCrypt", argumentCaptor.getValue().getPassword());
    }

    @Test
    void test06UserAbsent() {
        
        //Arrange
        User michel = new User();
        michel.setPseudo("Michel");
        michel.setPassword("admin");
        Mockito.doReturn(Optional.empty()).when(userRepository).findById(michel.getPseudo());

        //Act
        User result = sut.find(michel.getPseudo());

        //Assert
        Assertions.assertNull(result);

    }
    
}
