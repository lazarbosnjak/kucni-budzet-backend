package lazarbosnjak.kucniBuzdet.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KorisnikRegistracijaDTO {

	@NotBlank
	@NotEmpty
	private String ime;
	
	@NotBlank
	@NotEmpty
	private String prezime;
	
	@NotBlank
	@NotEmpty
	@Email
	private String email;
	
	@NotBlank
	@NotEmpty
	private String password;
	
	@NotBlank
	@NotEmpty
	private String ponovljeniPassword;
	
}
