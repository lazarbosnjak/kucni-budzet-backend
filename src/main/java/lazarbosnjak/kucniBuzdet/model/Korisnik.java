package lazarbosnjak.kucniBuzdet.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lazarbosnjak.kucniBuzdet.enumeration.KorisnickaUloga;
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
	private Set<KorisnickaUloga> uloge = new HashSet<KorisnickaUloga>();
	
	public void setUloga(KorisnickaUloga uloga) {
		this.uloge.add(uloga);
	}

}
