package lazarbosnjak.kucniBudzet.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lazarbosnjak.kucniBudzet.enumeration.KorisnickaUloga;
import lazarbosnjak.kucniBudzet.model.Korisnik;
import lazarbosnjak.kucniBudzet.model.dto.AuthKorisnikDTO;
import lazarbosnjak.kucniBudzet.model.dto.KorisnikDTO;
import lazarbosnjak.kucniBudzet.model.dto.KorisnikRegistracijaDTO;
import lazarbosnjak.kucniBudzet.security.JwtService;
import lazarbosnjak.kucniBudzet.service.KorisnikService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/korisnici")
@RequiredArgsConstructor
public class KorisnikController {
	
	private final UserDetailsService userDetailsService;
	private final KorisnikService korisnikService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@RequestBody @Validated KorisnikRegistracijaDTO dto) {
		
		if (!dto.getPassword().equals(dto.getPonovljeniPassword())) return ResponseEntity.badRequest().build();
		
		Korisnik korisnk = new Korisnik();
		String encodedPassword = passwordEncoder.encode(dto.getPassword());
		
		korisnk.setIme(dto.getIme());
		korisnk.setPrezime(dto.getPrezime());
		korisnk.setEmail(dto.getEmail());
		korisnk.setLozinka(encodedPassword);
		korisnk.setUloga(KorisnickaUloga.KORISNIK);
		korisnikService.save(korisnk);
		return ResponseEntity.accepted().build();
	}
	
	@PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticateUser(@RequestBody AuthKorisnikDTO dto) {
		 Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
		 if (authentication.isAuthenticated()) {
			 UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
			 System.out.println(jwtService.generateToken(userDetails));
			 return ResponseEntity.ok(jwtService.generateToken(userDetails));
		 } else {
			 throw new UsernameNotFoundException("invalid user request..!!");
		 }
	}
	
}
