package com.zhyy.entity;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description  工作流记录类
 * @author xlx
 * @Date 下午 21:54 2020/2/22 0022
 * @Param
 * @return
 **/
public class Vacation implements Serializable
{

    /**
     * 申请人
     */
    private String applyUser;
    /**
     *  流程实例id
     */
    private String instanceId;
    //申请药品集合
    private List<Druginformation> list;
    //申请原因
    private String reason;
    //申请时间
    private String applyTime;
    //申请状态
    private String applyStatus;

    /**
     * 审核人
     */
    private String auditor="";
    /**
     * 审核结果
     */
    private String result="";
    /**
     * 审核时间
     */
    private String auditTime="";
    /**
     * 发药人
     */
    private String dispenser="";
    /**
     * 发药结果
     */
    private String durgResult="";
    /**
     * 发药时间
     */
    private String medicineTime="";
    /**
     * 当前结果
     */
    private String nowResult;
    private String message;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getNowResult()
    {
        return nowResult;
    }

    public void setNowResult(String nowResult)
    {
        this.nowResult = nowResult;
    }

    public String getDispenser()
    {
        return dispenser;
    }

    public void setDispenser(String dispenser)
    {
        this.dispenser = dispenser;
    }

    public String getDurgResult()
    {
        return durgResult;
    }

    public void setDurgResult(String durgResult)
    {
        this.durgResult = durgResult;
    }



    public List<Druginformation> getList()
    {
        return list;
    }

    public void setList(List<Druginformation> list)
    {
        this.list = list;
    }

	public String getInstanceId()
	{
		return instanceId;
	}

	public void setInstanceId(String instanceId)
	{
		this.instanceId = instanceId;
	}

	public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApplyTime()
    {
        return applyTime;
    }

    public void setApplyTime(String applyTime)
    {
        this.applyTime = applyTime;
    }

    public String getAuditTime()
    {
        return auditTime;
    }

    public void setAuditTime(String auditTime)
    {
        this.auditTime = auditTime;
    }

    public String getMedicineTime()
    {
        return medicineTime;
    }

    public void setMedicineTime(String medicineTime)
    {
        this.medicineTime = medicineTime;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString()
    {
        return  new Gson().toJson(this);
    }
}
