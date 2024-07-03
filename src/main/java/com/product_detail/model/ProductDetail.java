package com.product_detail.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.product.model.Product;
import com.product_order.model.ProductOrder;
@Entity
@Table(name="product_detail")
public class ProductDetail implements Serializable {
	// Instance Variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="prod_detail_id")
	private Integer prodDetailId;	// 明細ID

//	@Column(name="prod_ord_id")
//	private Integer prodOrdId;	// 商品訂單ID
	@ManyToOne //關連到商品訂單主檔
	@JoinColumn(name="prod_ord_id" ,referencedColumnName = "prod_ord_id")
	private ProductOrder productOrder;
	
//	@Column(name="prod_id")
//	private Integer prodId;		// 商品ID
	
	@ManyToOne //關連到訂單商品
	@JoinColumn(name="prod_id" ,referencedColumnName = "prod_id")
	private Product product;
	
	
	@Column(name="unit_price")
	private Integer unitPrice;		// 單價
	
	@Column(name="prod_count")
	private Integer prodCount;		// 數量
	
	@Column(name="prod_sum")
	private Integer prodSum;		// 小計
	
	// Constructor
	public ProductDetail() {};
	
	public ProductDetail(Integer prodDetailId, Product product, Integer prodId, ProductOrder productOrder, 
			Integer unitPrice, Integer prodCount, Integer prodSum) {
		setProdDetailId(prodDetailId);
		setProductOrder(productOrder);
		setProduct(product);
		setUnitPrice(unitPrice);
		setProdCount(prodCount);
		setProdSum(prodSum);
	}

	// Method 

	void setProdDetailId(Integer prodDetailId) {
		this.prodDetailId = prodDetailId;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Integer getProdDetailId() {
		return prodDetailId;
	}

	
//	public Integer getProdOrdId() {
//		return prodOrdId;
//	}
//
//	public void setProdOrdId(Integer prodOrdId) {
//		this.prodOrdId = prodOrdId;
//	}

//	public Integer getProdId() {
//		return prodId;
//	}

//	public void setProdId(Integer prodId) {
//		this.prodId = prodId;
//	}

	public ProductOrder getProductOrder() {
		return productOrder;
	}

	public void setProductOrder(ProductOrder productOrder) {
		this.productOrder = productOrder;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getProdCount() {
		return prodCount;
	}

	public void setProdCount(Integer prodCount) {
		this.prodCount = prodCount;
	}

	public Integer getProdSum() {
		return prodSum;
	}

	public void setProdSum(Integer prodSum) {
		this.prodSum = prodSum;
	}

	@Override
	public String toString() {
		return "ProductDetail [prodDetailId=" + prodDetailId + ", productOrder=" + productOrder + ", product=" + product
				+ ", unitPrice=" + unitPrice + ", prodCount=" + prodCount + ", prodSum=" + prodSum + "]";
	}

	/* Reference
	 * String result = String.format("%10s, %15d%n", prod_name, prod_id);
	 * */
//	@Override
//	public String toString() {
//		String result = String.format("%14d |%12d |%9d |%11d |%11d |%9d", 
//				prodDetailId, productOrder,  unitPrice, prodCount, prodSum);
////		return prod_detail_id + ", " + prod_ord_id + ", " + prod_id + ", "
////				+ unit_price + ", " + prod_count + ", " + prod_sum;
//		return result;
//	}
	/*"Product_detail [prod_detail_id=" + prod_detail_id + ", prod_ord_id=" + prod_ord_id + ", prod_id="
				+ prod_id + ", unit_price=" + unit_price + ", prod_count=" + prod_count + ", prod_sum=" + prod_sum
				+ "]"*/
	
	
}
