package com.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.MailService;
import com.member.model.MemberService;
import com.member.model.MemberVO;

import net.bytebuddy.utility.RandomString;

//處理忘記密碼的controller
@RestController
@RequestMapping("/member")
public class MemberRestController {

	@Autowired
	MemberService memsvc;

	@PostMapping("forgetSubmit")
	public ResponseEntity<String> memberPasswordForget(@RequestParam("email") String email) {
		String passRandom = RandomString.make(5);
		MemberVO member = memsvc.findMemberEmail(email);
		if (member == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("此信箱用戶不存在!");
		} else {
//			String passRandom = RandomString.make(5);		
			String to = email;
			String subject = "忘記密碼通知";
			String ch_name = member.getMemberName();			
			String messageText = "Hello! " + ch_name + "請謹記此密碼:" + passRandom + "\n" + " (已經啟用)";
			memsvc.updateMemberPaswd(passRandom,member);
			MailService mailService = new MailService();
			mailService.sendMail(to, subject, messageText);
			return ResponseEntity.ok("新密碼已寄出，請查閱信箱!");
		}

	}

}
