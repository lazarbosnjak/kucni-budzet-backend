package lazarbosnjak.kucniBudzet.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class Korisnik implements UserDetails {
	
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 List<GrantedAuthority> authorities = new ArrayList<>();
		 authorities.add(new SimpleGrantedAuthority(this.uloga.toString()));
		 return authorities;
	}

	@Override
	public String getPassword() {
		return lozinka;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
