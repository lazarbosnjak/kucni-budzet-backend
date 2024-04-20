package lazarbosnjak.kucniBudzet.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lazarbosnjak.kucniBudzet.model.User;
import lazarbosnjak.kucniBudzet.enumeration.SupportTokenType;
import lazarbosnjak.kucniBudzet.model.SupportToken;
import lazarbosnjak.kucniBudzet.model.dto.UserPwdRecoveryDTO;
import lazarbosnjak.kucniBudzet.repo.SupportTokenRepository;
import lazarbosnjak.kucniBudzet.service.EmailService;
import lazarbosnjak.kucniBudzet.service.UserService;
import lazarbosnjak.kucniBudzet.service.SupportTokenService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupportTokenServiceImpl implements SupportTokenService{

	private final SupportTokenRepository supportTokenRepository;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	
	@Value("${frontend.url}")
	String frontendUrl;
	
	@Override
	public Optional<SupportToken> findById(UUID id) {
		return supportTokenRepository.findById(id);
	}
	
	@Override
	public SupportToken generateForgotPasswordToken(String email) throws MessagingException {
		User user = userService.findByEmail(email)
								.orElseThrow(() -> new UsernameNotFoundException("Korisnik sa ovim email-om nije pronadjen"));
		SupportToken token = SupportToken.builder()
								.createdAt(LocalDateTime.now())
								.expiresAt(LocalDateTime.now().plusMinutes(5))
								.used(false)
								.user(user)
								.type(SupportTokenType.PWD_RECOVERY)
								.build();
		SupportToken savedToken = supportTokenRepository.save(token);
		
		String url = "http://localhost:3000/forgot-password/password-reset/" + savedToken.getId().toString();
		
		emailService.sendEmailPwdRecovery(user.getEmail(), url);
		
		return savedToken;
	}


	@Override
	public void validateForgotPasswordToken(UUID id) {
		SupportToken token = supportTokenRepository.findById(id)
								.orElseThrow(() -> new EntityNotFoundException("Ovaj token ne postoji"));
		
		if (LocalDateTime.now().isAfter(token.getExpiresAt())) {
			throw new RuntimeException("Token je istekao, pokusajte ponovo");
		}
		
		token.setValidatedAt(LocalDateTime.now());
		supportTokenRepository.save(token);
	}

	@Override
	public void forgotPasswordNewPassword(UserPwdRecoveryDTO dto, UUID tokenId) {
		
		SupportToken token = supportTokenRepository.findById(tokenId)
									.orElseThrow(() -> new EntityNotFoundException("Ovaj token ne postoji"));
		if (token.getValidatedAt() == null) {
			throw new RuntimeException("Ovaj token nije validan");
		}
		
		if (token.isUsed()) {
			throw new RuntimeException("Ovaj token je vec iskoricen, posaljite ponovo zahtev");
		}
		User user = token.getUser();
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		userService.save(user);
		token.setUsed(true);
		supportTokenRepository.save(token);
		
	}

	
	@Override
	public SupportToken generateRegistrationToken(User user) {
		SupportToken token = SupportToken.builder()
										.createdAt(LocalDateTime.now())
										.expiresAt(LocalDateTime.now().plusMinutes(30))
										.used(false)
										.user(user)
										.type(SupportTokenType.REGISTRATION)
										.build();
		
		return supportTokenRepository.save(token);
	}

	@Override
	public void validateRegistrationToken(UUID tokenId) {
		SupportToken token = supportTokenRepository.findById(tokenId)
				.orElseThrow(() -> new EntityNotFoundException("Ovaj token ne postoji"));

		if (LocalDateTime.now().isAfter(token.getExpiresAt())) {
			throw new RuntimeException("Token je istekao, pokusajte ponovo");
		}	
		if (token.isUsed()) {
			System.out.println("??????????????????");
			throw new RuntimeException("Ovaj token je vec iskoricen");
		}
			
		
		token.setValidatedAt(LocalDateTime.now());
		token.setUsed(true);
		
		supportTokenRepository.save(token);
	}


	
}
