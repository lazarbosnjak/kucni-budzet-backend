package lazarbosnjak.kucniBudzet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lazarbosnjak.kucniBudzet.service.KorisnikService;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private KorisnikService korisnikService;
	
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		return korisnikService.findByEmail(userEmail)
					.orElseThrow(() -> new UsernameNotFoundException("E-mail nije pronadjen"));
	}

}
