package lazarbosnjak.kucniBudzet.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {

	@NotBlank
	@NotEmpty
	private String fullName;
	
	@NotBlank
	@NotEmpty
	@Email
	private String email;
	
	@NotBlank
	@NotEmpty
	private String password;
	
	@NotBlank
	@NotEmpty
	private String repeatedPassword;
	
}
