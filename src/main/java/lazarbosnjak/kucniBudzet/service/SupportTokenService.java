package lazarbosnjak.kucniBudzet.service;

import java.util.Optional;
import java.util.UUID;

import jakarta.mail.MessagingException;
import lazarbosnjak.kucniBudzet.model.SupportToken;
import lazarbosnjak.kucniBudzet.model.User;
import lazarbosnjak.kucniBudzet.model.dto.UserPwdRecoveryDTO;

public interface SupportTokenService {

	public Optional<SupportToken> findById(UUID id);
	
	public SupportToken generateForgotPasswordToken(String email) throws MessagingException;
	
	public void validateForgotPasswordToken(UUID id);
	
	public void forgotPasswordNewPassword(UserPwdRecoveryDTO dto, UUID tokenId);
	
	public SupportToken generateRegistrationToken(User user);

	public void validateRegistrationToken(UUID tokenId);
	
}
