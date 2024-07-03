package com.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.member.model.MemberService;
//用來處理會員驗證信連結的controller
@Controller
public class MemberVerifyController {
	@Autowired
	MemberService memsvc;
	
	
@GetMapping("/verify" )
	public String memberVerify(@Param("token")String token,Model model,RedirectAttributes attr) {
	boolean verified=memsvc.verify(token);
	if(verified) {
		    attr.addAttribute("verifySuccess", "帳號已成功開通，請登入!");
	        return "redirect:/member/verifySuccess";
	}else {
		
		 attr.addAttribute("verifySuccess", "帳號已開通過");
	        return "redirect:/member/verifySuccess";
	}
	
}
	
}
