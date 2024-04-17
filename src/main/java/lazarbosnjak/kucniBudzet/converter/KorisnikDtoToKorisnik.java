package lazarbosnjak.kucniBudzet.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lazarbosnjak.kucniBudzet.model.Korisnik;
import lazarbosnjak.kucniBudzet.model.dto.KorisnikDTO;
import lazarbosnjak.kucniBudzet.service.KorisnikService;
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
