package com.member.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.member.model.Validation;
import com.product_detail.model.ProductDetail;
import com.product_order.model.ProductOrder;
import com.ticlist.model.TicketListVO;
import com.ticorder.model.TicketOrderVO;

import net.bytebuddy.utility.RandomString;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Autowired
	MemberService memsvc;

	@RequestMapping("/")
	public String showLoginPage(ModelMap model) {
		MemberVO member = new MemberVO();
		
		
		
		model.addAttribute("memberVO", member);
		return "back-end/member/memberLogin";
	}
	
		//用來接收當點擊驗證信時會再MemberVerifyController處理驗證邏輯後，重導到這裡，再轉送給登入畫面
	   @GetMapping("/verifySuccess")
	    public String showVerifySuccessPage(@RequestParam(name = "verifySuccess", required = false) String verifySuccess, ModelMap model) {
	        
	            model.addAttribute("verifySuccess", verifySuccess);
	            MemberVO member = new MemberVO();
	    		model.addAttribute("memberVO", member);
	        
	        return "back-end/member/memberLogin";
	    }

	@PostMapping("LogIn")
	public String memberLogin(@Validated(Validation.ValidationLogin.class) MemberVO member, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			
			return "back-end/member/memberLogin";
		}
		member = memsvc.loginMember(member.getMemberAccount(), member.getMemberPassword());
		if (member == null) {
			model.addAttribute("erroMessage", "帳號或密碼錯誤");
			return "back-end/member/memberLogin";
		} else if(!member.isVerified()){
			model.addAttribute("verifyFalse","帳號未被開通，請點擊驗證信!");
			return "back-end/member/memberLogin";
		}else if(member.getMemberStatus().equals("停權")||member.getMemberStatus()==null){
			model.addAttribute("statusFail","帳號已被停權!");
			return "back-end/member/memberLogin";			
		} 
		else{
			byte[]by =member.getMemberImg();
			if(by==null) {
				model.addAttribute("memberVO", member);
				return "back-end/member/memberProfile";
			}else {
				String base64Img = Base64.getEncoder().encodeToString(by);	
				model.addAttribute("memberVO", member);
				model.addAttribute("base64Img",base64Img);	
				return "back-end/member/memberProfile";
			}		
		}
	}

	@GetMapping("register")
	public String registerMember(ModelMap model) {
		MemberVO member = new MemberVO();
		model.addAttribute("memberVO", member);
		return "back-end/member/memberRegis";
	}
	
	@PostMapping("From_Register")
	public String addMember(@Validated(Validation.ValidationRegister.class) MemberVO member,
			BindingResult result, Model model, HttpServletRequest req) {
		boolean shouldReturn = false;
		if (result.hasErrors()) {
			shouldReturn = true;
		}
		
		String paswdc = req.getParameter("paswdc");
		if ((paswdc == null || paswdc.trim().isEmpty())) {
			req.setAttribute("erroMessage1", "確認密碼請勿空白!");
			shouldReturn = true;
		}
		
		if ((!member.getMemberPassword().equals(paswdc))) {
			// 如果密碼和確認密碼不匹配，添加錯誤消息
			req.setAttribute("erroMessage2", "密碼與確認密碼不同，請重新輸入!");
			shouldReturn = true;
		}
		
		boolean isUK = memsvc.isEmailUni(member.getMemberEmail());
		if (isUK) {
			req.setAttribute("erroMessage3", "信箱已被註冊過!");
			shouldReturn = true;
		}
		
		if (shouldReturn) {
			return "back-end/member/memberRegis";
		}
		
		//創建45位數的亂碼，拿來當驗證信的比對號碼
		String randomCode=RandomString.make(45);
		member.setMemberStatus("啟用");
		member.setVerificationToken(randomCode);
		memsvc.addMember(member);
		memsvc.sandMail(req, member);
		model.addAttribute("member",member);
		model.addAttribute("verifySubmit", "驗證信已發送，請去確認開通!");
		return "back-end/member/memberLogin";

	}
	
	
	
	
	
	@PostMapping("ToUpdate")
	public String toUpdateMember(@RequestParam("memId") String memberId,ModelMap model) {

	        System.out.println("Member ID: " + memberId);
	        MemberVO member=memsvc.getOneMember(Integer.valueOf(memberId));
	        model.addAttribute("memberVO",member);
	        return "back-end/member/memberUpdate";
	}
	
	
	@PostMapping("update")
	public String updateMember(@Validated(Validation.ValidationRegister.class)MemberVO member,
			BindingResult result, Model model, HttpServletRequest req) {
	
		boolean shouldReturn = false;
		if (result.hasErrors()) {
			shouldReturn = true;
		}
		String paswdc = req.getParameter("paswdc");
		if ((paswdc == null || paswdc.trim().isEmpty())) {
			req.setAttribute("erroMessage1", "確認密碼請勿空白!");
			shouldReturn = true;
		}
		
		if ((!member.getMemberPassword().equals(paswdc))) {
			// 如果密碼和確認密碼不匹配，添加錯誤消息
			req.setAttribute("erroMessage2", "密碼與確認密碼不同，請重新輸入!");
			shouldReturn = true;
		}
		if (shouldReturn) {
			return "back-end/member/memberUpdate";
		}
		
		 memsvc.updateMember(member);
		 
		 member=memsvc.getOneMember(Integer.valueOf(member.getMemberId()));
		 byte[]by =member.getMemberImg();
			String base64Img = Base64.getEncoder().encodeToString(by);	
			model.addAttribute("base64Img",base64Img);
		 
		 model.addAttribute("memberVO",member);

		 return "back-end/member/memberProfile";
				

	}
	
	
	@GetMapping("forget")
	public String memberForget(){	
		return "back-end/member/memberForget";
	}
	
	@GetMapping("goProduct")
	public String memberProduct(@RequestParam("memberId") String memberId,Model model){
		Integer memId = Integer.valueOf(memberId);
		List<ProductOrder> orders=memsvc.getOrdersByMemberId(memId);
		model.addAttribute("orders",orders);		
		return "back-end/member/memberProduct";
	}
	
	
	@GetMapping("goTicket")
	public String memberTicket(@RequestParam("memberId") String memberId,Model model){
		Integer memId = Integer.valueOf(memberId);
		List<TicketOrderVO> orders= memsvc.getTicketByMemberId(memId);
//		List<TicketListVO> allTicketList = new ArrayList<>();
		model.addAttribute("orders",orders);
		return "back-end/member/memberTicket";
	}
	

	
	
	@PostMapping("updateImg")
	public String updateImg(@RequestParam("images") MultipartFile file,@RequestParam("memId")String memberId,Model model) throws IOException {
		Integer memId = Integer.valueOf(memberId);
		byte[]memberImg=file.getInputStream().readAllBytes();
		memsvc.updateMemImg(memberImg, memId);
		MemberVO member=memsvc.getOneMember(memId);
		System.out.println(memId);
		byte[]by =member.getMemberImg();
		String base64Img = Base64.getEncoder().encodeToString(by);	
		model.addAttribute("base64Img",base64Img);
		model.addAttribute("memberVO",member);
		
		return "back-end/member/memberProfile";
		
	}
	
	
	

}