package com.product_order.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.member.model.MemberVO;
import com.product_detail.model.ProductDetail;

/* VO: Value Object || DTO: Data Transfer Object */


/* Check how sql.Timestamp work
 * import java.sql.Timestamp;
 * test = Timestamp.valueOf("2024-4-16 08:50:50"); test ==> 2024-04-16 08:50:50.0
 * test.toString(); $11 ==> "2024-04-16 08:50:50.0"
 * jshell> test.getTime(); $12 ==> 1713228650000
 * */
@Entity
@Table(name="product_order")
public class ProductOrder implements Serializable {
	/* Instance Variable */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="prod_ord_id")
	private Integer prodOrdId;/* 商品訂單ID */
	
//	@Column(name="member_id")
//	private Integer memberId;		/* 會員ID */
	
	@Column(name="est_time")
	private Timestamp estTime;	/* 訂單成立時間 */
	
	@Column(name="ord_status")
	private Integer ordStatus;	/* 訂單狀態 */

	@Column(name="total")
	private Integer total;		/* 總額 */
	
	@Column(name="recipient")
	private String recipient;	/* 收件人姓名 */
	
	@Column(name="rec_addr")
	private String recAddr;	/* 收件人地址 */
	
	@ManyToOne   //關連到會員
	@JoinColumn(name="member_Id",referencedColumnName = "member_Id")
	private MemberVO member;	

	
	@OneToMany(mappedBy = "productOrder", cascade = CascadeType.ALL)
	@OrderBy("prodDetailId")
	private List<ProductDetail> prodDetails;
	
	// Set/Get
	public List<ProductDetail> getProductDetail() {
		return prodDetails;
	}

	public void setProductDetail(List<ProductDetail> prodDetails) {
		this.prodDetails = prodDetails;
	}
	

	/* Constructor */
	public ProductOrder() {}
	
	public ProductOrder(Integer prodOrdId, MemberVO member, Timestamp estTime, Integer ordStatus, 
			Integer total, String recipient, String recAddr) {
		this.prodOrdId = prodOrdId;
		this.member = member;
		this.estTime = estTime;
		this.ordStatus = ordStatus;
		this.total = total;
		this.recipient = recipient;
		this.recAddr = recAddr;
	
	}

	/* Method */
	public Integer getProdOrdId() {
		return prodOrdId;
	}
	
	public void setProdOrdId(Integer prodOrdId) {
		this.prodOrdId = prodOrdId;
	}
	public MemberVO getMember() { 
		return member;
	}
	public void setMember(MemberVO member) { 
		this.member = member;
	}
	public Timestamp getEstTime() {
		return estTime;
	}
	public void setEstTime(Timestamp estTime) {
		this.estTime = estTime;
	}
	public Integer getOrdStatus() {
		return ordStatus;
	}
	public void setOrdStatus(Integer ordStatus) {
		this.ordStatus = ordStatus;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getRecAddr() {
		return recAddr;
	}
	public void setRecAddr(String recAddr) {
		this.recAddr = recAddr;
	}

	@Override
	public String toString() {
		return "ProductOrder [prodOrdId=" + prodOrdId + ", estTime=" + estTime + ", ordStatus=" + ordStatus + ", total="
				+ total + ", recipient=" + recipient + ", recAddr=" + recAddr + ", member=" + member + "]";
	}
	
//	@Override
//	public String toString() {
//		String result = String.format(
//			"%9d |%9d |%10tF %tT |%10d |%6d |%8s |%10s ", 
//			prodOrdId, member, estTime, estTime, ordStatus, total, recipient, recAddr
//		);
//		return result;
//	}
	
	
	
}
