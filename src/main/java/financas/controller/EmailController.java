package financas.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import financas.dto.EmailDTO;
import financas.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService service;

	@PostMapping("/simples")
	public ResponseEntity<?> enviarEmail(@RequestBody EmailDTO email) {
		try {
			service.sendEmail(email);
			return ResponseEntity.ok("E-mail simples enviado");
		} catch (MailException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/html")
	public ResponseEntity<?> sendHtmlEmail(@RequestBody EmailDTO email) {
		try {
			service.sendHtmlEmail(email);
			return ResponseEntity.ok("E-mail HTML enviado");
		} catch (MailException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (MessagingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PostMapping("/attachment")
	public ResponseEntity<?> sendEmailWithAttachment(@RequestBody EmailDTO email) {
		try {
			service.sendEmailWithAttachment(email);
			return ResponseEntity.ok("E-mail com anexo enviado");
		} catch (MailException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (MessagingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
