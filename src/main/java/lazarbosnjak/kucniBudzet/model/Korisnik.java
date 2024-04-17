package lazarbosnjak.kucniBudzet.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lazarbosnjak.kucniBudzet.enumeration.KorisnickaUloga;
import lombok.Data;

@Data
@Entity
public class Korisnik {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
//	@Column(nullable = false)
	private String ime;
	
//	@Column(nullable = false)
	private String prezime;
	
//	@Column(unique = true, nullable = false)
	private String email;
	
//	@Column(nullable = false)
	private String lozinka;
	
	@Enumerated(EnumType.STRING)
	private KorisnickaUloga uloga;

}
