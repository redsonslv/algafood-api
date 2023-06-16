package com.algaworks.algafood.infrastructure.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService{

	@Autowired
	private Environment environment;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	Configuration freemarkerConfig;
	
	@Override
	public void enviar(Mensagem mensagem) {
		try {
			String corpo = processarTemplate(mensagem);
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"UTF-8");
			
			helper.setFrom(emailProperties.getRemetente());
			helper.setSubject(mensagem.getAssunto());
			helper.setText(corpo, true);

			if (environment.getActiveProfiles()[0].equals("develop")){
				helper.setTo(emailProperties.getDestinatarioImpl());				
			}else {
				helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			}
			
			mailSender.send(mimeMessage);
		}catch(Exception e) {
			throw new EmailException("Não foi possivel enviar email.", e); 
		}
		
	}
	
	private String processarTemplate(Mensagem mensagem) {
		try {
			Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
		} catch (Exception e) {
			throw new EmailException("Não foi possivel montar o template do email.",e);
		}
	}
}
