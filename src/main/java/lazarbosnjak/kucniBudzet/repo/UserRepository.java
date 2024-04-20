package lazarbosnjak.kucniBudzet.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lazarbosnjak.kucniBudzet.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

	Optional<User> findFirstByEmail(String email);
	
}
