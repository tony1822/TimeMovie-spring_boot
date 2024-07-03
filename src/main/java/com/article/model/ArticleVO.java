package com.article.model;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "article")
public class ArticleVO implements java.io.Serializable{
			//EmpVO
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id", updatable = false)
	private Integer articleId ; //empno
	@Column(name = "type_id")
	private Integer typeId ;    //ename
	@Column(name = "member_id")
	private Integer memberId ;
	@Column(name = "theme")
	private String theme ;
	@Column(name = "article_content")
	private String articleContent ;
	@Column(name = "browse_peoples")
    private Integer browsePeoples ;
	@Column(name = "article_status")
    private Integer articleStatus ;
	@Column(name = "release_time")
    private Timestamp releaseTime ;
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public Integer getBrowsePeoples() {
		return browsePeoples;
	}
	public void setBrowsePeoples(Integer browsePeoples) {
		this.browsePeoples = browsePeoples;
	}
	public Integer getArticleStatus() {
		return articleStatus;
	}
	public void setArticleStatus(Integer articleStatus) {
		this.articleStatus = articleStatus;
	}
	public Timestamp getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}
    
    
	

}
