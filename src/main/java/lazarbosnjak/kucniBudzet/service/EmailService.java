package lazarbosnjak.kucniBudzet.service;

import jakarta.mail.MessagingException;

public interface EmailService {

	public void sendEmailPwdRecovery(String to, String confirmationUrl) throws MessagingException;
	
	public void sendEmailUserActivation(String to, String confirmationUrl) throws MessagingException;
	
}
