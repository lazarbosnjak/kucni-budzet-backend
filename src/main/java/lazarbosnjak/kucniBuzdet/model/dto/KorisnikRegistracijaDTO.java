package lazarbosnjak.kucniBuzdet.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KorisnikRegistracijaDTO {

	@NotBlank
	@NotEmpty
	private String email;
	
	@NotBlank
	@NotEmpty
	private String password;
	
}
