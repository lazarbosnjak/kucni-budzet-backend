package lazarbosnjak.kucniBudzet.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lazarbosnjak.kucniBudzet.model.Korisnik;
import lazarbosnjak.kucniBudzet.repo.KorisnikRepository;
import lazarbosnjak.kucniBudzet.service.KorisnikService;

@Service
public class KorisnikServiceImpl implements KorisnikService{

	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Override
	public Optional<Korisnik> findByEmail(String email) {
		return korisnikRepository.findFirstByEmail(email);
	}

	@Override
	public Korisnik save(Korisnik korisnik) {
		return korisnikRepository.save(korisnik);
	}

}
