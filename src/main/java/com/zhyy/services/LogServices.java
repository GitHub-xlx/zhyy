package com.zhyy.services;

import com.zhyy.entity.Log;

import java.util.List;

public interface LogServices
{

	List<Log> queryAll(String where,String limits);

	int save (Log log);

}
