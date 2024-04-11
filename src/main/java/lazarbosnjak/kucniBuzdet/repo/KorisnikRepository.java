package lazarbosnjak.kucniBuzdet.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lazarbosnjak.kucniBuzdet.model.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, UUID>{

	Optional<Korisnik> findFirstByEmail(String eMail);
	
}
