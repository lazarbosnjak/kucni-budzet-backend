package lazarbosnjak.kucniBudzet.service;

import java.util.Optional;

import lazarbosnjak.kucniBudzet.model.Korisnik;

public interface KorisnikService {

	Optional<Korisnik> findByEmail(String eMail);
	
	Korisnik save(Korisnik korisnik);
	
}
