package lazarbosnjak.kucniBuzdet.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lazarbosnjak.kucniBuzdet.model.Korisnik;
import lazarbosnjak.kucniBuzdet.repo.KorisnikRepository;
import lazarbosnjak.kucniBuzdet.service.KorisnikService;

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
