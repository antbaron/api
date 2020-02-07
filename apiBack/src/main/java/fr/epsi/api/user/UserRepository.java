package fr.epsi.api.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> 
{
	List<User> findByPassword(String p_password);
	void save(String p_username, String p_pass);
}
