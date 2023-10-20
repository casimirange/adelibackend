package com.adeli.adelispringboot.configuration.email.service;


import com.adeli.adelispringboot.configuration.email.dto.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class EmailServiceImpl implements IEmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Autowired
	ApplicationContext appContext;

	@Override
	public void sendEmail(EmailDto emailDto) {
		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			Context context = new Context();
	        context.setVariables(emailDto.getProps());
			String html = templateEngine.process(emailDto.getBody(), context);

			mimeMessageHelper.setFrom(emailDto.getFrom(), emailDto.getSenderName());
			mimeMessageHelper.setTo(emailDto.getTo());
			mimeMessageHelper.setSubject(emailDto.getSubject());
			mimeMessageHelper.setReplyTo(emailDto.getReplyTo(),emailDto.getReplyToName());
			mimeMessageHelper.setText(html, true);
			Resource logo = appContext.getResource("classpath:/templates/logo.jpeg");

			mimeMessageHelper.addInline("logo", logo);

			if(emailDto.getAttachement() != null && emailDto.getNamefile() != null && emailDto.getContentType() != null){
				mimeMessageHelper.addAttachment(emailDto.getNamefile(), new ByteArrayDataSource(emailDto.getAttachement(), emailDto.getContentType()));
			}else if(emailDto.getAttachement() != null){
				mimeMessageHelper.addAttachment(emailDto.getFileName(), new ByteArrayDataSource(emailDto.getAttachement(), "application/pdf"));
			}
			emailSender.send(mimeMessage);
			log.info("Email Successful send to {}", emailDto.getTo());
		} catch (Exception ex) {
			log.error("Email send fail to {} error occurred {}", emailDto.getTo(), ex.getLocalizedMessage());
		}
	}
}
