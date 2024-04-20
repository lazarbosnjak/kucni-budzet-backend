package lazarbosnjak.kucniBudzet.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import lazarbosnjak.kucniBudzet.enumeration.UserRole;
import lazarbosnjak.kucniBudzet.model.SupportToken;
import lazarbosnjak.kucniBudzet.model.User;
import lazarbosnjak.kucniBudzet.model.dto.UserRegistrationDTO;
import lazarbosnjak.kucniBudzet.repo.UserRepository;
import lazarbosnjak.kucniBudzet.service.EmailService;
import lazarbosnjak.kucniBudzet.service.SupportTokenService;
import lazarbosnjak.kucniBudzet.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
//	private final SupportTokenService supportTokenService;
	private final EmailService emailService;
	
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findFirstByEmail(email);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User register(UserRegistrationDTO dto) throws MessagingException {
		
		User random = findByEmail(dto.getEmail()).orElse(null);
		System.out.println("===========================");
		System.out.println(random);
		
		if (random != null) {
			throw new DuplicateKeyException("Korisnik sa ovom E-mail adresom već postoji");
		}
		User user = User.builder()
						.fullName(dto.getFullName())
						.email(dto.getEmail())
						.password(passwordEncoder.encode(dto.getPassword()))
						.enabled(false)
						.role(UserRole.USER)
						.build();
		
		return save(user);
		
//		SupportToken token = supportTokenService.generateRegistrationToken(savedUser);
//		
//		String url = frontendUrl 
//					+ "/user-activation?u=" + savedUser.getId().toString()
//					+ "&t=" + token.getId().toString();
//		
//		emailService.sendEmailUserActivation(savedUser.getEmail(), url);
//		
//		save(user);
		
	}

	@Override
	public void activateRegistration(UUID userId) {
		
		User user = userRepository.findById(userId)
						.orElseThrow(() -> new UsernameNotFoundException("Korisnik sa ovim ID-om ne postoji"));
	
		user.setEnabled(true);
		save(user);
		
	}

}
