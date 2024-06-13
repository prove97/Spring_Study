package com.kh.mail;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

//직접도구를 생성해서 이메일을 보내보자

/*
 * Email Protocol
 * SMTP
 * 이메일을 전송할 때 사용하는 프로토콜
 * 
 * POP
 * 이메일서버에 도착한 메일을 클라이언트로 가져올 때 사용하는 프로토콜
 * 
 * IMAP
 * 이메일 서버에 직접 접속하여 이메일을 확인할 때 사용하는 프로토콜
 * (gamil의 SMTP를 이용하기 위해서는 IMAP을 사용으로 해줘야한다.) 
 * 
 * */
public class Test1 {
	public static void main(String[] args) {
		//MIME 형식의 메일을 보내기 위해서는 JavaMailSender 인터페이스를 사용한다.
		//계정설정
		
		 JavaMailSenderImpl sender = new JavaMailSenderImpl();
		 sender.setHost("smtp.gmail.com");
		 sender.setPort(587);
		 sender.setUsername("dsr102938@gmail.com");
		 sender.setPassword("hbac diza oekv dheo");
		 
		 //옵션설정
		 Properties prop = new Properties();
		 prop.put("mail.smtp.auth", true);
		 prop.put("mail.smtp.starttls.enable", true);
		 
		 sender.setJavaMailProperties(prop);
		 
		 //메시지 생성
		 SimpleMailMessage message = new SimpleMailMessage();
		 message.setSubject("이메일 전송 테스트");
		 message.setText("이메일을 테스트 하려고 보내는 메시지");
		 
		 String[] to = {"dsr1029@naver.com"};
		 message.setTo(to);
		 
		 String[] cc = {"dsr102938@gmail.com"};
		 message.setTo(cc);
		 
		 sender.send(message);
		 
		 System.out.println("전송완료");
		 
		 
	}
}
