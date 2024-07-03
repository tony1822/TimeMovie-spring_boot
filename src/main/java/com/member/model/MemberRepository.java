package com.member.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<MemberVO, Integer> {

	@Query(value = "SELECT * FROM member WHERE member_account = ?1 AND member_password = ?2", nativeQuery = true)
	MemberVO loginMemberVerify(String member_account, String member_password);

	@Modifying
	@Query(value = "UPDATE member SET member_Name=?1, member_Account=?2, member_Email=?3, member_Phone=?4, member_Password=?5 WHERE member_Id=?6", nativeQuery = true)
	void updateMember(String memberName, String memberAccount, String memberEmail, String memberPhone,
			String memberPassword, Integer memberId);

	@Query(value = "SELECT* FROM  member where member_email=?1", nativeQuery = true)
	MemberVO findMemberByEmail(String memberEmail);

	@Transactional
	@Modifying
	@Query(value = "UPDATE member SET member_Password =?1 WHERE member_Id = ?2", nativeQuery = true)
	void updateMemberPassword(String passRandom,Integer memberId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE member SET is_admin = '0' WHERE member_Id = ?1", nativeQuery = true)
	void updateAdmin(Integer memberId);

	@Query(value = "SELECT* FROM  member where verification_token=?1", nativeQuery = true)
	MemberVO findMemberByVerificationToken(String verificationToken);

	@Transactional
	@Modifying
	@Query(value = "UPDATE member SET is_verified = true WHERE member_Id = ?1", nativeQuery = true)
	void enable(Integer memberId);
	
	
	//尋找是否有註冊過信箱，有的話數量會大於1，再到service層去判斷
	@Query(value = "SELECT COUNT(*) FROM member WHERE member_email = ?", nativeQuery = true)
	Integer findUkEmail(String memberEmail);
	
	
	@Query(value = "SELECT* FROM  member where is_admin='1'", nativeQuery = true)
	List<MemberVO> findAllEmp();
	
	@Query(value = "SELECT* FROM  member where member_status!='null'", nativeQuery = true)
	List<MemberVO> findAllBackMem();
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE member SET member_email =?1,member_Phone=?2,member_img=?3 WHERE member_Id = ?4", nativeQuery = true)
	void updateEmp(String memberEmail,String memberPhone, byte[] memberImg ,Integer memberId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE member SET member_img=?1 WHERE member_Id = ?2", nativeQuery = true)
	void updateMemberImg(byte[] memberImg ,Integer memberId);
	
		
	@Transactional
	@Modifying
	@Query(value = "UPDATE member SET member_status = ?1 WHERE member_Id = ?2", nativeQuery = true)
	void updateStatus(String newStatus,Integer memberId);

}