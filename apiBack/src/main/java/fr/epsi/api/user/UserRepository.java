package fr.epsi.api.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
//Ce systeme genere par defaut toutes les requetes du CRUD !!
}
