package lazarbosnjak.kucniBuzdet.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lazarbosnjak.kucniBuzdet.model.Korisnik;
import lazarbosnjak.kucniBuzdet.model.dto.KorisnikDTO;
import lazarbosnjak.kucniBuzdet.service.KorisnikService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KorisnikDtoToKorisnik implements Converter<KorisnikDTO, Korisnik>{

	private final KorisnikService korisnikService;
	
	@Override
	public Korisnik convert(KorisnikDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
