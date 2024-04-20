package lazarbosnjak.kucniBudzet.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthUserDTO {

	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
}
