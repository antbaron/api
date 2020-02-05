package fr.epsi.api.user;

import java.io.UnsupportedEncodingException;

public interface UserService {

	Iterable<User> findAll();

	void login(String pseudo, String password);

	void save(String pseudo, String password) throws UnsupportedEncodingException;

	User find(String pseudo) throws Exception;
	
}
