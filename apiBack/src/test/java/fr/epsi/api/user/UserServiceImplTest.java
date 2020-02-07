package fr.epsi.api.user;

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
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    public UserServiceImpl sut;

    @Test
    void testFindAll() {
        //Arrange
        List<User> users = new ArrayList<User>();
//        Lorsqu'on appelle la methode findAll, 
//      on retournera le contenu de la vaiable users et pas le resultat de la bdd
        Mockito.doReturn(users).when(userRepository).findAll();
        //Act
        Iterable<User> result = sut.findAll();
        //Assert
        Assertions.assertEquals(users, result, "No user - msg si PAS OK");
    }

    @Test
    void testFindAll2() {
        //Arrange
        List<User> users = new ArrayList<User>();
        Mockito.doReturn(users).when(userRepository).findAll();
        //Act
        Iterable<User> result = sut.findAll();
        //Assert
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

        Assertions.assertEquals(users, result, "No user");
    }
    
    @Test
    void testSaveUser(){
//        se servir du ArgumentCaptor car on a pas la main dessus
    }
}
