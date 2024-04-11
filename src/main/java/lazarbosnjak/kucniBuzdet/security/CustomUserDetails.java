package lazarbosnjak.kucniBuzdet.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lazarbosnjak.kucniBuzdet.enumeration.KorisnickaUloga;
import lazarbosnjak.kucniBuzdet.model.Korisnik;

public class CustomUserDetails extends Korisnik implements UserDetails {
	
	private String username;
    private String password;
    Collection<? extends GrantedAuthority> grantedAuthorities;

    public CustomUserDetails(Korisnik korisnik) {
        this.username = korisnik.getEmail();
        this.password = korisnik.getLozinka();
        List<GrantedAuthority> auths = new ArrayList<>();

        for(KorisnickaUloga uloga : korisnik.getUloge()){

            auths.add(new SimpleGrantedAuthority(uloga.toString()));
        }
        this.grantedAuthorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
	
