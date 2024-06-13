package com.kh.mail;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class Test2 {
	
	@Autowired
	private JavaMailSender sender;
	
	@GetMapping("send")
	public String mail() {
		 //메시지 생성
		 SimpleMailMessage message = new SimpleMailMessage();
		 message.setSubject("이메일 전송 테스트2");
		 message.setText("이메일을 테스트 하려고 보내는 2 메시지");
		 
		 String[] to = {"dsr102938@gmail.com"};
		 message.setTo(to);
		 
		 String[] cc = {"dsr1029@naver.com"};
		 message.setTo(cc);
		 
		 sender.send(message);
		 
		 System.out.println("전송완료");
		 return "redirect:/";
	}
	
	@GetMapping("hypermail")
	public String hyperMail() throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
		
		 String[] to = {"dsr102938@gmail.com"};
		 helper.setTo(to);
		 
		 String[] cc = {"dsr1029@naver.com"};
		 helper.setTo(cc);
		 
		 System.out.println("전송완료");
		 helper.setSubject("이메일 전송테스트3");
		 
		 String url = ServletUriComponentsBuilder
					 .fromCurrentContextPath()
					 .port(8888)
					 .queryParam("user_id", "user01")
					 .queryParam("uuid", "skldjvnksdjbnv")
					 .toUriString();
		 
		 //두번째 인자를 true로 보낼시 html을 사용하겠다는 의미
		 helper.setText("<a href='"+ url + "'>웹사이트로 이동</a>", true);
		 sender.send(message);

		 return "redirect:/"; 
	}

	@GetMapping("sendfile")
	public String sendFile() throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		
		String[] to = {"dsr102938@gmail.com"};
		helper.setTo(to);
		
		String[] cc = {"dsr1029@naver.com"};
		helper.setTo(cc);
		
		helper.setSubject("이미지 전송 이메일 테스트");
		
		helper.setText("파일 전송 테스트 입니다");
		
		//첨부파일
		DataSource source = new FileDataSource("C:\\Users\\user1\\Downloads\\sample.jpg");
		helper.addAttachment(source.getName(), source);
		
		sender.send(message);
		
		return "redirect:/"; 
	}
	
}
