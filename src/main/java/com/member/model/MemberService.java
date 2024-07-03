package com.member.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MailService;
import com.UrlUtility;
import com.product_order.model.ProductOrder;
import com.ticorder.model.TicketOrderVO;

@Service
public class MemberService {

	@Autowired
	private MemberRepository repository;

	public MemberVO loginMember(String memberAccount, String memberPassword) {
		return repository.loginMemberVerify(memberAccount, memberPassword);
	}

	public void addMember(MemberVO member) {
		repository.save(member);
	}
	
	public void addEmp(MemberVO member) {
		repository.save(member);
	}
	
	
	@Transactional
	public void updateMember(MemberVO member) {
		repository.updateMember(member.getMemberName(), member.getMemberAccount(), member.getMemberEmail(),
				member.getMemberPhone(), member.getMemberPassword(), member.getMemberId());
	}
//	
//	public void updateMember(Member member) {
//		repository.save(member);	
//	}

	public void updateMemberPaswd(String passrandom,MemberVO member) {
		repository.updateMemberPassword(passrandom,member.getMemberId());
	}
	
	
	
	

	public MemberVO findMemberEmail(String memberEmail) {

		return repository.findMemberByEmail(memberEmail);
	}

	public MemberVO getOneMember(Integer memberId) {
		Optional<MemberVO> optional = repository.findById(memberId);
		return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	// 用來寄驗證信給使用者的方法，給MemberController調用
	public void sandMail(HttpServletRequest req, MemberVO member) {
		String siteURL = UrlUtility.getSiteUrl(req);
		// 相當於http://localhost:8080
		String verifyURL = siteURL + "/verify?token=" + member.getVerificationToken();
		String email = member.getMemberEmail();
		String to = email;
		String subject = "---帳號啟用通知---";
		String messageText = "請點選連結啟用您的帳號" + "<h3><a href=\"" + verifyURL + "\">Click here</a></h3>";
		MailService mailService = new MailService();
		mailService.sendMail(to, subject, messageText);
	}

	public boolean verify(String verificationToken) {
		MemberVO member = repository.findMemberByVerificationToken(verificationToken);
		if (member == null) {
			return false;
		} else {
			repository.enable(member.getMemberId());
			return true;
		}
	}
	
	public boolean isEmailUni(String memberEmail) {
		MemberVO member =new MemberVO();
		member.setMemberEmail(memberEmail);
		Integer count=repository.findUkEmail(member.getMemberEmail());	
        boolean emailExists = count > 0;
		return emailExists;
	}
	
	public List<ProductOrder> getOrdersByMemberId(Integer memberId) {
						Optional<MemberVO> member=repository.findById(memberId);
						return member.get().getProdOrders();
    }
	
	public List<TicketOrderVO> getTicketByMemberId(Integer memberId) {
		Optional<MemberVO> member=repository.findById(memberId);
		return member.get().getTicketOrders();
	}
	
	public List<MemberVO>getAllEmp(){	
		return repository.findAllEmp();
	}
	
	public List<MemberVO>getAllBackMem(){	
		return repository.findAllBackMem();
	}
	@Transactional
	public void updateEmp(String memberEmail,String memberPhone,byte[] memberImg,Integer memberId) {
		repository.updateEmp(memberEmail, memberPhone,memberImg, memberId);	
	}
	

	@Transactional
	public void updateIsAdmin(Integer memberId) {
		repository.updateAdmin(memberId);	
	}
	
	@Transactional
	public void updateMemImg(byte[] memberImg,Integer memberId) {
		repository.updateMemberImg(memberImg,memberId);	
	}
	
	@Transactional
	public void updateMemStatus(String newStatus,Integer memberId) {
		repository.updateStatus(newStatus, memberId);
	}
	
	
	

}
