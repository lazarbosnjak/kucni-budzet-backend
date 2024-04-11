package lazarbosnjak.kucniBuzdet.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lazarbosnjak.kucniBuzdet.enumeration.KorisnickaUloga;
import lazarbosnjak.kucniBuzdet.model.Korisnik;
import lazarbosnjak.kucniBuzdet.security.CustomUserDetails;
import lazarbosnjak.kucniBuzdet.service.KorisnikService;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private KorisnikService korisnikService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Korisnik korisnik = korisnikService.findByEmail(username).orElse(null);
		if (korisnik == null) throw new UsernameNotFoundException(String.format("Korisnik sa ovim Email-om '%s' ne postorji.", username));
		else {
			return new CustomUserDetails(korisnik);
		}
	}

}
