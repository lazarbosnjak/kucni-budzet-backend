package lazarbosnjak.kucniBudzet.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import lazarbosnjak.kucniBudzet.model.SupportToken;

public interface SupportTokenRepository extends JpaRepository<SupportToken, UUID>{

	Optional<SupportToken> findById(UUID id);
	
}
