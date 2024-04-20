package lazarbosnjak.kucniBudzet.service;

import java.util.Optional;
import java.util.UUID;

import jakarta.mail.MessagingException;
import lazarbosnjak.kucniBudzet.model.User;
import lazarbosnjak.kucniBudzet.model.dto.UserRegistrationDTO;

public interface UserService {

	Optional<User> findByEmail(String eMail);
	
	User register(UserRegistrationDTO dto) throws MessagingException;
	
	User save(User user);

	void activateRegistration(UUID userId);
	
}
