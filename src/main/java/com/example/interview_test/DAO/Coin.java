package com.example.interview_test.DAO;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "coin")
@Entity
public class Coin {

	@Id
	private String coinName;
	@Column(name = "chinese_name")
	private String chineseName;
	@Column(name = "rate")
	private float rate;
	@Column(name = "update_time")
	private Date updateTime;

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Coin [coinName=" + coinName + ", chineseName=" + chineseName + ", rate=" + rate + ", updateTime="
				+ updateTime + "]";
	}

}
