package com.member.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.member.model.Validation;

@Controller
@RequestMapping("/backstage")
public class BackStageController {
	@Autowired
	MemberService memsvc;

	@RequestMapping("/")
	public String showlogin(ModelMap model) {
		MemberVO member = new MemberVO();
		model.addAttribute("memberVO", member);
		return "back-end/backstage/empLogin";

	}

	@PostMapping("login")
	public String memberLogin(@Validated(Validation.ValidationLogin.class) MemberVO member, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "back-end/backstage/empLogin";
		}
		member = memsvc.loginMember(member.getMemberAccount(), member.getMemberPassword());
		if (member == null) {
			model.addAttribute("erroMessage1", "帳號或密碼錯誤");
			return "back-end/backstage/empLogin";
		} else if (member.getIsAdmin() == false) {
			model.addAttribute("erroMessage2", "已停權,非在職員工!");
			return "back-end/backstage/empLogin";
		} else {
			List<MemberVO> allEmp = memsvc.getAllEmp();
			model.addAttribute("allEmp", allEmp);
			return "back-end/backstage/backFormEmp";
		}

	}

	@PostMapping("regisEmp")
	public String empRegis(@RequestParam("member_name") String memberName,
			@RequestParam("member_account") String memberAccount,
			@RequestParam("member_password") String memberPassword, ModelMap model) {
		MemberVO member = new MemberVO();
		member.setMemberName(memberName);
		member.setMemberAccount(memberAccount);
		member.setMemberPassword(memberPassword);
		member.setIsAdmin(true);
		memsvc.addEmp(member);
		model.addAttribute("success", memberName + "員工新增成功!");
		// 68-71是為了讓剛新增的會員變成紅色
		Integer newlyAddedMemberId = member.getMemberId();
		System.out.println(newlyAddedMemberId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newlyAddedMemberId", newlyAddedMemberId);
		model.addAttribute("newlyAddedMemberId", newlyAddedMemberId);

		List<MemberVO> allEmp = memsvc.getAllEmp();
		model.addAttribute("allEmp", allEmp);
		return "back-end/backstage/backFormEmp";
	}

	@PostMapping("/resetPassword")
	@ResponseBody
	public Map<String, Object> resetPassword(@RequestParam("memberId") String memberId) {

		Map<String, Object> response = new HashMap<>();
		String newPassword = "000";
		System.out.println(newPassword);
		System.out.println(memberId);
		try {
			MemberVO member = memsvc.getOneMember(Integer.valueOf(memberId));
			System.out.println(member);
			if (member != null) {
				memsvc.updateMemberPaswd(newPassword, member);
				response.put("success", true);
				response.put("newPassword", newPassword);
			} else {
				response.put("success", false);
				response.put("message", "Member not found");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error resetting password");
		}
		return response;
	}

	@PostMapping("/resetAdmin")
	@ResponseBody
	public Map<String, Object> resetAdmin(@RequestParam("memberId") String memberId) {
		Map<String, Object> response = new HashMap<>();
		memsvc.updateIsAdmin(Integer.valueOf(memberId));
		response.put("success", true);
		return response;
	}
	
	
	@PostMapping("/toMemStatus")
	@ResponseBody
	public Map<String, Object> resetMemStatus(@RequestParam("memberId") String memberId,@RequestParam("newStatus") String newStatus){
		Map<String, Object> response = new HashMap<>();
		memsvc.updateMemStatus(newStatus,Integer.valueOf(memberId));
		response.put("success", true);
		return response;
	}
	
	
	

	@PostMapping("/toEmpProfile")
	public String toUpdateMember(@RequestParam("memberId") String memberId,ModelMap model) {
		MemberVO member = memsvc.getOneMember(Integer.valueOf(memberId));
		byte[] by = member.getMemberImg();
		if (by == null) {
			model.addAttribute("member", member);
			return "back-end/backstage/empProfile";
		} else {
			String base64String = Base64.getEncoder().encodeToString(by);
			model.addAttribute("base64String", base64String);
			model.addAttribute("member", member);
			return "back-end/backstage/empProfile";
		}
	}

	@PostMapping("/updateEmployee")
	public String updateEmp(@RequestParam("memberId") String memberId, @RequestParam("email") String memberEmail,
			@RequestParam("phone") String memberPhone, @RequestParam("file") MultipartFile file, ModelMap model)
			throws IOException {

		byte[] memberImg = file.getInputStream().readAllBytes();
		memsvc.updateEmp(memberEmail, memberPhone, memberImg, Integer.valueOf(memberId));
		List<MemberVO> allEmp = memsvc.getAllEmp();
		model.addAttribute("allEmp", allEmp);
		model.addAttribute("updateSuccess", "員工更新成功!");
		return "back-end/backstage/backFormEmp";
	}

	@GetMapping("/backController")
	public String backEmp(ModelMap model) {
		List<MemberVO> allEmp = memsvc.getAllEmp();
		model.addAttribute("allEmp", allEmp);
		return "back-end/backstage/backFormEmp";
	}

	@GetMapping("/toMemProfile")
	public String toMemProfile(ModelMap model) {
		Map<Integer, String> base64Images = new HashMap<>();
		List<MemberVO> allMem = memsvc.getAllBackMem();
		
		for (MemberVO memberVO : allMem) {
			byte[] by = memberVO.getMemberImg();
			if (by != null) {
				String base64String = Base64.getEncoder().encodeToString(by);	
				base64Images.put(memberVO.getMemberId(), base64String);
			}
		}
		model.addAttribute("base64Images",base64Images);
		model.addAttribute("allMem", allMem);
		return "back-end/backstage/backMemProfile";
	}

}
