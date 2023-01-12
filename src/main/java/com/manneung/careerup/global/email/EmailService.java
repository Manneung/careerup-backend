package com.manneung.careerup.global.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    //의존성 주입을 통해서 필요한 객체를 가져온다.
    private final JavaMailSender emailSender;
    // 타임리프를사용하기 위한 객체를 의존성 주입으로 가져온다
    private final SpringTemplateEngine templateEngine;
    //메일 양식 작성
    public EmailAuthResponseDto sendEmail(String email) throws MessagingException, UnsupportedEncodingException {
        EmailAuthResponseDto emailAuthResponseDto = new EmailAuthResponseDto();

        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;

        String setFrom = "rladustn1001@naver.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toEmail = email; //받는 사람
        String title = "careerup 회원가입 인증 번호"; //제목
        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> 내 손으로 직접 만드는 커리어 성장 플랫폼 커리어맵 입니다</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>항상 당신의 꿈을 응원합니다. 감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += checkNum + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgg += "</div>";


        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(msgg, "utf-8", "html");
        emailSender.send(message);

        emailAuthResponseDto.setCheckNum(checkNum);
        return emailAuthResponseDto;
    }

//    //실제 메일 전송
//    public MimeMessage sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
//
//        //메일전송에 필요한 정보 설정
//        MimeMessage emailForm = createEmailForm(toEmail);
//        //실제 메일 전송
//        emailSender.send(emailForm);
//
//    }
}