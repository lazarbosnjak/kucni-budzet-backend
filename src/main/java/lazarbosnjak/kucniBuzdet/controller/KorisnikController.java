package lazarbosnjak.kucniBuzdet.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lazarbosnjak.kucniBuzdet.enumeration.KorisnickaUloga;
import lazarbosnjak.kucniBuzdet.model.Korisnik;
import lazarbosnjak.kucniBuzdet.model.dto.AuthKorisnikDTO;
import lazarbosnjak.kucniBuzdet.model.dto.KorisnikDTO;
import lazarbosnjak.kucniBuzdet.model.dto.KorisnikRegistracijaDTO;
import lazarbosnjak.kucniBuzdet.security.JwtService;
import lazarbosnjak.kucniBuzdet.service.KorisnikService;
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
	
	@PostMapping(value = "/register")
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
