package lazarbosnjak.kucniBudzet.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lazarbosnjak.kucniBudzet.enumeration.EmailTemplateName;
import lazarbosnjak.kucniBudzet.service.EmailService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;
	private final SpringTemplateEngine templateEngine;
	
	
	@Override
	@Async
	public void sendEmailPwdRecovery(String to, String confirmationUrl) throws MessagingException {
		
		String templateName = EmailTemplateName.PASSWORD_RECOVERY.name();
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("confirmationUrl", confirmationUrl);
		
		Context context = new Context();
		context.setVariables(properties);
		
		helper.setFrom("no-reply@kb.pisns.biz");
		helper.setTo(to);
		helper.setSubject("[Kućni budžet] Promena lozinke");
		
		String template = templateEngine.process(templateName, context);
		helper.setText(template, true);
		
		mailSender.send(mimeMessage);
		System.out.println("E-mail sent...");
	}


	@Override
	@Async
	public void sendEmailUserActivation(String to, String confirmationUrl) throws MessagingException {
		
		String templateName = EmailTemplateName.ACTIVATE_USER.name();
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("confirmationUrl", confirmationUrl);
		
		Context context = new Context();
		context.setVariables(properties);
		
		helper.setFrom("no-reply@kb.pisns.biz");
		helper.setTo(to);
		helper.setSubject("[Kućni budžet] Aktivirajte nalog");
		
		String template = templateEngine.process(templateName, context);
		helper.setText(template, true);
		
		mailSender.send(mimeMessage);
		System.out.println("Registration E-mail sent...");
		
	}
	
	
	
}
