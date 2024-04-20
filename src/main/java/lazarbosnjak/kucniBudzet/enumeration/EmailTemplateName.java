package lazarbosnjak.kucniBudzet.enumeration;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
	
	ACTIVATE_USER("activate_user"),
	
	PASSWORD_RECOVERY("password_recovery");
	
	private final String name;

	private EmailTemplateName(String name) {
		this.name = name;
	}
	
}
