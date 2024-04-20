package lazarbosnjak.kucniBudzet.controller;


import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lazarbosnjak.kucniBudzet.enumeration.UserRole;
import lazarbosnjak.kucniBudzet.model.User;
import lazarbosnjak.kucniBudzet.model.SupportToken;
import lazarbosnjak.kucniBudzet.model.dto.AuthUserDTO;
import lazarbosnjak.kucniBudzet.model.dto.UserPwdRecoveryDTO;
import lazarbosnjak.kucniBudzet.model.dto.UserRegistrationDTO;
import lazarbosnjak.kucniBudzet.security.JwtService;
import lazarbosnjak.kucniBudzet.service.UserService;
import lazarbosnjak.kucniBudzet.service.EmailService;
import lazarbosnjak.kucniBudzet.service.SupportTokenService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/korisnici")
@RequiredArgsConstructor
public class UserController {
	
	private final UserDetailsService userDetailsService;
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final SupportTokenService supportTokenService;
	private final EmailService emailService;
	
	@Value("${frontend.url}")
	String frontendUrl;
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@RequestBody @Validated UserRegistrationDTO dto) throws MessagingException {
		
		if (!dto.getPassword().equals(dto.getRepeatedPassword())) return ResponseEntity.badRequest().body("Lozinke se ne podudaraju");
		
		User createdUser = userService.register(dto);
		SupportToken token = supportTokenService.generateRegistrationToken(createdUser);
		
		String url = frontendUrl 
				+ "/user-activation?u=" + createdUser.getId().toString()
				+ "&t=" + token.getId().toString();

		emailService.sendEmailUserActivation(createdUser.getEmail(), url);
		
		
		return ResponseEntity.accepted().build();
	}
	
	@PutMapping("/register")
	public ResponseEntity<?> activateRegistration(
			@RequestParam(name = "u") UUID userId,
			@RequestParam(name = "t") UUID tokenId) {
		
		try {
			supportTokenService.validateRegistrationToken(tokenId);
			userService.activateRegistration(userId);
		} catch (Exception ex) { return ResponseEntity.badRequest().body(ex.getMessage()); }
		
		return ResponseEntity.accepted().build();
		
	}
	
	@PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticateUser(@RequestBody AuthUserDTO dto) {
		 Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
		 if (authentication.isAuthenticated()) {
			 UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
			 System.out.println(jwtService.generateToken(userDetails));
			 return ResponseEntity.ok(jwtService.generateToken(userDetails));
		 } else {
			 throw new UsernameNotFoundException("invalid user request..!!");
		 }
	}
	
	@GetMapping(value = "/forgot-password")
	public ResponseEntity<?> generateForgotPasswordToken(@RequestParam String email) throws MessagingException {
		
		supportTokenService.generateForgotPasswordToken(email);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/forgot-password")
	public ResponseEntity<?> validateForgotPasswordToken(@RequestParam(name = "t") UUID tokenId) {
		supportTokenService.validateForgotPasswordToken(tokenId);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(value = "/forgot-password/new-password", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> forgotPasswordNewPassword(
								@RequestBody UserPwdRecoveryDTO dto,
								@RequestParam(name = "t") UUID tokenId) {

		if (!dto.getPassword().equals(dto.getRepeatedPassword())) {
			return ResponseEntity.badRequest().body("Lozinke se ne podudaraju");
		}
		
		try { supportTokenService.forgotPasswordNewPassword(dto, tokenId); } 
		catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		
		
		return ResponseEntity.accepted().build();
	}
	
	@GetMapping(value = "/test-sufa")
	public ResponseEntity<?> testSufa() {
		
		// GET: Dobavljanje HTML-a SUF stranice
		RestTemplate getHtmlStranice = new RestTemplate();
		
		// Po default-u, Spring enkoduje svaki uri, a posto je nas uri vec enkodovan, dolazi do duplog enkodovanja i statusa 400, tako da onesposobljavamo ovo ponasanje
		DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
	    defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
	    getHtmlStranice.setUriTemplateHandler(defaultUriBuilderFactory);
		
		String urlRacuna = "https://suf.purs.gov.rs/v/?vl=AzNHSlVLWTdSM0dKVUtZN1IUpwEAEqcBAGD3kgEAAAAAAAABjqg0IjMAAACFwYlwPXJXCtKZYDacSurJWQAnPuT1P7T%2FeKwn71Nl2KFrBQcts1hx%2BK2pOzKsA16icjrhpuSKb7rqw8CXeod8OJTL7V1eNdAP8ZNeQMqO0RgEJgSnk8KpQgs0nqyAHyaO5vkgg5FuUE5H7oji8sEso3rQuaVnWMank74pTGmBW2ugt9VFBRyn1j%2BPg2FauHzs6l2whRdHpy3spobRCB%2FQmx3pxyT7jjgAH9G7YytESbhY9vxowBzweU6%2Bcf%2BkY7W7vzYsgHujWpThh8dG34asV2GPyBFt7Z0lTXoz%2B%2BQwuflzJoNb50t98qw2cLx9gUztgfGYsJQS9P6RR2rSdaK%2FiMo6lUBnsJo8YuCKMB0oFFD%2BWGykhi3gO0WiOka6fLcWceywr%2BiLKXlqPm7tjuh71g3Jd29Sf%2B8FLzY2UeOYLcm8CFZBqT0Oq1BQDeepopW%2BJ4q3wkq%2FY59EcFlE6MKlp%2BpHUfzlSAYRE4HaDoFd5BXzzDtFqhjZ1BqtOmFgaWfohUYrrFU%2Fs8y35YRrSQ%2FUarKD1%2FGrroTtV8X8%2BKH0FcyrP3x%2Bodm%2BO6ssfBvP2YSG8V84kA98IY2fg5gpXSyZAmVOsMVQQJb6NpTlFSeZHUoNLm8mFJknt9RoDPNjRv0vbvJuN8qbqqMI%2F0M5vHRdht1W2ulZMBSuM4ogkXqTRlZXOfEGh9Ue%2FH3KUHJEL4A%3D";
		String racunHtml = getHtmlStranice.getForObject(urlRacuna, String.class);

		
		// Uzimanje potrebnih podataka iz HTML-a
		Pattern tokenPattern = Pattern.compile("viewModel\\.Token\\('([^']+)'\\)");
		Matcher tokenMatcher = tokenPattern.matcher(racunHtml);
		Pattern invoiceNumberPattern = Pattern.compile("viewModel\\.InvoiceNumber\\('([^']+)'\\)");
		Matcher invoiceNumberMatcher = invoiceNumberPattern.matcher(racunHtml);
		
		String token = "";
		String invoiceNumber = "";
		if (tokenMatcher.find() && invoiceNumberMatcher.find()) {
			token = tokenMatcher.group(1);
			System.out.println("token: " + token);
			invoiceNumber = invoiceNumberMatcher.group(1);
			System.out.println("invoiceNumber" + invoiceNumber);
		}
		
		Document doc = Jsoup.parse(racunHtml);
		
		
		// POST: zahtev za dobijanje proizvoda
		RestTemplate getAllProizvodi = new RestTemplate();
		String urlEndpointProizvoda = "https://suf.purs.gov.rs//specifications";
		
		HttpHeaders proizvodiHeaders = new HttpHeaders();
		proizvodiHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> proizvodiBodyParams = new LinkedMultiValueMap<String, String>();
		proizvodiBodyParams.add("invoiceNumber", invoiceNumber);
		proizvodiBodyParams.add("token", token);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(proizvodiBodyParams, proizvodiHeaders);
		ResponseEntity<String> response = getAllProizvodi.postForEntity(urlEndpointProizvoda, request, String.class);
		
		System.out.println(response);
		
		return response;
		
		
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/test")
	public ResponseEntity<?> testing(Authentication auth) {
		
		return ResponseEntity.ok(auth.getPrincipal());
	}
}
