package lazarbosnjak.kucniBudzet.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lazarbosnjak.kucniBudzet.model.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, UUID>{

	Optional<Korisnik> findFirstByEmail(String email);
	
}
