package fr.epsi.api.user;

import fr.epsi.api.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String SECRET_KEY = "My_S3cr3t";

    private UserRepository userRepository;

    private SecurityService securityService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void login(String pseudo, String password) {
        Optional<User> user = userRepository.findById(pseudo);
        Assert.isTrue(
                user.isPresent()
                        && password.equals(securityService.decryptPassword(user.get().getPassword(), SECRET_KEY)),
                "Not connected");
    }

    @Override
    public void save(String pseudo, String password) throws UnsupportedEncodingException {
        User user = new User();
        user.setPseudo(pseudo);
        user.setPassword(securityService.encryptPassword(password, SECRET_KEY));
        userRepository.save(user);
    }

    @Override
    public User find(String pseudo) {
        Optional<User> optionalUser = userRepository.findById(pseudo);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new EntityNotFoundException("User with pseudo " + pseudo + " not found");
    }

}
