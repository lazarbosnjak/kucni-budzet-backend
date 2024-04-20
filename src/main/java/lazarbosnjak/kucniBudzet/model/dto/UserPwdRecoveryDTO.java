package lazarbosnjak.kucniBudzet.model.dto;

import lombok.Data;

@Data
public class UserPwdRecoveryDTO {

	private String password;
	
	private String repeatedPassword;
	
}
