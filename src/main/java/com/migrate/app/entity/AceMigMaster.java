package com.migrate.app.entity;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "ace_mig_master")
public class AceMigMaster {
	
	@Id	
	@Column(name = "LGC_CUST_ID")
	private Long lgcCustID;
	
	@Column(name = "TAR_CUST_ID")
	private String tarCustID;
	
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	@Column(name = "PROC_IND")
	private String procInd;
	
	@Column(name = "PROC_DESC")
	private String procDescription;
	
	@Column(name = "EXEC_SEQ")
	private String execSeq;
	
	public AceMigMaster() {
	
	}
	
	public AceMigMaster(String customerId) {
		// TODO Auto-generated constructor stub
	}

	public Long getLgcCustID() {
		return lgcCustID;
	}

	public void setLgcCustID(Long lgcCustID) {
		this.lgcCustID = lgcCustID;
	}

	public String getTarCustID() {
		return tarCustID;
	}

	public void setTarCustID(String tarCustID) {
		this.tarCustID = tarCustID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date localDateTime) {
		this.createDate = localDateTime;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date localDateTime) {
		this.updateDate = localDateTime;
	}

	public String getProcInd() {
		return procInd;
	}

	public void setProcInd(String procInd) {
		this.procInd = procInd;
	}

	public String getProcDescription() {
		return procDescription;
	}

	public void setProcDescription(String procDescription) {
		this.procDescription = procDescription;
	}

	public String getExecSeq() {
		return execSeq;
	}

	public void setExecSeq(String execSeq) {
		this.execSeq = execSeq;
	}

	@Override
	public String toString() {
		return "AceMigMaster [lgcCustID=" + lgcCustID + ", tarCustID=" + tarCustID + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", procInd=" + procInd + ", procDescription="
				+ procDescription + ", execSeq=" + execSeq + "]";
	}
	
}
