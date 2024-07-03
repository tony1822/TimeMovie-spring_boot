package com.member.model;
//demo
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;

import com.product_order.model.ProductOrder;
import com.ticorder.model.TicketOrderVO;



@Entity
@Table(name = "member")
public class MemberVO implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id", updatable = false)
	private Integer memberId;

	@Column(name = "member_account")
	private String memberAccount;

	@Column(name = "member_password")
	private String memberPassword;

	@Column(name = "member_name")
	private String memberName;

	@Column(name = "member_phone")
	private String memberPhone;

	@Column(name = "member_email")
	private String memberEmail;
	
	@Column(name = "member_register_datetime", updatable = false)
	@CreationTimestamp
	private LocalDateTime  memberRegisterDatetime;

	@Column(name = "member_img", updatable = false, columnDefinition = "LONGBLOB")
	private byte[] memberImg;

	@Column(name = "is_verified", updatable = false)
	private boolean isVerified;
	
	@Column(name = "is_admin", updatable = false)
	private boolean isAdmin;
	
	@Column(name = "member_status")
	private String memberStatus;
	
	
	@Column(name = "verificationToken", updatable = false)
	private String verificationToken;
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@OrderBy("prodOrdId")
	private List<ProductOrder> prodOrders;
	
	@OneToMany(mappedBy = "memberId", cascade = CascadeType.ALL)
	@OrderBy("movieOrderId")
	private List<TicketOrderVO> TicketOrders;
	// Set/Get
	public List<TicketOrderVO> getTicketOrders() {
		return TicketOrders;
	}

	public void setTicketOrders(List<TicketOrderVO> TicketOrders) {
		this.TicketOrders = TicketOrders;
	}
	
	// Set/Get
	public List<ProductOrder> getProdOrders() {
		return prodOrders;
	}

	public void setProdOrders(List<ProductOrder> prodOrders) {
		this.prodOrders = prodOrders;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public MemberVO() {
		super();
	}

	public MemberVO(String memberAccount, String memberPassword, String memberName, String memberPhone,
			String memberEmail) {
		super();
		this.memberAccount = memberAccount;
		this.memberPassword = memberPassword;
		this.memberName = memberName;
		this.memberPhone = memberPhone;
		this.memberEmail = memberEmail;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@NotBlank(groups = Validation.ValidationLogin.class, message = "會員帳號請勿空白")
	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	@NotBlank(groups = Validation.ValidationLogin.class, message = "會員密碼請勿空白")
	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	@NotEmpty(groups = Validation.ValidationRegister.class, message = "會員名稱請勿空白")
	@Pattern(groups = Validation.ValidationRegister.class, regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@NotBlank(groups = Validation.ValidationRegister.class, message = "會員電話請勿空白")
	@Pattern(groups = Validation.ValidationRegister.class, regexp = "^09[0-9]{8}$", message = "電話開頭請為0和9後面為8位數字")
	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	@NotBlank(groups = Validation.ValidationRegister.class, message = "會員信箱請勿空白")
	@Email(groups = Validation.ValidationRegister.class, message = "會員信箱格式錯誤")
	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public LocalDateTime getMemberRegisterDatetime() {
		return memberRegisterDatetime;
	}

	public void setMemberRegisterDatetime(LocalDateTime memberRegisterDatetime) {
		this.memberRegisterDatetime = memberRegisterDatetime;
	}

	public byte[] getMemberImg() {
		return memberImg;
	}

	public void setMemberImg(byte[] memberImg) {
		this.memberImg = memberImg;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}


	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", memberAccount=" + memberAccount + ", memberPassword="
				+ memberPassword + ", memberName=" + memberName + ", memberPhone=" + memberPhone + ", memberEmail="
				+ memberEmail + ", memberRegisterDatetime=" + memberRegisterDatetime + ", memberImg="
				+ Arrays.toString(memberImg) + ", isVerified=" + isVerified + ", isAdmin=" + isAdmin
				+ ", verificationToken=" + verificationToken + ", prodOrders=" + prodOrders + "]";
	}

}
