package lazarbosnjak.kucniBuzdet.service;

import java.util.Optional;

import lazarbosnjak.kucniBuzdet.model.Korisnik;

public interface KorisnikService {

	Optional<Korisnik> findByEmail(String eMail);
	
	Korisnik save(Korisnik korisnik);
	
}
