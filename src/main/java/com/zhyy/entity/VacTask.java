package com.zhyy.entity;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  工作流任务实体类
 * @author xlx
 * @Date 上午 8:36 2020/2/24 0024
 * @Param
 * @return
 **/
public class VacTask implements Serializable
{

    private String id;
    private String name;
    private Vacation vac;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vacation getVac() {
        return vac;
    }

    public void setVac(Vacation vac) {
        this.vac = vac;
    }
    @Override
    public String toString()
    {
        return  new Gson().toJson(this);
    }
}
